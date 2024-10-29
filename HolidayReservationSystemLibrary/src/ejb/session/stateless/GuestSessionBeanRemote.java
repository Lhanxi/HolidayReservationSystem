/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Remote;
import util.exception.GuestNotFoundException;
import util.exception.InvalidLoginException;

/**
 *
 * @author leunghanxi
 */
@Remote
public interface GuestSessionBeanRemote {
    public Long createNewGuest(Customer newGuest);
    
    public Customer retrieveGuestByUsername(String username) throws GuestNotFoundException;
    
    public Customer guestLogin(String username, String password) throws InvalidLoginException;
}
