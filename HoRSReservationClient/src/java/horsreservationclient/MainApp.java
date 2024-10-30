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
//import ejb.session.stateless.GuestSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import util.exception.InvalidLoginException;
/**
 *
 * @author leunghanxi
 */
public class MainApp {
    //private GuestSessionBeanRemote guestSessionBeanRemote;
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    private RoomSessionBeanRemote roomSessionBeanRemote;
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    
    private Customer customer;
    
    public MainApp() {
    }
    
    public MainApp(RoomRateSessionBeanRemote roomRateSessionBeanRemote, RoomSessionBeanRemote roomSessionBeanRemote, RoomTypeSessionBeanRemote roomTypeSessionBeanRemote) {
        //this.guestSessionBeanRemote = guestSessionBeanRemote;
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
                System.out.print("> ");
                response = scanner.nextInt();
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
        while (true) {
            String username = "";
            String password = "";
            System.out.println("=== Customer Login Page ===\n");
            System.out.print("Enter username> ");
            username = scanner.nextLine().trim();
            System.out.print("Enter password> ");
            password = scanner.nextLine().trim();
            if (username.length() > 0 && password.length() > 0) {
                //customer = guestSessionBeanRemote.guestLogin(username, password);
                System.out.println(username + " successfully logged in!\n");
                this.showCustomerMenu();
            }
        }
    }
    
    public void registerCustomer() {
        Scanner scanner = new Scanner(System.in);
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
                //Long customerId = guestSessionBeanRemote.createNewGuest(customer);
                System.out.println(username + " successfully registered!\n");
                this.showCustomerMenu();
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
            System.out.println("4: Exit\n");
            
            response = 0;
            while (response < 1 || response > 4) {
                System.out.print("> ");
                response = scanner.nextInt();
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
    }
    
    public void reserveHotelRoom() {
    }
    
    public void viewReservationDetails() {
    }
    
    public void viewAllReservations() {
    }
}