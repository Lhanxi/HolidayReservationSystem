/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author leunghanxi
 */
@Singleton
@LocalBean
public class InitialisationSessionBean {
    
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager entityManager;
    
    @EJB(name = "EmployeeSessionBeanLocal")
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    
    public InitialisationSessionBean() {
    }
}
