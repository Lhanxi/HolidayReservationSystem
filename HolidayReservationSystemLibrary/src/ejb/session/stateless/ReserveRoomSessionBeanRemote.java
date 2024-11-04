/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Reservation;
import entity.RoomReservation;
import entity.RoomType;
import java.math.BigDecimal;
import javax.ejb.Remote;

/**
 *
 * @author jeremy
 */
@Remote
public interface ReserveRoomSessionBeanRemote {
    public Long createReservation(Reservation newReservation, RoomType roomType);
    public BigDecimal calculateReservationPriceForWalkIn(Reservation reservation, Long roomTypeId);
}
