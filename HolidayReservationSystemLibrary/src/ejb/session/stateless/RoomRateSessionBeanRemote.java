/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomRate;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jeremy
 */
@Remote
public interface RoomRateSessionBeanRemote {
    public Long createNewRoomRate(RoomRate roomRate);
    public List<RoomRate> getAllRoomRates();
}
