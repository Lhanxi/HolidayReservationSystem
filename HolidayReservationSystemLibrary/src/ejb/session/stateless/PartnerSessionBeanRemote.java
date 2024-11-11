/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Partner;
import entity.Reservation;
import java.util.List;
import javax.ejb.Remote;
import util.exception.InvalidLoginException;
import util.exception.InvalidPartnerCreationException;
import util.exception.PartnerNotFoundException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author leunghanxi
 */
@Remote
public interface PartnerSessionBeanRemote {
    public Long createNewPartner(Partner partner) throws InvalidPartnerCreationException;
    public List<Partner> retrieveListOfAllPartners();
    public Partner retrievePartnerByUsername(String username) throws PartnerNotFoundException;
    public Partner partnerLogin(String username, String password) throws InvalidLoginException;
    public Reservation retrieveReservationById(Long reservationId) throws ReservationNotFoundException;
    public List<Reservation> retrieveAllReservationByPartnerId(Long partnerId) throws ReservationNotFoundException;
}
