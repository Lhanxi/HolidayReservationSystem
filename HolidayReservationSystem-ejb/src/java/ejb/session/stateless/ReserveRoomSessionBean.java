/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Partner;
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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.enumeration.RateTypeEnum;
import util.exception.ReservationCreationException;

/**
 *
 * @author jeremy
 */
@Stateless
public class ReserveRoomSessionBean implements ReserveRoomSessionBeanRemote, ReserveRoomSessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    @EJB
    private RoomRateSessionBeanLocal roomRateSessionBeanLocal;
    
    public ReserveRoomSessionBean() {
    }
    
    @Override
    public Long createReservation(Reservation newReservation, RoomType roomType, Long visitorId, Long roomRateId) throws ReservationCreationException {
        try {
            //creates a new Reservation
            em.persist(newReservation); 
            newReservation.setRoomType(roomType); 
            RoomType r = newReservation.getRoomType();

            Visitor visitor = em.find(Visitor.class, visitorId);
            visitor.getReservations().add(newReservation);

            RoomRate roomRate = em.find(RoomRate.class, roomRateId);
            newReservation.addRoomRate(roomRate);
            em.flush();

            return newReservation.getReservationId();
        } catch (PersistenceException ex) {
            throw new ReservationCreationException("Missing an attribute");
        }
    }
    
    @Override
    public Long createReservationForCustomer(Long visitorId, Reservation newReservation, RoomType roomType, Date startDate, Date endDate) { 
        newReservation.setRoomType(roomType);
        List<RoomRate> roomRates = roomRateSessionBeanLocal.retrieveRoomRateByDate(startDate, endDate, roomType);
        for (RoomRate roomRate : roomRates) {
            newReservation.addRoomRate(roomRate);
        }
        em.persist(newReservation);
        em.flush();
        Visitor visitor = em.find(Visitor.class, visitorId);
        visitor.getReservations().add(newReservation);
        return newReservation.getReservationId();
    }
    
    @Override
    public Long createReservationForPartner(Long partnerId, Reservation newReservation, RoomType roomType, Date startDate, Date endDate) { 
        newReservation.setRoomType(roomType);
        List<RoomRate> roomRates = roomRateSessionBeanLocal.retrieveRoomRateByDate(startDate, endDate, roomType);
        for (RoomRate roomRate : roomRates) {
            newReservation.addRoomRate(roomRate);
        }
        em.persist(newReservation);
        em.flush();
        Partner partner = em.find(Partner.class, partnerId);
        partner.getReservations().add(newReservation);
        return newReservation.getReservationId();
    }
/*
    @Override
    public BigDecimal calculateReservationPriceForWalkIn(Long reservationId, Long roomTypeId) {
        Reservation reservation = em.find(Reservation.class, reservationId);
        RoomType roomType = reservation.getRoomType();
       
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
    
 */
    
    public BigDecimal getPublishedRoomRate(RoomType roomType) throws NoResultException {
        Query query = em.createQuery("SELECT r FROM RoomRate r WHERE r.roomType=:roomType AND r.rateTypeEnum =:rateTypeEnum");
        query.setParameter("roomType", roomType); 
        query.setParameter("rateTypeEnum", RateTypeEnum.PUBLISHED);
        List<RoomRate> roomRates = query.getResultList();
        
        if (roomRates.isEmpty()) {
            throw new NoResultException("No matching RoomRate found for published rates of this room tyepe.");
        }
        
        return roomRates.get(0).getRoomRateAmount();
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
    
    @Override
    public List<Reservation> getReservationsOfDate(Date todayDate) {
        Query query = em.createQuery("SELECT r FROM Reservation r");
        List<Reservation> reservations = query.getResultList();

        //get today's allocations of RoomReservations
        List<Reservation> todayReservations = new ArrayList<Reservation>();
        for (Reservation r: reservations) {
            if (r.getStartDate().equals(todayDate)) {
                todayReservations.add(r); 
                r.getRoomReservations().size(); //eager fetching to get the room type of room reservations later on 

            }
        }
        return todayReservations;
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
