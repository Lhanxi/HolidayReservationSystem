/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Room;
import entity.RoomType;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jeremy
 */
@Remote
public interface RoomSessionBeanRemote {
    public Long createNewRoom(Room room);

    public List<Room> viewAllRooms();

    public boolean isValidRoomNumber(String roomNumber);

    public Room getRoom(String roomNumber);
    public void updateRoom(Long roomId, RoomType roomType, String roomNumber, Boolean status, Boolean isDisabled);
    public String deleteRoom(String roomNumber);
}
