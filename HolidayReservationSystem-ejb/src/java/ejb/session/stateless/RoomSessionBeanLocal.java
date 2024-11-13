/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Room;
import entity.RoomType;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.RoomDeletionException;

/**
 *
 * @author jeremy
 */
@Local
public interface RoomSessionBeanLocal {

    public Long createNewRoom(Room room);

    public List<Room> viewAllRooms();

    public boolean isValidRoomNumber(String roomNumber);

    public Room getRoom(String roomNumber);

    public void deleteRoom(String roomNumber) throws RoomDeletionException;

    public void updateRoom(Long roomId, RoomType roomType, String roomNumber, Boolean status, Boolean isDisabled);
    public void updateRoomStatus(List<Room> rooms, Boolean roomStatus);

    public void checkOut(List<String> roomNumbers);
    
}
