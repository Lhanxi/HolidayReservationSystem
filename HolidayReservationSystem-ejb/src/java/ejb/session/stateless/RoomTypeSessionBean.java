/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomType;
import java.util.Iterator;
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
public class RoomTypeSessionBean implements RoomTypeSessionBeanRemote, RoomTypeSessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    public RoomTypeSessionBean() {
    }
    
    @Override
    public Long createNewRoomType(RoomType roomType) {
    try {
        em.persist(roomType);
        em.flush(); // Explicitly flush to push changes immediately to the database
        System.out.println("RoomType created with ID: " + roomType.getRoomTypeId());
    } catch (Exception ex) {
        ex.printStackTrace();
        System.out.println("Error persisting RoomType: " + ex.getMessage());
    }
    return roomType.getRoomTypeId();
}

    @Override
    public List<RoomType> getRoomTypeList() {
        Query query = em.createQuery("SELECT r FROM RoomType r"); 
        return query.getResultList();
    }
    
    @Override
    public List<RoomType> getEnabledRoomTypeList() {
        List<RoomType> roomTypes = getRoomTypeList(); 
        Iterator<RoomType> iterator = roomTypes.iterator();

        while (iterator.hasNext()) {
            RoomType roomType = iterator.next();
            if (roomType.isIsDisabled()) {
                iterator.remove();  // Safely removes the element
            }
        }

    return roomTypes;
}

    
    public void updateRoomTypeDetails(Long roomTypeId, String roomTypeName, String newDescription, String newSize, String newBedCapacity, String newAmenities) {
        RoomType roomType = em.find(RoomType.class, roomTypeId); 
        roomType.setName(roomTypeName);
        roomType.setDescription(newDescription);
        roomType.setSize(newSize);
        roomType.setBedCapacity(newBedCapacity);
        roomType.setAmenities(newAmenities);
    }
    
    @Override
    public String deleteRoomType(Long roomTypeId) {
        //check whether there are any rooms that depend on this roomType
        RoomType roomType = em.find(RoomType.class, roomTypeId);
        if (checkRoomTypeForRooms(roomType) && checkRoomTypeForRoomRates(roomType)) {
            em.remove(roomType);
            return "Room Type successfully deleted";
        } else {
            roomType.setIsDisabled(true);
        }
        return "Room Type deletion failed, Room Type is being used. Room Type has been disabled.";
    }
    
    private boolean checkRoomTypeForRooms(RoomType roomType) {
        Query query = em.createQuery("SELECT r FROM Room r WHERE r.roomType = :roomType"); 
        query.setParameter("roomType", roomType);
        return query.getResultList().isEmpty();
    } 
    
    //for the case where there might not be any rooms that are currently using a room type
    private boolean checkRoomTypeForRoomRates(RoomType roomType) {
        Query query = em.createQuery("SELECT r FROM RoomRate r WHERE r.roomType = :roomType"); 
        query.setParameter("roomType", roomType);
        return query.getResultList().isEmpty();
    }
    
    @Override
    public RoomType getRoomTypeByName(String roomTypeName) {
        Query query = em.createQuery("SELECT r FROM RoomType r WHERE r.name =:roomTypeName");
        query.setParameter("roomTypeName", roomTypeName);
        return (RoomType) query.getSingleResult();
    }

}