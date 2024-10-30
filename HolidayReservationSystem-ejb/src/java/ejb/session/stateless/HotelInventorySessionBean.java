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
import java.util.Date;
import java.util.List;
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
    public boolean roomTypeIsAvailableForReservation(Date startDate, Date endDate, Integer ranking) {
        //dynamically generate the inventory of the hotel to prevent sync errors
        List<RoomType> roomTypes = getListOfRoomTypes(ranking);
        List<Room> rooms = getRoomsOfRoomType(roomTypes);
        List<Reservation> reservations = getReservationsForPeriod(startDate, endDate, roomTypes);
        int count = getCountOfAvailableRooms(reservations, startDate, endDate);
       
        //if more rooms than booking then can make reservation
        return rooms.size() > count;
    }
    
    @Override
    public int numberOfAvailableRoomsForReservation(Date startDate, Date endDate, Integer ranking) {
        //used to get the max number of reservations that can be made 
        List<RoomType> roomTypes = getListOfRoomTypes(ranking);
        List<Room> rooms = getRoomsOfRoomType(roomTypes);
        List<Reservation> reservations = getReservationsForPeriod(startDate, endDate, roomTypes);
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

    private List<Reservation> getReservationsForPeriod(Date startDate, Date endDate, List<RoomType> roomTypes) {
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
