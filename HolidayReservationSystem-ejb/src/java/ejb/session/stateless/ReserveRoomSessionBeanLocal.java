/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Partner;
import entity.Reservation;
import entity.RoomReservation;
import entity.RoomType;
import entity.Visitor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.NoResultException;
import util.exception.ReservationCreationException;

/**
 *
 * @author jeremy
 */
@Local
public interface ReserveRoomSessionBeanLocal {
    public Long createReservationForCustomer(Long visitorId, Reservation newReservation, RoomType roomType, Date startDate, Date endDate);
    public Long createReservationForPartner(Long partnerId, Reservation newReservation, RoomType roomType, Date startDate, Date endDate);
    public List<RoomReservation> getTodayRoomAllocation(List<Reservation> reservations);
    public BigDecimal getPublishedRoomRate(RoomType roomType) throws NoResultException;
    public Long createReservation(Reservation newReservation, RoomType roomType, Long visitorId, Long roomRateId) throws ReservationCreationException;
}
