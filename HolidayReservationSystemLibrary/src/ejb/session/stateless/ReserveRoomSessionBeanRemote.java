/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Partner;
import entity.Reservation;
import entity.RoomReservation;
import entity.RoomType;
import entity.Visitor;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jeremy
 */
@Remote
public interface ReserveRoomSessionBeanRemote {
    public Long createReservation(Reservation newReservation, RoomType roomType);
    public BigDecimal calculateReservationPriceForWalkIn(Long reservationId, Long roomTypeId);
    public Long createReservationForCustomer(Long visitorId, Reservation newReservation, RoomType roomType);
    public Long createReservationForPartner(Long partnerId, Reservation newReservation, RoomType roomType);
    public Long createReservation(Reservation newReservation, RoomType roomType, Long visitorId);
    public BigDecimal calculateReservationPriceForWalkIn(Reservation reservation, Long roomTypeId);
    public List<RoomReservation> getTodayRoomAllocation(List<Reservation> reservations);
}
