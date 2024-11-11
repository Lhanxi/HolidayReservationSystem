/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Partner;
import entity.Reservation;
import entity.RoomType;
import entity.Visitor;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author jeremy
 */
@Local
public interface ReserveRoomSessionBeanLocal {
    public Long createReservation(Reservation newReservation, RoomType roomType);
    public BigDecimal calculateReservationPriceForWalkIn(Long reservationId, Long roomTypeId);
    public Long createReservationForCustomer(Long visitorId, Reservation newReservation, RoomType roomType);
    public Long createReservationForPartner(Long partnerId, Reservation newReservation, RoomType roomType);
}
