/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomType;
import java.util.List;
import javax.ejb.Local;
import util.exception.RoomTypeCreationException;

/**
 *
 * @author jeremy
 */
@Local
public interface RoomTypeSessionBeanLocal {
    public Long createNewRoomType(RoomType newRoomType, Integer ranking) throws RoomTypeCreationException ;
    public List<RoomType> getRoomTypeList();
    public void updateRoomTypeDetails(Long roomTypeId, String roomTypeName, String newDescription, String newSize, String newBedCapacity, String newAmenities, Integer ranking);
    public String deleteRoomType(Long roomTypeId);
    public List<RoomType> getEnabledRoomTypeList();
    public RoomType getRoomTypeByName(String roomTypeName);    
}
