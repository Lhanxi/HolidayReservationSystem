/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import javax.ejb.Stateless;
import java.util.List;
import javax.ejb.Stateless;
import entity.Partner;
import entity.Reservation;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.PartnerNotFoundException;
import util.exception.InvalidPartnerCreationException;
import util.exception.InvalidLoginException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author leunghanxi
 */
@Stateless
public class PartnerSessionBean implements PartnerSessionBeanRemote, PartnerSessionBeanLocal {
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager entityManager;
    
    public PartnerSessionBean() {
    }
    
    @Override
    public Long createNewPartner(Partner partner) throws InvalidPartnerCreationException {
        try {
            entityManager.persist(partner);
            entityManager.flush();
            return partner.getPartnerId();
        } catch (PersistenceException ex) {
            throw new InvalidPartnerCreationException("Invalid partner creation. Please try again!");
        }
    }
    
    @Override
    public List<Partner> retrieveListOfAllPartners() {
        return entityManager.createQuery("SELECT p from Partner p").getResultList();
    }
    
    @Override
    public Partner retrievePartnerByUsername(String username) throws PartnerNotFoundException {
        Query query = entityManager.createQuery("SELECT P from Partner p WHERE p.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try {
            return (Partner)query.getSingleResult();
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new PartnerNotFoundException("Partner Username " + username + "does not exist");
        }
    }
    
    @Override 
    public Partner partnerLogin(String username, String password) throws InvalidLoginException {
        try {
            Partner partner = this.retrievePartnerByUsername(username);
            
            if (partner.getPassword().equals(password)) {
                return partner;
            } else {
                throw new InvalidLoginException("Username does not exist or invalid password!");
            }
        } catch(PartnerNotFoundException ex) {
            throw new InvalidLoginException("Username does not exist or invalid password!");
        }
    }
    
    @Override
    public Reservation retrieveReservationById(Long reservationId) throws ReservationNotFoundException {
        Reservation reservation = entityManager.find(Reservation.class, reservationId);
        if (reservation != null) {
            return reservation;
        } else {
            throw new ReservationNotFoundException("Reservation ID " + reservationId + " does not exist");
        }
    }
    
    @Override
    public List<Reservation> retrieveAllReservationByPartnerId(Long partnerId) throws ReservationNotFoundException {
        try {
            Query query = entityManager.createQuery("SELECT r FROM Reservation r WHERE r.partner.partnerId = :inPartnerId");
            query.setParameter("inPartnerId", partnerId);

            List<Reservation> reservations = (List<Reservation>) query.getResultList();
            reservations.size();
            return reservations;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new ReservationNotFoundException("Reservations do not exist");
        }
    }
} 
