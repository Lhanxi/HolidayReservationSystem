/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import entity.Reservation;
import java.util.List;
import javax.ejb.Remote;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidCustomerCreationException;
import util.exception.InvalidLoginException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author leunghanxi
 */
@Remote
public interface CustomerSessionBeanRemote {
    public Long createNewCustomer(Customer newCustomer) throws InvalidCustomerCreationException;
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;
    public Customer customerLogin(String username, String password) throws InvalidLoginException;
    public Reservation retrieveReservationById(Long reservationId) throws ReservationNotFoundException;
    public List<Reservation> retrieveAllReservationByCustomerId(Long customerId);
}
