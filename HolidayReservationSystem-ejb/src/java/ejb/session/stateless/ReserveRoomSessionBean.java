/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Reservation;
import entity.RoomType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jeremy
 */
@Stateless
public class ReserveRoomSessionBean implements ReserveRoomSessionBeanRemote, ReserveRoomSessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    
    public ReserveRoomSessionBean() {
    }

    @Override
    public void reserveRooms(Reservation newReservation, RoomType roomType) {
        //creates a new Reservation
        em.persist(newReservation); 
        newReservation.setRoomType(roomType); 
        
    }

            
}
