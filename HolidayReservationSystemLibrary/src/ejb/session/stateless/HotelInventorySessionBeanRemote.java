/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Room;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jeremy
 */
@Remote
public interface HotelInventorySessionBeanRemote {
    public HashMap<String, Integer> getAvailableRoomTypes(Date startDate, Date endDate);
    public List<Room> getAllEnabledRooms();
    public List<Room> getRoomsForAllocation(Date startDate);
}
