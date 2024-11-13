/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/EjbWebService.java to edit this template
 */
package ejb.session.ws;

import ejb.session.stateless.HotelInventorySessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.session.stateless.ReserveRoomSessionBeanLocal;
import ejb.session.stateless.RoomTypeSessionBeanLocal;
import ejb.session.stateless.RoomRateSessionBeanLocal;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.Partner;
import java.util.List;
import entity.Reservation;
import entity.RoomReservation;
import entity.RoomType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import util.exception.InvalidLoginException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author leunghanxi
 */
@WebService(serviceName = "HolidayReservationSystemWebService")
@Stateless()
public class HolidayReservationSystemWebService {

    @EJB(name = "ReserveRoomSessionBean")
    private ReserveRoomSessionBeanLocal reserveRoomSessionBeanLocal;

    @EJB(name = "HotelInventorySessionBeanLocal")
    private HotelInventorySessionBeanLocal hotelInventorySessionBeanLocal;

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;
    
    @EJB(name = "RoomTypeSessionBeanlocal")
    private RoomTypeSessionBeanLocal roomTypeSessionBeanLocal;
    
    @EJB(name = "RoomRateSessionBeanlocal")
    private RoomRateSessionBeanLocal roomRateSessionBeanLocal;
    
    @WebMethod(operationName = "doPartnerLogin")
    public Partner doPartnerLogin(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws InvalidLoginException {
        try {
            Partner partner = partnerSessionBeanLocal.partnerLogin(username, password);
            em.detach(partner);
            return partner;
        } catch(InvalidLoginException ex) {
            throw new InvalidLoginException("Username does not exist or invalid password!");
        }
    }
    
    @WebMethod(operationName = "searchRoom")
    public String[] searchRoom(@WebParam(name = "startDate") XMLGregorianCalendar startDate, 
                                 @WebParam(name = "endDate") XMLGregorianCalendar endDate) throws InvalidLoginException {
        Date startDateConverted = startDate.toGregorianCalendar().getTime();
        Date endDateConverted = endDate.toGregorianCalendar().getTime();

        HashMap<String, Integer> roomAvailability = hotelInventorySessionBeanLocal.getAvailableRoomTypes(startDateConverted, endDateConverted);

        String[] resultArray = new String[roomAvailability.size()]; 

        int index = 0;
        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            resultArray[index] = entry.getKey() + " " + entry.getValue().toString();  
            index++;
        }
        return resultArray;
    }
    
    @WebMethod(operationName = "retrieveRoomRateCost")
    public BigDecimal retrieveRoomRateCost(@WebParam(name = "startDate") XMLGregorianCalendar startDate, 
                                 @WebParam(name = "endDate") XMLGregorianCalendar endDate,
                                 @WebParam(name = "roomType") RoomType roomType,
                                 @WebParam(name = "numOfRooms") int noOfRooms) {
        Date startDateConverted = startDate.toGregorianCalendar().getTime();
        Date endDateConverted = endDate.toGregorianCalendar().getTime();

        BigDecimal totalAmount = roomRateSessionBeanLocal.calculateRoomRateAmount(roomType, startDateConverted, endDateConverted, noOfRooms);
        
        return totalAmount;
    }
    
    @WebMethod(operationName = "retrieveRoomType")
    public RoomType retrieveRoomType(@WebParam(name = "roomTypeName") String roomTypeName) {
        return roomTypeSessionBeanLocal.getRoomTypeByName(roomTypeName);
    }
    
    @WebMethod(operationName = "reserveRoom")
    public Long reserveRoom(@WebParam(name = "partnerId") Long partnerId, 
                            @WebParam(name = "startDate") XMLGregorianCalendar startDate,
                            @WebParam(name = "endDate") XMLGregorianCalendar endDate, 
                            @WebParam(name = "numOfRooms") Integer numOfRooms, 
                            @WebParam(name = "roomType") RoomType roomType) {
        Date startDateConverted = startDate.toGregorianCalendar().getTime();
        Date endDateConverted = endDate.toGregorianCalendar().getTime();
        Reservation newReservation = new Reservation(startDateConverted, endDateConverted, numOfRooms);
        return reserveRoomSessionBeanLocal.createReservationForPartner(partnerId, newReservation, roomType);
    }
    
    @WebMethod(operationName = "retrieveReservation")
    public Reservation retrieveReservation(@WebParam(name = "reservationId") Long reservationId) throws ReservationNotFoundException {
        try {
            Reservation reservation = partnerSessionBeanLocal.retrieveReservationById(reservationId);

            if (reservation.getRoomReservations() != null) {
                List<RoomReservation> roomReservations = new ArrayList<>(reservation.getRoomReservations());
                for (RoomReservation roomReservation : roomReservations) {
                    em.detach(roomReservation);
                }
                reservation.setRoomReservations(roomReservations);
            }

            if (reservation.getRoomType() != null) {
                em.detach(reservation.getRoomType());
            }

            em.detach(reservation);

            return reservation;

        } catch (ReservationNotFoundException ex) {
            throw new ReservationNotFoundException("Reservation ID " + reservationId + " does not exist");
        }
    }
    
    @WebMethod(operationName = "retrieveAllPartnerReservations")
    public Reservation[] retrieveAllPartnerReservations(@WebParam(name = "partnerId") Long partnerId) throws ReservationNotFoundException {
        try {
            List<Reservation> reservations = partnerSessionBeanLocal.retrieveAllReservationByPartnerId(partnerId);

            for (Reservation reservation : reservations) {
                if (reservation.getRoomReservations() != null) {
                    List<RoomReservation> roomReservations = new ArrayList<>(reservation.getRoomReservations());

                    for (RoomReservation roomReservation : roomReservations) {
                        em.detach(roomReservation);
                    }

                    reservation.setRoomReservations(roomReservations);
                }
                if (reservation.getRoomType() != null) {
                    em.detach(reservation.getRoomType());
                }
                em.detach(reservation);
            }

            return reservations.toArray(new Reservation[0]);

        } catch (ReservationNotFoundException ex) {
            throw new ReservationNotFoundException("Reservations do not exist");
        }
    }
}
