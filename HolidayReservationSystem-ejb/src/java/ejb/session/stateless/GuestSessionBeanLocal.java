/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Customer;
import entity.Reservation;
import entity.Visitor;
import java.util.List;
import javax.ejb.Local;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidCustomerCreationException;
import util.exception.InvalidLoginException;
import util.exception.ReservationNotFoundException;

/**
 *
 * @author leunghanxi
 */
@Local
public interface GuestSessionBeanLocal {
    public Long createNewCustomer(Customer newCustomer) throws InvalidCustomerCreationException;
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;
    public Customer customerLogin(String username, String password) throws InvalidLoginException;
    public Reservation retrieveReservationById(Long reservationId) throws ReservationNotFoundException;
    public List<Reservation> retrieveAllReservationByCustomerId(Long customerId);

    public Visitor retrieveCustomerByPassport(String passportNumber) throws CustomerNotFoundException;

    public Visitor createNewVisitor(Visitor newVisitor) throws InvalidCustomerCreationException;
}
