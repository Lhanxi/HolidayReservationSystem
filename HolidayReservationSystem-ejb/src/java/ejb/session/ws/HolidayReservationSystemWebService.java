/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/EjbWebService.java to edit this template
 */
package ejb.session.ws;

import ejb.session.stateless.HotelInventorySessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.session.stateless.ReserveRoomSessionBeanLocal;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.Partner;
import java.util.List;
import java.util.ArrayList;
import entity.Reservation;
import entity.RoomReservation;

/**
 *
 * @author leunghanxi
 */
@WebService(serviceName = "HolidayReservationSystemWebService")
@Stateless()
public class HolidayReservationSystemWebService {

    @EJB
    private ReserveRoomSessionBeanLocal reserveRoomSessionBean;

    @EJB(name = "HotelInventorySessionBeanLocal")
    private HotelInventorySessionBeanLocal hotelInventorySessionBeanLocal;

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @EJB(name = "PartnerSessionBeanLocal")
    private PartnerSessionBeanLocal partnerSessionBeanLocal;
    /**
     * This is a sample web service operation
     */
    /**
     * @return 
    @WebMethod(operationName = "doLogin")
    public Partner doLogin() {
        return "Hello " + " !";
    }
    **/
    
    @WebMethod(operationName = "retrieveAllPartnerReservations")
    public List<Reservation> retrieveAllPartnerReservations(@WebParam(name = "partnerId") Long partnerId) {
        List<Reservation> reservations = partnerSessionBeanLocal.retrieveAllReservationByPartnerId(partnerId);
        
        for (Reservation reservation : reservations) {
            if (reservation.getRoomReservations() != null) {
                
                List<RoomReservation> roomReservations = reservation.getRoomReservations(); 
                roomReservations.size();
                em.detach(roomReservations);
            }
            if (reservation.getRoomType() != null) {
                em.detach(reservation.getRoomType());
            }
            em.detach(reservation);
        }
        return reservations;
    }
}
