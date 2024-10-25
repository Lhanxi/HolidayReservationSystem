/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.sessions.stateless;

import entity.RoomType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jeremy
 */
@Local
public interface RoomTypeSessionBeanLocal {
    public Long createNewRoomType(RoomType roomType);
    public List<RoomType> getRoomTypeList();
    public void updateRoomTypeDetails(Long roomTypeId, String roomTypeName, String newDescription, String newSize, String newBedCapacity, String newAmenities);
    public String deleteRoomType(RoomType roomType);
    
}
