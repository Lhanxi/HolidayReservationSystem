/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import javax.ejb.Stateless;
import entity.Customer;
import entity.Reservation;
import entity.Visitor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.InvalidLoginException;
import util.exception.InvalidCustomerCreationException;
import util.exception.CustomerNotFoundException;
import util.exception.ReservationNotFoundException;
import javax.ejb.EJB;
import util.exception.VisitorNotFoundException;


/**
 *
 * @author leunghanxi
 */
@Stateless
public class GuestSessionBean implements GuestSessionBeanRemote, GuestSessionBeanLocal {
    
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager entityManager;
    
    @EJB
    private ReserveRoomSessionBeanLocal reserveRoomSessionBean;
    
    public GuestSessionBean() {
    }
    
    @Override
    public Long createNewCustomer(Visitor newCustomer) throws InvalidCustomerCreationException {
        try {
            entityManager.persist(newCustomer);
            entityManager.flush();
            return newCustomer.getGuestId();
        } catch (PersistenceException ex) {
            throw new InvalidCustomerCreationException("Invalid customer creation. Please try again!");
        }
    }
    
    
    public Visitor createNewVisitor(Visitor newVisitor) throws InvalidCustomerCreationException {
       try {
            entityManager.persist(newVisitor);
            return newVisitor;
        } catch (PersistenceException ex) {
            throw new InvalidCustomerCreationException("Invalid passport number. Please try again!");
        }
    }
    
    @Override
    public Visitor visitorCheckIn(String name, String passportNumber) throws VisitorNotFoundException {
        Query query = entityManager.createQuery("SELECT v from Visitor v WHERE v.passportNumber = :inPassportNumber");
        query.setParameter("inPassportNumber", passportNumber);
        
        try {
            return (Visitor)query.getSingleResult();
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new VisitorNotFoundException("Visitor with passport number " + passportNumber + "does not exist");
        }
    }
    
    @Override
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException {
        Query query = entityManager.createQuery("SELECT c from Customer c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try {
            return (Customer)query.getSingleResult();
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new CustomerNotFoundException("Customer Username " + username + "does not exist");
        }
    }
    
    @Override
    public Visitor retrieveCustomerByPassport(String passportNumber) throws CustomerNotFoundException { 
        Query query = entityManager.createQuery("SELECT v from Visitor v WHERE v.passportNumber = :passportNumber");
        query.setParameter("passportNumber", passportNumber);
        try {
            Visitor visitor = (Visitor)query.getSingleResult();
            visitor.getReservations().size(); // load the reservations
            return visitor;
        } catch(NoResultException ex) {
            throw new CustomerNotFoundException("Customer with " + passportNumber + "does not exist");
        }
    }
    
    @Override 
    public Customer customerLogin(String username, String password) throws InvalidLoginException {
        try {
            Customer customer = this.retrieveCustomerByUsername(username);
            
            if (customer.getPassword().equals(password)) {
                return customer;
            } else {
                throw new InvalidLoginException("Username does not exist or invalid password!");
            }
        } catch(CustomerNotFoundException ex) {
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
    public List<Reservation> retrieveAllReservationByCustomerId(Long customerId) throws ReservationNotFoundException {
        try {
            Query query = entityManager.createQuery("SELECT r FROM Reservation r WHERE r.customer.guestId = :inGuestId");
            query.setParameter("inGuestId", customerId);

            List<Reservation> reservations = (List<Reservation>) query.getResultList();
            reservations.size();
            return reservations;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new ReservationNotFoundException("Reservations do not exist");
        }
    }
}