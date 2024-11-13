/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package horsreservationclient;


import java.util.Scanner;
import entity.Customer;
import ejb.session.stateless.HotelInventorySessionBeanRemote;
import ejb.session.stateless.ReserveRoomSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import ejb.session.stateless.GuestSessionBeanRemote;
import entity.Reservation;
import entity.RoomRate;
import entity.RoomType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import util.exception.InvalidLoginException;
import util.exception.InvalidCustomerCreationException;
import util.exception.ReservationNotFoundException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
/**
 *
 * @author leunghanxi
 */
public class MainApp {
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    private RoomSessionBeanRemote roomSessionBeanRemote;
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    private HotelInventorySessionBeanRemote hotelInventorySessionBeanRemote;
    private ReserveRoomSessionBeanRemote reserveRoomSessionBeanRemote;
    private GuestSessionBeanRemote guestSessionBeanRemote;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    private Customer customer;
    private Long customerId;
    
    public MainApp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    public MainApp(RoomTypeSessionBeanRemote roomTypeSessionBeanRemote, RoomSessionBeanRemote roomSessionBeanRemote, RoomRateSessionBeanRemote roomRateSessionBeanRemote, HotelInventorySessionBeanRemote hotelInventorySessionBeanRemote, ReserveRoomSessionBeanRemote reserveRoomSessionBeanRemote, GuestSessionBeanRemote guestSessionBeanRemote) {
        this();
        this.roomTypeSessionBeanRemote = roomTypeSessionBeanRemote;
        this.roomSessionBeanRemote = roomSessionBeanRemote;
        this.roomRateSessionBeanRemote = roomRateSessionBeanRemote;
        this.hotelInventorySessionBeanRemote = hotelInventorySessionBeanRemote;
        this.reserveRoomSessionBeanRemote = reserveRoomSessionBeanRemote;
        this.guestSessionBeanRemote = guestSessionBeanRemote;
    }
    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;
        while (true) {
            System.out.println("=== Welcome to the Hotel Reservation System - Reservation Client ===\n");
            System.out.println("1: Guest Login");
            System.out.println("2: Register as Guest");
            System.out.println("3: Search Hotel Room");
            System.out.println("4: Exit\n");
            
            response = 0;
            while (response < 1 || response > 4) {
                System.out.print(">");
                response = getIntegerInput();
                if (response == 1) {
                    this.loginCustomer();
                } else if (response == 2) {
                    this.registerCustomer();
                } else if (response == 3) {
                    this.searchHotelRoom();
                } else {
                    break;
                }
            }
            if (response == 4) {
                break;
            }
        }
        scanner.close();
    }
    
    public void loginCustomer() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String username = "";
                String password = "";
                System.out.println("=== Customer Login Page ===\n");
                System.out.print("Enter username> ");
                username = scanner.nextLine().trim();
                System.out.print("Enter password> ");
                password = scanner.nextLine().trim();
                if (username.length() > 0 && password.length() > 0) {
                    customer = guestSessionBeanRemote.customerLogin(username, password);
                    customerId = customer.getGuestId();
                    System.out.println(username + " successfully logged in!\n");
                    this.showCustomerMenu();
                }
            }
        } catch (InvalidLoginException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public void registerCustomer() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
                String username = "";
                String password = "";
                String passportNumber = "";
                String name = "";
                System.out.println("=== Customer Registration Page ===\n");
                System.out.print("Enter username> ");
                username = scanner.nextLine().trim();
                System.out.print("Enter password> ");
                password = scanner.nextLine().trim();
                System.out.print("Enter name> ");
                name = scanner.nextLine().trim();
                System.out.print("Enter passport number> ");
                passportNumber = scanner.nextLine().trim();
                if (username.length() > 0 && password.length() > 0 && passportNumber.length() > 0) {
                    customer = new Customer(username, password, name, passportNumber);
                    Set<ConstraintViolation<Customer>>constraintViolations = validator.validate(customer);
                    if(constraintViolations.isEmpty()) {
                        try {
                            customerId = guestSessionBeanRemote.createNewCustomer(customer);
                            System.out.println(username + " successfully registered!\n");
                            this.showCustomerMenu();
                        } catch (InvalidCustomerCreationException ex) {
                            System.out.println(ex.getMessage());
                        }
                        
                    } else {
                        showInputDataValidationErrorsForCustomer(constraintViolations);
                    }
                }
            
            }
        }
    
    public void showCustomerMenu() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;
        while (true) {
            System.out.println("=== Customer Menu Page ===\n");
            System.out.println("1: Search Hotel Room");
            System.out.println("2: Reserve Hotel Room");
            System.out.println("3: View All Reservations");
            System.out.println("4: View Reservation Details");
            System.out.println("5: Logout\n");
            
            response = 0;
            while (response < 1 || response > 5) {
                response = getIntegerInput();
                if (response == 1) {
                    this.searchHotelRoom();
                } else if (response == 2) {
                    this.searchHotelRoom();
                } else if (response == 3) {
                    this.viewAllReservations();
                } else if (response == 4) {
                    this.viewReservationDetails();
                } else {
                    break;
                }
            }
            if (response == 5) {
                customer = null;
                customerId = null;
                this.runApp();
            }
        }
    }
    
    public void searchHotelRoom() {
        Scanner scanner = new Scanner(System.in);
        Date startDate;
        Date endDate;
        while (true) {
            System.out.println("Please enter the start date in the format DD/MM/YYYY");
            System.out.print(">");
            startDate = this.getDateInput();
            System.out.println("Please enter the end date in the format DD/MM/YYYY");
            System.out.print(">");
            endDate = this.getDateInput();
            
            if (endDate.before(startDate)) {
                System.out.println("End Date cannot be before start date. Please try again.\n");
            } else {
                break;
            }
        }
            
        System.out.println("=== These are the available room types ===\n");
        HashMap<String, Integer> availableRoomTypes = hotelInventorySessionBeanRemote.getAvailableRoomTypes(startDate, endDate);
        int counter = 1;
        for (Map.Entry<String, Integer> entry : availableRoomTypes.entrySet()) {
            String roomTypeName = entry.getKey(); 
            Integer availability = entry.getValue(); 
            RoomType roomType = roomTypeSessionBeanRemote.getRoomTypeByName(roomTypeName);
            BigDecimal totalAmount = roomRateSessionBeanRemote.calculateRoomRateAmount(roomType, startDate, endDate, 1);
            System.out.println(counter + ". " + roomTypeName + " - Available: " + availability);
            System.out.println("Cost for 1 room: $" + totalAmount + " \n");
            counter++;
        }
        
        
        if (customer != null) {
            this.reserveHotelRoom(availableRoomTypes, startDate, endDate);
        } else { 
            System.out.println("You are not logged in. Please login to reserve a room!\n");
            this.runApp();
        }
    }
    
    private void reserveHotelRoom(HashMap<String, Integer> availableRoomTypes, Date startDate, Date endDate) {
        Scanner scanner = new Scanner(System.in);
        int response = 0;
        System.out.println("Do you want to proceed with booking a room?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        
        while (response < 1 || response > 2) {
            response = getIntegerInput();
            if (response == 1) {
                break;
            } else if (response == 2) {
                this.showCustomerMenu();
            } else {
                System.out.println("Invalid Choice. Please try again!\n");
                continue;
            }
        }
        response = 0;
        String choice = "";
        System.out.println("=== Select the room type ===\n");

        int counter = 1; 
        List<String> roomTypes = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : availableRoomTypes.entrySet()) {
            String roomType = entry.getKey(); 
            Integer availability = entry.getValue();
            roomTypes.add(roomType);
            System.out.println(counter + ". " + roomType + " - Available: " + availability);
            counter++;
        }
        
        while (response < 1 || response > counter) {
            response = getIntegerInput();
            if (response >= 1 || response <= counter) {
                choice = roomTypes.get(response - 1);
                break;
            } else {
                System.out.println("Invalid Choice. Please try again!\n");
                continue;
            }
        }
        
        int numOfRooms;
        int availableNumOfRooms = availableRoomTypes.get(choice);
        Reservation reservation;
        System.out.println("=== Enter the number of rooms ===\n");
        
        while (true) {
            numOfRooms = this.getIntegerInput();
            
            if (numOfRooms < 1 || numOfRooms > availableNumOfRooms) {
                System.out.println("Invalid number of rooms. There are " + availableNumOfRooms + " available rooms!\n");
                RoomType roomType = roomTypeSessionBeanRemote.getRoomTypeByName(choice);
                BigDecimal totalAmount = roomRateSessionBeanRemote.calculateRoomRateAmount(roomType, startDate, endDate, numOfRooms);
                List<RoomRate> roomRates = roomRateSessionBeanRemote.retrieveRoomRateByDate(startDate, endDate, roomType);

                reservation = new Reservation(startDate, endDate, numOfRooms, roomRates);
                Set<ConstraintViolation<Reservation>>constraintViolations = validator.validate(reservation);
                if(constraintViolations.isEmpty()) {
                    Long reservationId = reserveRoomSessionBeanRemote.createReservationForCustomer(customerId, reservation, roomType);
                    System.out.println("Reservation " + reservationId + " succesfully created.");
                    System.out.println("Total Amount to be paid: $" + totalAmount + " \n");
                } else {
                    showInputDataValidationErrorsForReservation(constraintViolations);
                }
                this.showCustomerMenu();
            } else {
                break;
            }
        }
    }
    
    public void viewReservationDetails() {
        try {
            Scanner scanner = new Scanner(System.in);
            int response = 0;
            String reservationId;
            while (true) {
                response = 0;
                System.out.println("=== Reservation Details ===\n");
                System.out.println("Please Enter Reservation Id");
                System.out.print(">");
                reservationId = scanner.next().trim();
                Reservation reservation = guestSessionBeanRemote.retrieveReservationById(Long.parseLong(reservationId));
                System.out.println("Reservation Id: " + reservation.getReservationId() + ", Start Date: " + reservation.getStartDate() + ", End Date: " + reservation.getEndDate() + ", Number of Rooms: " + reservation.getNumRooms() + ", Room Type: " + reservation.getRoomType() + "\n");
                
                System.out.println("Would you like to view more reservations?\n");
                System.out.println("1. Yes");
                System.out.println("2. No");
                while (response < 1 || response > 2) {
                    response = getIntegerInput();
                    if (response == 1) {
                        this.viewReservationDetails();
                    } else if (response == 2) {
                        this.showCustomerMenu();
                    }
                }
                
            }
        } catch (ReservationNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void viewAllReservations() {
        try {
            List<Reservation> reservations = guestSessionBeanRemote.retrieveAllReservationByCustomerId(customerId);
            int counter = 1;
            System.out.println("=== All Reservations ===\n");
            for (Reservation reservation : reservations) {
                System.out.println(counter + ". Reservation Id: " + reservation.getReservationId());
                counter += 1;
            }
            Scanner scanner = new Scanner(System.in);
            int response = 0;
            while (true) {
                System.out.println("=== Do you want to view a specific reservation? ===\n");
                System.out.println("1. Yes");
                System.out.println("2: No");

                response = 0;
                while (response < 1 || response > 2) {
                    response = getIntegerInput();
                    if (response == 1) {
                        this.viewReservationDetails();
                    } else if (response == 2) {
                        this.showCustomerMenu();
                    }
                }
            }
        } catch (ReservationNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
     //utility functions
    public int getIntegerInput() {
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        boolean isValid = false;
        
        while (!isValid) {
            System.out.print(">"); 
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                isValid = true;
            } else {
                System.out.println("Invalid input. Please try again.");
                scanner.next();
            }
                    
        }
        return input;
    }
    
    public static Date getDateInput() {
        Scanner scanner = new Scanner(System.in);
        String dateInput;
        while (true) {
            System.out.print(">");
            dateInput = scanner.next(); 
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            if (isValidDate(dateInput)) {
                System.out.println("The date format is valid: " + dateInput);
                LocalDate localDate = LocalDate.parse(dateInput, formatter);
                return java.sql.Date.valueOf(localDate); 
            } else {
                System.out.println("The date format is invalid. Please try again.");
            }
        }
    }
        
    public static boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        try {
            LocalDate.parse(date, formatter);
            return true; 
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    private void showInputDataValidationErrorsForCustomer(Set<ConstraintViolation<Customer>>constraintViolations)
    {
        System.out.println("\nInput data validation error!:");
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            System.out.println("\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage());
        }

        System.out.println("\nPlease try again......\n");
    }
    
    private void showInputDataValidationErrorsForReservation(Set<ConstraintViolation<Reservation>>constraintViolations)
    {
        System.out.println("\nInput data validation error!:");
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            System.out.println("\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage());
        }

        System.out.println("\nPlease try again......\n");
    }
}