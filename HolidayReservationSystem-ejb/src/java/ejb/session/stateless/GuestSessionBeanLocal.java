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
import util.exception.VisitorNotFoundException;

/**
 *
 * @author leunghanxi
 */
@Local
public interface GuestSessionBeanLocal {
    public Long createNewCustomer(Visitor newCustomer) throws InvalidCustomerCreationException;
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;
    public Customer customerLogin(String username, String password) throws InvalidLoginException;
    public Visitor visitorCheckIn(String name, String passportNumber) throws VisitorNotFoundException;
    public Reservation retrieveReservationById(Long reservationId) throws ReservationNotFoundException;
    public List<Reservation> retrieveAllReservationByCustomerId(Long customerId) throws ReservationNotFoundException;
    public Visitor retrieveCustomerByPassport(String passportNumber) throws VisitorNotFoundException;
    public Visitor createNewVisitor(Visitor newVisitor) throws InvalidCustomerCreationException;

    public Visitor receiveCustomerById(Long customerId);
}
