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
import entity.RoomType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.exception.InvalidLoginException;
import util.exception.InvalidCustomerCreationException;
import util.exception.ReservationNotFoundException;
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
    
    private Customer customer;
    
    public MainApp() {
    }
    
    public MainApp(RoomTypeSessionBeanRemote roomTypeSessionBeanRemote, RoomSessionBeanRemote roomSessionBeanRemote, RoomRateSessionBeanRemote roomRateSessionBeanRemote, HotelInventorySessionBeanRemote hotelInventorySessionBeanRemote, ReserveRoomSessionBeanRemote reserveRoomSessionBeanRemote, GuestSessionBeanRemote guestSessionBeanRemote) {
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
        try {
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
                    Long customerId = guestSessionBeanRemote.createNewCustomer(customer);
                    System.out.println(username + " successfully registered!\n");
                    this.showCustomerMenu();
                }
            }
        } catch (InvalidCustomerCreationException ex) {
            System.out.println(ex.getMessage());
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
            System.out.println("4: Exit\n");
            
            response = 0;
            while (response < 1 || response > 4) {
                response = getIntegerInput();
                if (response == 1) {
                    this.searchHotelRoom();
                } else if (response == 2) {
                    this.searchHotelRoom();
                } else if (response == 3) {
                    this.viewAllReservations();
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
            
            System.out.println("=== These are the available room types ===\n");
            HashMap<String, Integer> availableRoomTypes = hotelInventorySessionBeanRemote.getAvailableRoomTypes(startDate, endDate);
            int counter = 1;
            List<String> roomTypes = new ArrayList<String>();
            for (Map.Entry<String, Integer> entry : availableRoomTypes.entrySet()) {
                String roomType = entry.getKey(); 
                Integer availability = entry.getValue(); 
                System.out.println(counter + ". " + roomType + " - Available: " + availability);
                counter++;
            }
            if (customer != null) {
                this.reserveHotelRoom(roomTypes, startDate, endDate);
            } else { 
                System.out.println("You are not logged in. Please login to reserve a room!\n");
                this.runApp();
            }
        }
    }
    
    private void reserveHotelRoom(List<String> roomTypes, Date startDate, Date endDate) {
        Scanner scanner = new Scanner(System.in);
        int response = 0;
        System.out.println("Do you want to proceed with booking a room?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        while (response < 1 || response > 2) {
            System.out.print(">");
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
        for (String roomType : roomTypes) {
            System.out.println(counter + ". " + roomType);
            counter++; 
        }
        
        while (response < 1 || response > roomTypes.size()) {
            System.out.print(">");
            if (response < 1 || response > roomTypes.size()) {
                choice = roomTypes.get(response - 1);
            } else {
                System.out.println("Invalid Choice. Please try again!\n");
                continue;
            }
        }
        int numOfRooms;
        System.out.println("=== Enter the number of rooms ===\n");
        numOfRooms = this.getIntegerInput();
        Reservation reservation = new Reservation(startDate, endDate, numOfRooms);
        RoomType roomType = roomTypeSessionBeanRemote.getRoomTypeByName(choice);
        reserveRoomSessionBeanRemote.createReservation(reservation, roomType);
        System.out.println("=== Reservation succesfully created ===\n");
        this.showCustomerMenu();
    }
    
    public void viewReservationDetails() {
        try {
            Scanner scanner = new Scanner(System.in);
            int response = 0;
            String reservationId;
            while (true) {
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
                    scanner.close();
                }
                
            }
        } catch (ReservationNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void viewAllReservations() {
        List<Reservation> reservations = guestSessionBeanRemote.retrieveAllReservationByCustomerId(customer.getGuestId());
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
    }
    
    
     //utility functions
    private int getIntegerInput() {
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
}