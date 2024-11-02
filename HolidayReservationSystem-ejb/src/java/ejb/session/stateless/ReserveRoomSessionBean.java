/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Reservation;
import entity.RoomRate;
import entity.RoomType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.RateTypeEnum;

/**
 *
 * @author jeremy
 */
@Stateless
public class ReserveRoomSessionBean implements ReserveRoomSessionBeanRemote, ReserveRoomSessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    
    public ReserveRoomSessionBean() {
    }

    @Override
    public void createReservation(Reservation newReservation, RoomType roomType) {
        //creates a new Reservation
        em.persist(newReservation); 
        newReservation.setRoomType(roomType); 
    }
    
    @Override
    public BigDecimal calculateReservationPriceForWalkIn(Reservation reservation) {
        RoomType roomType = reservation.getRoomType();
        
        Query query = em.createQuery("SELECT r FROM RoomRate r WHERE r.roomType = :roomType AND r.rateTypeEnum = :rateTypeEnum");
        query.setParameter("roomType", roomType);
        query.setParameter("rateTypeEnum", RateTypeEnum.PUBLISHED);
        RoomRate roomRate = (RoomRate) query.getSingleResult();
        BigDecimal publishedRate = roomRate.getRoomRateAmount();
        
        BigDecimal numOfRooms = BigDecimal.valueOf(reservation.getNumRooms());
        BigDecimal numOfDays = BigDecimal.valueOf(calculateDaysBetween(reservation.getStartDate(), reservation.getEndDate()));
        
        return numOfRooms.multiply(numOfDays).multiply(publishedRate);

    }

    private long calculateDaysBetween(Date startDate, Date endDate) {
        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        // Calculate days between inclusive
        return ChronoUnit.DAYS.between(start, end) + 1;
    }
            
}
