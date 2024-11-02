/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Reservation;
import entity.RoomReservation;
import entity.RoomType;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author jeremy
 */
@Local
public interface ReserveRoomSessionBeanLocal {
    public void createReservation(Reservation newReservation, RoomType roomType);

    public BigDecimal calculateReservationPriceForWalkIn(Reservation reservation);
}
