/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Reservation;
import entity.RoomReservation;
import entity.RoomType;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jeremy
 */
@Local
public interface ReserveRoomSessionBeanLocal {
    public Long createReservation(Reservation newReservation, RoomType roomType, Long visitorId);
    public BigDecimal calculateReservationPriceForWalkIn(Reservation reservation, Long roomTypeId);
    public List<RoomReservation> getTodayRoomAllocation(List<Reservation> reservations);
}
