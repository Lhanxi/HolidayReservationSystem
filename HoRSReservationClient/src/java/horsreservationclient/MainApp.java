/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package horsreservationclient;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import entity.Customer;
import entity.Visitor;
import entity.Reservation;
import entity.RoomReservation;
import entity.Room;
import entity.RoomRate;
import entity.RoomType;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import util.exception.InvalidLoginException;
import util.exception.InvalidCustomerCreationException;
/**
 *
 * @author leunghanxi
 */
public class MainApp {
    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    private RoomSessionBeanRemote roomSessionBeanRemote;
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    
    private Customer customer;
    
    public MainApp() {
    }
    
    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, RoomRateSessionBeanRemote roomRateSessionBeanRemote, RoomSessionBeanRemote roomSessionBeanRemote, RoomTypeSessionBeanRemote roomTypeSessionBeanRemote) {
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.roomRateSessionBeanRemote = roomRateSessionBeanRemote;
        this.roomSessionBeanRemote = roomSessionBeanRemote;
        this.roomTypeSessionBeanRemote = roomTypeSessionBeanRemote;
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
                    customer = customerSessionBeanRemote.customerLogin(username, password);
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
                System.out.println("=== Customer Registration Page ===\n");
                System.out.print("Enter username> ");
                username = scanner.nextLine().trim();
                System.out.print("Enter password> ");
                password = scanner.nextLine().trim();
                System.out.print("Enter passport number> ");
                passportNumber = scanner.nextLine().trim();
                if (username.length() > 0 && password.length() > 0 && passportNumber.length() > 0) {
                    customer = new Customer(username, password, passportNumber);
                    Long customerId = customerSessionBeanRemote.createNewCustomer(customer);
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
                    this.reserveHotelRoom();
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
        int response = 0;
        while (true) {
            System.out.println("=== Search Hotel Room ===\n");
            System.out.println("Please Select a Room Type\n");
            System.out.println("1: Reserve Hotel Room");
            System.out.println("3: View All Reservations");
            System.out.println("4: Exit\n");
            
            response = 0;
            while (response < 1 || response > 4) {
                response = getIntegerInput();
                if (response == 1) {
                    this.searchHotelRoom();
                } else if (response == 2) {
                    this.reserveHotelRoom();
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
    
    public void reserveHotelRoom() {
    }
    
    public void viewReservationDetails() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;
        while (true) {
            System.out.println("=== Search Hotel Room ===\n");
            System.out.println("Please Select a Room Type\n");
            System.out.println("1: Reserve Hotel Room");
            System.out.println("3: View All Reservations");
            System.out.println("4: Exit\n");
            
            response = 0;
            while (response < 1 || response > 4) {
                response = getIntegerInput();
                if (response == 1) {
                    this.searchHotelRoom();
                } else if (response == 2) {
                    this.reserveHotelRoom();
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
    
    public void viewAllReservations() {
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
    
    public String getValidDate() {
        Scanner scanner = new Scanner(System.in);
        String dateInput;
        
        while (true) {
            System.out.print(">");
            dateInput = scanner.next(); 
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            if (isValidDate(dateInput)) {
                System.out.println("The date format is valid: " + dateInput);
                break; // Exit the loop if the date is valid
            } else {
                System.out.println("The date format is invalid. Please try again.");
            }
        }
        return dateInput;
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