/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import javax.ejb.Stateless;
import java.util.List;
import entity.Guest;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import util.exception.GuestNotFoundException;
import util.exception.InvalidLoginException;

/**
 *
 * @author leunghanxi
 */
@Stateless
public class GuestSessionBean implements GuestSessionBeanRemote, GuestSessionBeanLocal {
    
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager entityManager;
    
    public GuestSessionBean() {
    }
    
    @Override
    public Long createNewGuest(Guest newGuest) {
        entityManager.persist(newGuest);
        entityManager.flush();
        return newGuest.getGuestId();
    }
    
    @Override
    public Guest retrieveGuestByUsername(String username) throws GuestNotFoundException {
        Query query = entityManager.createQuery("SELECT g from Guest g WHERE g.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try {
            return (Guest)query.getSingleResult();
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new GuestNotFoundException("Guest Username " + username + "does not exist");
        }
            
    }
    
    @Override 
    public Guest guestLogin(String username, String password) throws InvalidLoginException {
        try {
            Guest guest = this.retrieveGuestByUsername(username);
            
            if (guest.getPassword().equals(password)) {
                return guest;
            } else {
                throw new InvalidLoginException("Username does not exist or invalid password!");
            }
        } catch(GuestNotFoundException ex) {
            throw new InvalidLoginException("Username does not exist or invalid password!");
        }
    }
    
}
