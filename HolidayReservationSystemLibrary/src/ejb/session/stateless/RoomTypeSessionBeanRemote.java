/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jeremy
 */
@Remote
public interface RoomTypeSessionBeanRemote {
    public Long createNewRoomType(RoomType roomType);
    public List<RoomType> getRoomTypeList();
    public void updateRoomTypeDetails(Long roomTypeId, String roomTypeName, String newDescription, String newSize, String newBedCapacity, String newAmenities);
    public String deleteRoomType(Long roomTypeId);
    public List<RoomType> getEnabledRoomTypeList();
}
