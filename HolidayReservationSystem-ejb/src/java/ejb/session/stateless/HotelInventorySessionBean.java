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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jeremy
 */
@Stateless
public class HotelInventorySessionBean implements HotelInventorySessionBeanRemote, HotelInventorySessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    public HotelInventorySessionBean() {
    }
    
    @Override
    public HashMap<String, Integer> getAvailableRoomTypes(Date startDate, Date endDate) {
        List<Room> rooms = getAllRooms(); 
        List<Reservation> reservations = getReservationsForPeriod(startDate, endDate);
        
        HashMap<String, Integer> roomCount = new HashMap<String, Integer>(); //count the avail room for each room type
        
        for (Room room : rooms) {
            String roomTypeName = room.getRoomType().getName();
            roomCount.put(roomTypeName, roomCount.getOrDefault(roomTypeName, 0) + 1);
        }
        
        for (Reservation r : reservations) {
            String roomTypeName = r.getRoomType().getName();
            int roomsReserved = r.getNumRooms();
            int currentCount = roomCount.get(roomTypeName);
            roomCount.put(roomTypeName, currentCount - roomsReserved);
        }
        
        HashMap<String, Integer> availableRoomTypes = new HashMap<String, Integer>();
        
        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
            if (entry.getValue() > 0) {
                availableRoomTypes.put(entry.getKey(), entry.getValue());
            }
        }
        return availableRoomTypes;
    }
    
    private List<Room> getAllRooms() {
        Query query = em.createQuery("SELECT r FROM Room r");
        List<Room> rooms = query.getResultList();
        List<Room> availRooms = new ArrayList<>();
        
        for (Room r : rooms) {
            if (!r.isDisabled()) {
                availRooms.add(r);
            }
        }
        return availRooms;
    }
    
    private List<Reservation> getReservationsForPeriod(Date startDate, Date endDate) {
        Query query = em.createQuery("SELECT r FROM Reservation r");
        List<Reservation> reservations = query.getResultList();
        List<Reservation> periodReservations = new ArrayList<>();
        for (Reservation r : reservations) {
            if ((r.getStartDate().compareTo(startDate) >= 0 && r.getStartDate().compareTo(endDate) <= 0) ||
                (r.getEndDate().compareTo(endDate) <= 0 && r.getEndDate().compareTo(startDate) >= 0)) {
                periodReservations.add(r);
            }
        }
        return periodReservations;
    }
    
/*

    @Override
    public boolean roomTypeIsAvailableForReservation(Date startDate, Date endDate, Integer ranking) {
        //dynamically generate the inventory of the hotel to prevent sync errors
        //gets the two types of roomTypes for the ranking
        List<RoomType> roomTypes = getListOfRoomTypes(ranking);
        List<Room> rooms = getRoomsOfRoomType(roomTypes);
        List<Reservation> reservations = getReservationsForRoomType(roomTypes);
        int count = getCountOfAvailableRooms(reservations, startDate, endDate);
       
        //if more rooms than booking then can make reservation
        return rooms.size() > count;
    }
    
    @Override
    public int numberOfAvailableRoomsForReservation(Date startDate, Date endDate, Integer ranking) {
        //used to get the max number of reservations that can be made 
        List<RoomType> roomTypes = getListOfRoomTypes(ranking);
        List<Room> rooms = getRoomsOfRoomType(roomTypes);
        List<Reservation> reservations = getReservationsForRoomType(roomTypes);
        int count = getCountOfAvailableRooms(reservations, startDate, endDate);
       
        //if more rooms than booking then can make reservation
        return rooms.size() - count;
    }
    
    private List<Room> getRoomsOfRoomType(List<RoomType> roomTypes) {
        Query roomQuery = em.createQuery("SELECT r FROM Room r WHERE r.RoomType IN (:roomType1, :roomType2)");
        
        if (roomTypes.size() == 1) {
            roomQuery.setParameter("roomType1", roomTypes.get(0));
            roomQuery.setParameter("roomType2", roomTypes.get(0));
        } else if (roomTypes.size() == 2) {
            roomQuery.setParameter("roomType1", roomTypes.get(0));
            roomQuery.setParameter("roomType2", roomTypes.get(1));
        }

        //conducts the check for whether the room has been disabled
        List<Room> rooms = roomQuery.getResultList();
        for (Room room : rooms) {
            if (room.isDisabled()) {
                rooms.remove(room);
            }
        }
        return rooms;
    }

    private List<Reservation> getReservationsForRoomType(List<RoomType> roomTypes) {
        //gets all the reservations with that roomType 
        Query reservationQuery = em.createQuery("SELECT r FROM Reservation r WHERE r.roomType IN (:roomType1, :roomType2)"); 
        if (roomTypes.size() == 1) {
            reservationQuery.setParameter("roomType1", roomTypes.get(0));
            reservationQuery.setParameter("roomType2", roomTypes.get(0));
        } else if (roomTypes.size() == 2) {
            reservationQuery.setParameter("roomType1", roomTypes.get(0));
            reservationQuery.setParameter("roomType2", roomTypes.get(1));
        }
        List<Reservation> reservations = reservationQuery.getResultList();
        
        return reservations;
    }
    
    private int getCountOfAvailableRooms(List<Reservation> reservations, Date startDate, Date endDate) {
        int count = 0; 
        for (Reservation r : reservations) {
            if ((r.getStartDate().compareTo(startDate) >= 0 && r.getStartDate().compareTo(endDate) <= 0) ||
                (r.getEndDate().compareTo(endDate) <= 0 && r.getEndDate().compareTo(startDate) >= 0)) {
                count += r.getNumRooms();
            }
        }
        return count;
    }

/*
    private List<RoomType> getListOfRoomTypes(Integer ranking) {
        Query roomTypeQuery = em.createQuery("SELECT r FROM RoomType r WHERE r.ranking IN (:ranking, :rankingPlusOne)");
        roomTypeQuery.setParameter("ranking", ranking);
        roomTypeQuery.setParameter("rankingPlusOne", ranking + 1);
        
        List<RoomType> roomTypes = roomTypeQuery.getResultList();
        return roomTypes;
    }

/*
    public void allocateRoomsToCurrentDayReservations() {
        //need to update this when we have created a method to call the current date
        Query reservationQuery = em.createQuery("SELECT r FROM Reservation r WHERE r.startDate = :startDate");
        reservationQuery.setParameter("startDate", startDate);
        List<Reservation> reservations = reservationQuery.getResultList();
        
        for (Reservation r : reservations) {
            
        }
        
        
    }

*/
    
    private void createAllocationExceptionReport(RoomReservation r) {
        AllocationExceptionReport allocationExceptionReport = new AllocationExceptionReport(); 
        allocationExceptionReport.setRoomReservation(r);
    }

}
