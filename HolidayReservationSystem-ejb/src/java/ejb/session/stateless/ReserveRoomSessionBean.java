/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Reservation;
import entity.RoomRate;
import entity.RoomReservation;
import entity.RoomType;
import entity.Visitor;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    public Long createReservation(Reservation newReservation, RoomType roomType, Long visitorId) {
        //creates a new Reservation
        em.persist(newReservation); 
        newReservation.setRoomType(roomType); 
        RoomType r = newReservation.getRoomType();
        
        Visitor visitor = em.find(Visitor.class, visitorId);
        visitor.getReservations().add(newReservation);
        
        return r.getRoomTypeId();
    }
    
    @Override
    public BigDecimal calculateReservationPriceForWalkIn(Reservation reservation, Long roomTypeId) {
        RoomType roomType = em.find(RoomType.class, roomTypeId);
       
        RateTypeEnum rateTypeEnum = RateTypeEnum.PUBLISHED;

        Query query = em.createQuery("SELECT r FROM RoomRate r WHERE r.roomType = :roomType AND r.rateTypeEnum = :rateTypeEnum");
        query.setParameter("roomType", roomType);
        query.setParameter("rateTypeEnum", rateTypeEnum);
        List<RoomRate> roomRates = query.getResultList();
        if (roomRates.isEmpty()) {
            throw new NoResultException("No matching RoomRate found for the given RoomType and RateTypeEnum.");
        }
        RoomRate roomRate = roomRates.get(0);
        BigDecimal publishedRate = roomRate.getRoomRateAmount();
        
        BigDecimal numOfRooms = BigDecimal.valueOf(reservation.getNumRooms());
        BigDecimal numOfDays = BigDecimal.valueOf(calculateDaysBetween(reservation.getStartDate(), reservation.getEndDate()) - 1);
        
        return numOfRooms.multiply(numOfDays).multiply(publishedRate);

    }
/*
    private long calculateDaysBetween(Date startDate, Date endDate) {
        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Calculate days between inclusive
        return ChronoUnit.DAYS.between(start, end) + 1;
    }
  */  
    private long calculateDaysBetween(Date startDate, Date endDate) {
    // Convert java.sql.Date to LocalDate by using getTime() and Instant.ofEpochMilli()
    LocalDate start = Instant.ofEpochMilli(startDate.getTime())
                             .atZone(ZoneId.systemDefault())
                             .toLocalDate();
    LocalDate end = Instant.ofEpochMilli(endDate.getTime())
                           .atZone(ZoneId.systemDefault())
                           .toLocalDate();

    // Calculate days between, inclusive
    return ChronoUnit.DAYS.between(start, end) + 1;
    }
    
    @Override
    public List<RoomReservation> getTodayRoomAllocation(List<Reservation> reservations) {
        List<RoomReservation> roomReservations = new ArrayList<RoomReservation>(); 
        
        for (Reservation r : reservations) {
            Reservation reservation = em.find(Reservation.class, r.getReservationId());
            List<RoomReservation> rooms = reservation.getRoomReservations();
            
            for (RoomReservation rr : rooms) {
                roomReservations.add(rr);
            }
        }
        
        return roomReservations;
    }


    /* 

    private long calculateDaysBetween(Date startDate, Date endDate) {
        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        // Calculate days between inclusive
        return ChronoUnit.DAYS.between(start, end) + 1;
    }
         
*/
    }
