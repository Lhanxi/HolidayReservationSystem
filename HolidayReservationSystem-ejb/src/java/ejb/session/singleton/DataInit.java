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
import util.exception.EmployeeNotFoundException;

/**
 *
 * @author jeremy
 */
@Singleton
@Startup
public class DataInit implements DataInitLocal {

    @EJB
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    
    @PostConstruct
    public void defaultSystemAccount() {
        try {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("admin");
        } catch (EmployeeNotFoundException e) {
            // Create the default system administrator if not found
            Employee admin = new Employee("admin", "adminpassword", EmployeeType.SYSTEM_ADMIN);
            employeeSessionBeanLocal.createNewEmployee(admin);
        }
    }
}
