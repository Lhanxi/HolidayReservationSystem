/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.sessions.stateless;

import entity.Room;
import entity.RoomType;
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
public class RoomSessionBean implements RoomSessionBeanRemote, RoomSessionBeanLocal {
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    
    public RoomSessionBean() {
    }
    

    @Override
    public Long createNewRoom(Room room) {
        em.persist(room); 
        em.flush();
        return room.getRoomId();
    }
    
    @Override
    public List<Room> viewAllRooms() {
        Query query = em.createQuery("SELECT r FROM Room r"); 
        return query.getResultList();
    }
    
    @Override
    public boolean isValidRoomNumber(String roomNumber) {
        Query query = em.createQuery("SELECT r FROM Room r WHERE r.roomNumber = :roomNumber");
        query.setParameter("roomNumber", roomNumber);
        return query.getResultList().isEmpty();
    }
    
    @Override
    public Room getRoom(String roomNumber) {
        Query query = em.createQuery("SELECT r FROM Room r WHERE r.roomNumber = :roomNumber");
        Room room = (Room) query.getSingleResult();
        return room;
    }
    
    @Override
    public void updateRoom(Room room, RoomType roomType, String roomNumber, Boolean status) {
        //unidirectional so dont need to update room in roomType
        room.setRoomType(roomType);
        room.setRoomNumber(roomNumber);
        room.setRoomStatus(status);
    }
    
}
