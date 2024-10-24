/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.sessions.stateless;

import entity.Reservation;
import entity.Room;
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

    //dynamically generate the inventory of the hotel to prevent sync errors
    public boolean availableRoomsForReservation(Date startDate, Date endDate, RoomType roomType) {
        Query roomQuery = em.createQuery("SELECT r FROM Room r WHERE r.roomType =:roomType");
        roomQuery.setParameter("roomType", roomType);
        
        //handles the case where there are no rooms of that room type in the first place
        if (roomQuery.getResultList().isEmpty()) {
            return false;
        }
     
        //conducts the check for whether the room has been disabled
        List<Room> rooms = roomQuery.getResultList();
        for (Room room : rooms) {
            if (room.getIsDisabled()) {
                rooms.remove(room);
            }
        }
        
        Query reservationQuery = em.createQuery("SELECT r FROM Reservation r WHERE r.roomType =:roomType"); 
        reservationQuery.setParameter("roomType", roomType); 
        List<Reservation> reservations = reservationQuery.getResultList();
        
        int count = 0; 
        for (Reservation r : reservations) {
            if ((r.getStartDate().compareTo(startDate) >= 0 && r.getStartDate().compareTo(endDate) <= 0) ||
                (r.getEndDate().compareTo(endDate) <= 0 && r.getEndDate().compareTo(startDate) >= 0)) {
                count += 1;
            }
        }
        
        //if more rooms than booking then can make reservation
        return rooms.size() > count;
        
    }


}
