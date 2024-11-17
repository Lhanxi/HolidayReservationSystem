/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Room;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jeremy
 */
@Local
public interface HotelInventorySessionBeanLocal {
    public HashMap<String, Integer> getAvailableRoomTypes(Date startDate, Date endDate);

    public List<Room> getAllEnabledRooms();

    public List<Room> getRoomsForAllocation(Date startDate);
}