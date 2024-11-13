/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Room;
import entity.RoomReservation;
import entity.RoomType;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.RoomDeletionException;

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
        query.setParameter("roomNumber", roomNumber);
        Room room = (Room) query.getSingleResult();
        return room;
    }
    
    @Override
    public void updateRoom(Long roomId, RoomType roomType, String roomNumber, Boolean status, Boolean isDisabled) {
        Room room = em.find(Room.class, roomId);
        //unidirectional so dont need to update room in roomType
        room.setRoomType(roomType);
        room.setRoomNumber(roomNumber);
        room.setRoomStatus(status);
        room.setIsDisabled(isDisabled);
    }
    
    @Override
    public void updateRoomStatus(List<Room> rooms, Boolean roomStatus) {
        for (Room r : rooms) {
            Room room = em.find(Room.class, r.getRoomId()); 
            room.setRoomStatus(roomStatus); 
        }
    }
    
    @Override
    public void checkOut(List<String> roomNumbers) {
        Query query = em.createQuery("SELECT r FROM Room r WHERE r.roomNumber IN :roomNumbers");
        query.setParameter("roomNumbers", roomNumbers);
        List<Room> rooms = query.getResultList();
        System.out.println("rooms length:" + rooms.size());
        
        updateRoomStatus(rooms, true);
        checkOutRoomReservations(roomNumbers);
    }
    
    private void checkOutRoomReservations(List<String> roomNumbers) {
        //this is necessary to remove the foreign key of room in room reservation, allows the rooms to be deleted after they are not in use
        Query query = em.createQuery("SELECT r FROM RoomReservation r WHERE r.room.roomNumber IN :roomNumbers"); 
        query.setParameter("roomNumbers", roomNumbers);
        List<RoomReservation> roomReservations = query.getResultList(); 
        
        for (RoomReservation r : roomReservations) {
            r.setRoom(null);
        }
    }
     
    
    
    @Override
    public void deleteRoom(String roomNumber) throws RoomDeletionException {
        Query query = em.createQuery("SELECT r FROM Room r WHERE r.roomNumber = :roomNumber"); 
        query.setParameter("roomNumber", roomNumber); 
        Room room = (Room) query.getSingleResult();
        
        Query queryRoomReservations = em.createQuery("SELECT r FROM RoomReservation r WHERE r.room =:room");
        queryRoomReservations.setParameter("room", room); 
        
        if (queryRoomReservations.getResultList().size() == 0) {
            em.remove(room);
        } else {
            throw new RoomDeletionException("Room cannot be deleted, set to disabled");
        }
    }
}