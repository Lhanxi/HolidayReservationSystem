/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Room;
import entity.RoomType;
import java.util.List;
import javax.ejb.Remote;
import util.enumeration.RoomDeletionException;
import util.exception.RoomCreationException;

/**
 *
 * @author jeremy
 */
@Remote
public interface RoomSessionBeanRemote {
    public Long createNewRoom(Room room) throws RoomCreationException;

    public List<Room> viewAllRooms();

    public boolean isValidRoomNumber(String roomNumber);

    public Room getRoom(String roomNumber);
    public void updateRoom(Long roomId, RoomType roomType, String roomNumber, Boolean status, Boolean isDisabled);
    public void deleteRoom(String roomNumber) throws RoomDeletionException;
    public void updateRoomStatus(List<Room> rooms, Boolean roomStatus);
    public void checkOut(List<String> roomNumbers);
}
