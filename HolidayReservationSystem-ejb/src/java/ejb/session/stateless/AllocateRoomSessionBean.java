/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.AllocationExceptionReport;
import entity.Reservation;
import entity.Room;
import entity.RoomReservation;
import entity.RoomType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.AllocationExceptionReportTypeEnum;

/**
 *
 * @author jeremy
 */
@Stateless
public class AllocateRoomSessionBean implements AllocateRoomSessionBeanRemote, AllocateRoomSessionBeanLocal {

    @EJB
    private HotelInventorySessionBeanLocal hotelInventorySessionBeanLocal;
    @EJB 
    private RoomRateSessionBeanLocal roomRateSessionBeanLocal;
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    
    public AllocateRoomSessionBean() {
    }

    @Schedule(hour = "2", minute = "0", second = "0")
    public void automaticAllocation() {
        allocateRooms();
    } 
    
    public void allocateRooms(){ 
        Date currentDate = getCurrentDate();
        allocateRooms(currentDate);
    }
    
    
    
    @Override
    public void allocateRooms(Date currentDate) {
        //get all the rooms that are not disabled
        List<Room> rooms = hotelInventorySessionBeanLocal.getAllEnabledRooms();
        
        //get the list of reservations that start for this date's NIGHT. need to allocated these 
        List<Reservation> currentDayReservations = getCurrentDayReservations(currentDate);
        
        //find the list of reservations for which the rooms are being used
        //ie reservations where the startDate is before current date && the endDate <= current date
        List<Reservation> overlappingReservations = getOverlappingReservations(currentDate);
        
        //find the rooms allocated to these reservations
        for (Reservation r : overlappingReservations) {
            List<RoomReservation> roomReservations = r.getRoomReservations();
            for (RoomReservation roomReservation : roomReservations) {
                Room room = roomReservation.getRoom();
                rooms.remove(room);
            }
        }
        
        
        //create hashMap of the roomTypes and roomCount
        HashMap<RoomType, List<Room>> roomTypeCount = new HashMap<RoomType, List<Room>>(); 
        HashMap<RoomType, Integer> roomTypesExtra = new HashMap<RoomType, Integer>(); //used to measure the roomtypes that have not been reserved
        
        
        for (Room room : rooms) {
            RoomType roomType = room.getRoomType();
            List<Room> roomList = roomTypeCount.getOrDefault(roomType, new ArrayList<Room>());
            roomList.add(room);
            roomTypeCount.put(roomType, roomList);
            
            roomTypesExtra.put(roomType, roomTypesExtra.getOrDefault(roomType, 0) + 1);
        }
        
        //gets the number of extra room types 
        for (Reservation r : currentDayReservations) {
            int numOfRooms = r.getNumRooms(); 
            RoomType rt = r.getRoomType(); 
            roomTypesExtra.put(rt, roomTypesExtra.getOrDefault(rt, 0) - numOfRooms);
        }

        
        //for each of the reservations allocate the room and create the room Reservation
        for (Reservation r : currentDayReservations) {
            RoomType roomType = r.getRoomType(); 
            int numRooms = r.getNumRooms();
            
            //check for each room
            for (int j = 0; j < numRooms; j++) {
                //if that room type has 0 left
                if (roomTypeCount.getOrDefault(roomType, new ArrayList<Room>()).size() <= 0) {
                    //check for the next best ranking
                    RoomType newRoomType = getNextRankingRoomType(roomType);
                    
                    //check if the new one has extras
                    if (roomTypesExtra.get(newRoomType) > 0) {
                        Room allocatedRoom = roomTypeCount.get(newRoomType).get(0); 
                        RoomReservation roomReservation = createRoomReservation(allocatedRoom, newRoomType);
                        roomTypeCount.get(newRoomType).remove(allocatedRoom); //takes it out from the current roomts
                        createAllocationExceptionReport(roomReservation, AllocationExceptionReportTypeEnum.TYPE_1);
                        
                        //set the reservation
                        r.getRoomReservations().add(roomReservation);
                        
                        roomTypesExtra.put(newRoomType, roomTypesExtra.get(newRoomType) - 1);
                    } else {
                        RoomReservation roomReservation = createRoomReservation(newRoomType); 
                        r.getRoomReservations().add(roomReservation);
                        createAllocationExceptionReport(roomReservation, AllocationExceptionReportTypeEnum.TYPE_2);
                    }
                } else if (roomTypeCount.get(roomType).size() > 0) {
                    Room allocatedRoom = roomTypeCount.get(roomType).get(0); 
                    RoomReservation roomReservation = createRoomReservation(allocatedRoom, roomType);
                    roomTypeCount.get(roomType).remove(allocatedRoom);
                    r.getRoomReservations().add(roomReservation);
                } 
            }
            
        }
    }
    
    private List<Reservation> getCurrentDayReservations(Date currentDate) {
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.startDate =:startDate");
        query.setParameter("startDate", currentDate);
        return query.getResultList();
    }
    
    private List<Reservation> getOverlappingReservations(Date currentDate) {
        //gets the reservations where the dates spill into the current day
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.startDate <= :currentDate AND r.endDate >= :currentDate");
        query.setParameter("currentDate", currentDate);
        return query.getResultList();
    }
    
    private Date getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
    
    
    private RoomType getNextRankingRoomType(RoomType roomType) {
        Query query = em.createQuery("SELECT r FROM RoomType r WHERE r.ranking >=:ranking");
        Integer ranking = roomType.getRanking();
        query.setParameter("ranking", ranking);
        List<RoomType> roomTypes = query.getResultList();
        RoomType nextRoomType = roomType;
        
        for (RoomType r : roomTypes) {
            if (r.getRanking() > ranking && !r.isIsDisabled()) {
                nextRoomType = r;
                break;
            }
        }
        return nextRoomType;
    }
    
    
    private void createAllocationExceptionReport(RoomReservation roomReservation, AllocationExceptionReportTypeEnum allocationExceptionReportTypeEnum) {
        AllocationExceptionReport allocationExceptionReport = new AllocationExceptionReport(allocationExceptionReportTypeEnum);
        em.persist(allocationExceptionReport); 
        allocationExceptionReport.setRoomReservation(roomReservation);
        roomReservation.setAllocationExceptionReport(allocationExceptionReport);
    }
    
    private RoomReservation createRoomReservation(Room room, RoomType roomType) {
        RoomReservation roomReservation = new RoomReservation(room);
        em.persist(roomReservation); 
        //roomReservation.setRoomType(roomType);
        return roomReservation;
    }
    
    private RoomReservation createRoomReservation(RoomType roomType) {
        RoomReservation roomReservation = new RoomReservation();
        em.persist(roomReservation); 
        //roomReservation.setRoomType(roomType);
        return roomReservation;
    }

}
