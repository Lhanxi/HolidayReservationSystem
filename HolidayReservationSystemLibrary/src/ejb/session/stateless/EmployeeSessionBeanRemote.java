/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Employee;
import java.util.List;
import javax.ejb.Remote;
import util.exception.EmployeeNotFoundException;
import util.exception.InvalidLoginException;

/**
 *
 * @author jeremy
 */
@Remote
public interface EmployeeSessionBeanRemote {
    public Long createNewEmployee(Employee newEmployee);
    public List<Employee> retrieveListOfAllEmployees();
    public Employee employeeLogin(String username, String password) throws InvalidLoginException; 
    public Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException;
    
}
