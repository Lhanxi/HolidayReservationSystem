/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeSessionBeanLocal;
import entity.Employee;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import util.enumeration.EmployeeType;
import util.exception.DuplicateUsernameException;
import util.exception.EmployeeNotFoundException;

/**
 *
 * @author jeremy
 */
@Singleton
//@Startup
public class DataInit implements DataInitLocal {

    @EJB
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    
    //@PostConstruct
    public void defaultSystemAccount() {
        try {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("admin1");
        } catch (EmployeeNotFoundException e) {
            // Create the default system administrator if not found
            Employee admin = new Employee("admin1", "adminpassword", EmployeeType.SYSTEM_ADMIN);
            try {
                employeeSessionBeanLocal.createNewEmployee(admin);
            } catch (DuplicateUsernameException ex) {
                System.err.println("Failed to create default admin account: " + ex.getMessage());
            }
        }
        
        try {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("system");
        } catch (EmployeeNotFoundException e) {
            // Create the default system administrator if not found
            Employee system = new Employee("system", "systempassword", EmployeeType.SYSTEM);
            try {
                employeeSessionBeanLocal.createNewEmployee(system);
            } catch (DuplicateUsernameException ex) {
                System.err.println("Failed to create default admin account: " + ex.getMessage());
            }
        }
        
        
        
        
        
        try {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("hanxi1");
        } catch (EmployeeNotFoundException e) {
            // Create the default system administrator if not found
            Employee hanxi1 = new Employee("hanxi1", "enya1", EmployeeType.OPERATION_MANAGER); 
            try {
                employeeSessionBeanLocal.createNewEmployee(hanxi1);
            } catch (DuplicateUsernameException ex) {
                System.err.println("Failed to create default admin account: " + ex.getMessage());
            }
        }
        
        try {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("hanxi2");
        } catch (EmployeeNotFoundException e) {
            // Create the default system administrator if not found
            Employee hanxi2 = new Employee("hanxi2", "enya2", EmployeeType.SALES_MANAGER); 
            try {
                employeeSessionBeanLocal.createNewEmployee(hanxi2);
            } catch (DuplicateUsernameException ex) {
                System.err.println("Failed to create default admin account: " + ex.getMessage());
            }
        }
        
        try {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("hanxi2");
        } catch (EmployeeNotFoundException e) {
            // Create the default system administrator if not found
            Employee hanxi3 = new Employee("hanxi3", "enya3", EmployeeType.GUEST_RELATION_OFFICER); 
            try {
                employeeSessionBeanLocal.createNewEmployee(hanxi3);
            } catch (DuplicateUsernameException ex) {
                System.err.println("Failed to create default admin account: " + ex.getMessage());
            }
        }
    }
}
