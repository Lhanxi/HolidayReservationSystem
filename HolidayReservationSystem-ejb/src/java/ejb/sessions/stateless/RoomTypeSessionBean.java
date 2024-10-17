/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.sessions.stateless;

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
public class RoomTypeSessionBean implements RoomTypeSessionBeanRemote, RoomTypeSessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    public RoomTypeSessionBean() {
    }
    
    @Override
    public Long createNewRoomType(RoomType roomType) {
        em.persist(roomType); 
        em.flush();
        return roomType.getRoomTypeId();
    }
    
    @Override
    public List<RoomType> getRoomTypeList() {
        Query query = em.createQuery("SELECT r FROM RoomType r"); 
        
        return query.getResultList();
    }
    
    public void updateRoomTypeDetails(Long roomTypeId, String roomTypeName, String newDescription, String newSize, String newBedCapacity, String newAmenities) {
        RoomType roomType = em.find(RoomType.class, roomTypeId); 
        roomType.setName(roomTypeName);
        roomType.setDescription(newDescription);
        roomType.setSize(newSize);
        roomType.setBedCapacity(newBedCapacity);
        roomType.setAmenities(newAmenities);
    }
    

}
