/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import java.util.List;
import javax.ejb.Stateless;
import entity.Employee;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginException;
/**
 *
 * @author leunghanxi
 */
@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanRemote, EmployeeSessionBeanLocal {
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager entityManager;
    
    public EmployeeSessionBean() {
    }
    
    @Override
    public Long createNewEmployee(Employee newEmployee) {
        entityManager.persist(newEmployee);
        entityManager.flush();
        return newEmployee.getEmployeeId();
    }
    
    @Override
    public List<Employee> retrieveListOfAllEmployees() {
        return entityManager.createQuery("SELECT e from Employee e").getResultList();
    }
    
    @Override
    public Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException {
        Query query = entityManager.createQuery("SELECT e from Employee e WHERE e.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try {
            return (Employee)query.getSingleResult();
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new EmployeeNotFoundException("Employee Username " + username + "does not exist");
        }
    }
    
    @Override
    public Employee employeeLogin(String username, String password) throws InvalidLoginException {
        try {
            Employee employee = this.retrieveEmployeeByUsername(username);
            
            if (employee.getPassword().equals(password)) {
                return employee;
            } else {
                throw new InvalidLoginException("Username does not exist or invalid password!");
            }
        } catch(EmployeeNotFoundException ex) {
            throw new InvalidLoginException("Username does not exist or invalid password!");
        }
    }
    
}