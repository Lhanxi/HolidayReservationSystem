/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;
import util.exception.GuestNotFoundException;
import util.exception.InvalidLoginException;

/**
 *
 * @author leunghanxi
 */
@Local
public interface GuestSessionBeanLocal {
    public Long createNewGuest(Customer newGuest);
    
    public Customer retrieveGuestByUsername(String username) throws GuestNotFoundException;
    
    public Customer guestLogin(String username, String password) throws InvalidLoginException;
}
