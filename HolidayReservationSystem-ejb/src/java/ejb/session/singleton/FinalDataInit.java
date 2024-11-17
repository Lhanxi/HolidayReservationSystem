/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.RoomRateSessionBeanLocal;
import ejb.session.stateless.RoomSessionBeanLocal;
import ejb.session.stateless.RoomTypeSessionBeanLocal;
import entity.Employee;
import entity.Room;
import entity.RoomRate;
import entity.RoomType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.EmployeeType;
import util.enumeration.RateTypeEnum;
import util.exception.DuplicateUsernameException;
import util.exception.EmployeeNotFoundException;
import util.exception.RoomCreationException;
import util.exception.RoomRateCreationException;
import util.exception.RoomTypeCreationException;

/**
 *
 * @author jeremy
 */
@Singleton
@Startup
public class FinalDataInit implements FinalDataInitLocal {
    @EJB
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    @EJB
    private RoomTypeSessionBeanLocal roomTypeSessionBeanLocal;
    @EJB 
    private RoomRateSessionBeanLocal roomRateSessionBeanLocal;
    @EJB
    private RoomSessionBeanLocal roomSessionBeanLocal;
    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    
    
    @PostConstruct
    public void defaultSystemAccount() {
        // Initialize System Administrator
        try {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("sysadmin");
        } catch (EmployeeNotFoundException e) {
            Employee admin = new Employee("sysadmin", "password", EmployeeType.SYSTEM_ADMIN);
            try {
                employeeSessionBeanLocal.createNewEmployee(admin);
            } catch (DuplicateUsernameException ex) {
                System.err.println("Failed to create default admin account: " + ex.getMessage());
            }
        }

        // Initialize Operation Manager
        try {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("opmanager");
        } catch (EmployeeNotFoundException e) {
            Employee opManager = new Employee("opmanager", "password", EmployeeType.OPERATION_MANAGER);
            try {
                employeeSessionBeanLocal.createNewEmployee(opManager);
            } catch (DuplicateUsernameException ex) {
                System.err.println("Failed to create Operation Manager account: " + ex.getMessage());
            }
        }

        // Initialize Sales Manager
        try {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("salesmanager");
        } catch (EmployeeNotFoundException e) {
            Employee salesManager = new Employee("salesmanager", "password", EmployeeType.SALES_MANAGER);
            try {
                employeeSessionBeanLocal.createNewEmployee(salesManager);
            } catch (DuplicateUsernameException ex) {
                System.err.println("Failed to create Sales Manager account: " + ex.getMessage());
            }
        }

        // Initialize Guest Relation Officer
        try {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("guestrelo");
        } catch (EmployeeNotFoundException e) {
            Employee guestRelo = new Employee("guestrelo", "password", EmployeeType.GUEST_RELATION_OFFICER);
            try {
                employeeSessionBeanLocal.createNewEmployee(guestRelo);
            } catch (DuplicateUsernameException ex) {
                System.err.println("Failed to create Guest Relation Officer account: " + ex.getMessage());
            }
        }
        
        try {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("system");
        } catch (EmployeeNotFoundException e) {
            Employee system = new Employee("system", "password", EmployeeType.SYSTEM);
            try {
                employeeSessionBeanLocal.createNewEmployee(system);
            } catch (DuplicateUsernameException ex) {
                System.err.println("Failed to create Guest Relation Officer account: " + ex.getMessage());
            }
        }
        
        try {
            RoomType deluxe = new RoomType("Deluxe Room", "deluxe room", "20", "2", "toilet, shower", false);
            roomTypeSessionBeanLocal.createNewRoomType(deluxe, 0);
        } catch (RoomTypeCreationException ex) {
            System.err.println(ex.getMessage());
        }
        
        try {
            RoomType premier = new RoomType("Premier Room", "premier room", "30", "3", "toilet, shower", false);
            roomTypeSessionBeanLocal.createNewRoomType(premier, 1);
        } catch (RoomTypeCreationException ex) {
            System.err.println(ex.getMessage());
        }
        
        try {
            RoomType family = new RoomType("Family Room", "Family", "40", "4", "toilet, shower", false);
            roomTypeSessionBeanLocal.createNewRoomType(family, 2);
        } catch (RoomTypeCreationException ex) {
            System.err.println(ex.getMessage());
        }
        
        try {
            RoomType junior = new RoomType("Junior Suite", "junior suite", "50", "5", "toilet, shower", false);
            roomTypeSessionBeanLocal.createNewRoomType(junior, 3);
        } catch (RoomTypeCreationException ex) {
            System.err.println(ex.getMessage());
        }
        
        try {
            RoomType grand = new RoomType("Grand Suite", "Grand suite", "60", "6", "toilet, shower", false);
            roomTypeSessionBeanLocal.createNewRoomType(grand, 4);
        } catch (RoomTypeCreationException ex) {
            System.err.println(ex.getMessage());
        }
        
        RoomType deluxe = getRoomType("Deluxe Room");
        RoomType premier = getRoomType("Premier Room");
        RoomType family = getRoomType("Family Room");
        RoomType junior = getRoomType("Junior Suite");
        RoomType grand = getRoomType("Grand Suite");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Date startDate = java.sql.Date.valueOf(LocalDate.parse("01/01/2002", formatter));
        Date endDate = java.sql.Date.valueOf(LocalDate.parse("01/02/2002", formatter));
        
        try {
            RoomRate dP = new RoomRate("Deluxe Room Published", deluxe, RateTypeEnum.PUBLISHED, new BigDecimal(100), startDate, endDate);
            roomRateSessionBeanLocal.createNewRoomRate(dP);
        } catch (RoomRateCreationException ex) {
            System.err.println("Failed to create Deluxe Room Published rate: " + ex.getMessage());
        }

        try {
            RoomRate dN = new RoomRate("Deluxe Room Normal", deluxe, RateTypeEnum.NORMAL, new BigDecimal(50), startDate, endDate);
            roomRateSessionBeanLocal.createNewRoomRate(dN);
        } catch (RoomRateCreationException ex) {
            System.err.println("Failed to create Deluxe Room Normal rate: " + ex.getMessage());
        }

        try {
            RoomRate pP = new RoomRate("Premier Room Published", premier, RateTypeEnum.PUBLISHED, new BigDecimal(200), startDate, endDate);
            roomRateSessionBeanLocal.createNewRoomRate(pP);
        } catch (RoomRateCreationException ex) {
            System.err.println("Failed to create Premier Room Published rate: " + ex.getMessage());
        }

        try {
            RoomRate pN = new RoomRate("Premier Room Normal", premier, RateTypeEnum.NORMAL, new BigDecimal(100), startDate, endDate);
            roomRateSessionBeanLocal.createNewRoomRate(pN);
        } catch (RoomRateCreationException ex) {
            System.err.println("Failed to create Premier Room Normal rate: " + ex.getMessage());
        }

        try {
            RoomRate fP = new RoomRate("Family Room Published", family, RateTypeEnum.PUBLISHED, new BigDecimal(300), startDate, endDate);
            roomRateSessionBeanLocal.createNewRoomRate(fP);
        } catch (RoomRateCreationException ex) {
            System.err.println("Failed to create Family Room Published rate: " + ex.getMessage());
        }

        try {
            RoomRate fN = new RoomRate("Family Room Normal", family, RateTypeEnum.NORMAL, new BigDecimal(150), startDate, endDate);
            roomRateSessionBeanLocal.createNewRoomRate(fN);
        } catch (RoomRateCreationException ex) {
            System.err.println("Failed to create Family Room Normal rate: " + ex.getMessage());
        }

        try {
            RoomRate jP = new RoomRate("Junior Suite Published", junior, RateTypeEnum.PUBLISHED, new BigDecimal(400), startDate, endDate);
            roomRateSessionBeanLocal.createNewRoomRate(jP);
        } catch (RoomRateCreationException ex) {
            System.err.println("Failed to create Junior Suite Published rate: " + ex.getMessage());
        }

        try {
            RoomRate jN = new RoomRate("Junior Suite Normal", junior, RateTypeEnum.NORMAL, new BigDecimal(200), startDate, endDate);
            roomRateSessionBeanLocal.createNewRoomRate(jN);
        } catch (RoomRateCreationException ex) {
            System.err.println("Failed to create Junior Suite Normal rate: " + ex.getMessage());
        }
        
        try {
            RoomRate gP = new RoomRate("Grand Suite Published", grand, RateTypeEnum.PUBLISHED, new BigDecimal(500), startDate, endDate);
            roomRateSessionBeanLocal.createNewRoomRate(gP);
        } catch (RoomRateCreationException ex) {
            System.err.println("Failed to create Grand Suite Published rate: " + ex.getMessage());
        }

        try {
            RoomRate gN = new RoomRate("Grand Suite Normal", grand, RateTypeEnum.NORMAL, new BigDecimal(250), startDate, endDate);
            roomRateSessionBeanLocal.createNewRoomRate(gN);
        } catch (RoomRateCreationException ex) {
            System.err.println("Failed to create Grand Suite Normal rate: " + ex.getMessage());
        }
        
        try {
            Room r1 = new Room(deluxe, "0101", true, false);
            roomSessionBeanLocal.createNewRoom(r1);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Deluxe Room 0101: " + ex.getMessage());
        }

        try {
            Room r2 = new Room(deluxe, "0201", true, false);
            roomSessionBeanLocal.createNewRoom(r2);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Deluxe Room 0201: " + ex.getMessage());
        }

        try {
            Room r3 = new Room(deluxe, "0301", true, false);
            roomSessionBeanLocal.createNewRoom(r3);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Deluxe Room 0301: " + ex.getMessage());
        }

        try {
            Room r4 = new Room(deluxe, "0401", true, false);
            roomSessionBeanLocal.createNewRoom(r4);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Deluxe Room 0401: " + ex.getMessage());
        }

        try {
            Room r5 = new Room(deluxe, "0501", true, false);
            roomSessionBeanLocal.createNewRoom(r5);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Deluxe Room 0501: " + ex.getMessage());
        }

        // Premier Room Initialization
        try {
            Room r6 = new Room(premier, "0102", true, false);
            roomSessionBeanLocal.createNewRoom(r6);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Premier Room 0102: " + ex.getMessage());
        }

        try {
            Room r7 = new Room(premier, "0202", true, false);
            roomSessionBeanLocal.createNewRoom(r7);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Premier Room 0202: " + ex.getMessage());
        }

        try {
            Room r8 = new Room(premier, "0302", true, false);
            roomSessionBeanLocal.createNewRoom(r8);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Premier Room 0302: " + ex.getMessage());
        }

        try {
            Room r9 = new Room(premier, "0402", true, false);
            roomSessionBeanLocal.createNewRoom(r9);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Premier Room 0402: " + ex.getMessage());
        }

        try {
            Room r10 = new Room(premier, "0502", true, false);
            roomSessionBeanLocal.createNewRoom(r10);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Premier Room 0502: " + ex.getMessage());
        }

        // Family Room Initialization
        try {
            Room r11 = new Room(family, "0103", true, false);
            roomSessionBeanLocal.createNewRoom(r11);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Family Room 0103: " + ex.getMessage());
        }

        try {
            Room r12 = new Room(family, "0203", true, false);
            roomSessionBeanLocal.createNewRoom(r12);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Family Room 0203: " + ex.getMessage());
        }

        try {
            Room r13 = new Room(family, "0303", true, false);
            roomSessionBeanLocal.createNewRoom(r13);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Family Room 0303: " + ex.getMessage());
        }

        try {
            Room r14 = new Room(family, "0403", true, false);
            roomSessionBeanLocal.createNewRoom(r14);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Family Room 0403: " + ex.getMessage());
        }

        try {
            Room r15 = new Room(family, "0503", true, false);
            roomSessionBeanLocal.createNewRoom(r15);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Family Room 0503: " + ex.getMessage());
        }

        // Junior Suite Initialization
        try {
            Room r16 = new Room(junior, "0104", true, false);
            roomSessionBeanLocal.createNewRoom(r16);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Junior Suite 0104: " + ex.getMessage());
        }

        try {
            Room r17 = new Room(junior, "0204", true, false);
            roomSessionBeanLocal.createNewRoom(r17);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Junior Suite 0204: " + ex.getMessage());
        }

        try {
            Room r18 = new Room(junior, "0304", true, false);
            roomSessionBeanLocal.createNewRoom(r18);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Junior Suite 0304: " + ex.getMessage());
        }

        try {
            Room r19 = new Room(junior, "0404", true, false);
            roomSessionBeanLocal.createNewRoom(r19);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Junior Suite 0404: " + ex.getMessage());
        }

        try {
            Room r20 = new Room(junior, "0504", true, false);
            roomSessionBeanLocal.createNewRoom(r20);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Junior Suite 0504: " + ex.getMessage());
        }

        // Grand Suite Initialization
        try {
            Room r21 = new Room(grand, "0105", true, false);
            roomSessionBeanLocal.createNewRoom(r21);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Grand Suite 0105: " + ex.getMessage());
        }

        try {
            Room r22 = new Room(grand, "0205", true, false);
            roomSessionBeanLocal.createNewRoom(r22);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Grand Suite 0205: " + ex.getMessage());
        }

        try {
            Room r23 = new Room(grand, "0305", true, false);
            roomSessionBeanLocal.createNewRoom(r23);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Grand Suite 0305: " + ex.getMessage());
        }

        try {
            Room r24 = new Room(grand, "0405", true, false);
            roomSessionBeanLocal.createNewRoom(r24);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Grand Suite 0405: " + ex.getMessage());
        }

        try {
            Room r25 = new Room(grand, "0505", true, false);
            roomSessionBeanLocal.createNewRoom(r25);
        } catch (RoomCreationException ex) {
            System.err.println("Failed to create Grand Suite 0505: " + ex.getMessage());
        }
 
    }
    
    private RoomType getRoomType(String name) {
        Query query = em.createQuery("SELECT r FROM RoomType r WHERE r.name=:name");
        query.setParameter("name", name);
        return (RoomType) query.getSingleResult();
    }

}




