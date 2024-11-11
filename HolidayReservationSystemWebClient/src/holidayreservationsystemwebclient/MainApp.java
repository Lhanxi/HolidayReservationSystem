/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package holidayreservationsystemwebclient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.client.HolidayReservationSystemWebService_Service;
import ws.client.InvalidLoginException_Exception;
import ws.client.Partner;
import ws.client.PartnerType;
import ws.client.Reservation;
import ws.client.ReservationNotFoundException_Exception;
import ws.client.RoomType;

/**
 *
 * @author leunghanxi
 */
public class MainApp {
    private Partner partner;
    
    private HolidayReservationSystemWebService_Service service;

    public MainApp() {
    }

    public MainApp(HolidayReservationSystemWebService_Service service) {
        this.service = service;
    }

    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;
        while (true) {
            System.out.println("=== Welcome to the Holiday Reservation System - Partner Web Client ===\n");
            System.out.println("1: Partner Login");
            System.out.println("2: Exit\n");
            
            response = 0;
            while (response < 1 || response > 2) {
                System.out.print(">");
                response = getIntegerInput();
                if (response == 1) {
                    this.loginPartner();
                } else {
                    break;
                }
            }
            if (response == 2) {
                break;
            }
        }
        scanner.close();
    }
    
    public void loginPartner() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String username = "";
                String password = "";
                System.out.println("=== Partner Login Page ===\n");
                System.out.print("Enter username> ");
                username = scanner.nextLine().trim();
                System.out.print("Enter password> ");
                password = scanner.nextLine().trim();
                if (username.length() > 0 && password.length() > 0) {
                    partner = service.getHolidayReservationSystemWebServicePort().doPartnerLogin(username, password);
                    
                    System.out.println(username + " successfully logged in!\n");
                    break;
                }
                
            }
            if (partner.getPartnerType().equals(PartnerType.EMPLOYEE)) {
                this.showPartnerEmployeeMenu();
            } else {
                this.showManagerMenu();
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public void showPartnerEmployeeMenu() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;
        while (true) {
            System.out.println("=== Welcome to the Partner Employee Menu ===\n");
            System.out.println("1: Search Hotel Room");
            System.out.println("2: Exit\n");
            
            response = 0;
            while (response < 1 || response > 2) {
                System.out.print(">");
                response = getIntegerInput();
                if (response == 1) {
                    this.searchHotelRoom();
                } else {
                    break;
                }
            }
            if (response == 2) {
                break;
            }
        }
        scanner.close();
    }
    
    public void searchHotelRoom() {
        Scanner scanner = new Scanner(System.in);
        Date startDate;
        Date endDate;
        try {
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
            List<String> availableRoomTypes = service.getHolidayReservationSystemWebServicePort().searchRoom(startDate, endDate);
            for (int i = 0; i < availableRoomTypes.size(); i++) {
                String roomData = availableRoomTypes.get(i);

                String[] parts = roomData.split(" ");

                if (parts.length == 2) {
                    String roomType = parts[0]; 
                    String availableRooms = parts[1];  

                    System.out.println((i + 1) + ". " + roomType + " - Available: " + availableRooms);
                }
            }

            if (partner.getPartnerType().equals(PartnerType.EMPLOYEE)) {
                this.showPartnerEmployeeMenu();
            } else {
                this.reserveHotelRoom(availableRoomTypes, startDate, endDate);
            }
        } catch (InvalidLoginException_Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    public void showManagerMenu() {
        Scanner scanner = new Scanner(System.in);
        int response = 0;
        while (true) {
            System.out.println("=== Partner Manager Menu Page ===\n");
            System.out.println("1: Search Hotel Room");
            System.out.println("2: Reserve Hotel Room");
            System.out.println("3: View All Reservations");
            System.out.println("4: View Reservation Details");
            System.out.println("5: Exit\n");
            
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
            if (response == 4) {
                break;
            }
        }
        scanner.close();
    }
    
    public void reserveHotelRoom(List<String> availableRoomTypes, Date startDate, Date endDate) {
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
                this.showManagerMenu();
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
        List<String> availablity = new ArrayList<String>();
        for (int i = 0; i < availableRoomTypes.size(); i++) {
            String roomData = availableRoomTypes.get(i);

            String[] parts = roomData.split(" ");

            if (parts.length == 2) {
                String roomType = parts[0]; 
                String availableRooms = parts[1]; 
                roomTypes.add(parts[0]);
                availablity.add(parts[1]);
                System.out.println((i + 1) + ". " + roomType + " - Available: " + availableRooms);
                counter++;
            }
        }
        
        int availableNumOfRooms = 0;
        
        while (response < 1 || response > counter) {
            response = getIntegerInput();
            if (response >= 1 || response <= counter) {
                choice = roomTypes.get(response - 1);
                availableNumOfRooms = Integer.parseInt(availablity.get(response - 1));
                break;
            } else {
                System.out.println("Invalid Choice. Please try again!\n");
                continue;
            }
        }
        
        int numOfRooms;
        Reservation reservation;
        RoomType roomType = service.getHolidayReservationSystemWebServicePort().retrieveRoomType(choice);
        
        System.out.println("=== Enter the number of rooms ===\n");

        while (true) {
            numOfRooms = this.getIntegerInput();
            
            if (numOfRooms < 1 || numOfRooms > availableNumOfRooms) {
                System.out.println("Invalid number of rooms. There are " + availableNumOfRooms + " available rooms!\n");
            } else {
                Long reservationId = service.getHolidayReservationSystemWebServicePort().reserveRoom(partner.getPartnerId(), startDate, endDate, numOfRooms, roomType);
                System.out.println("Reservation " + reservationId + " successfully reserved!\n");
                break;
            }
        }
        this.showManagerMenu();
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
                
                Reservation reservation = service.getHolidayReservationSystemWebServicePort().retrieveReservation(Long.parseLong(reservationId));
                System.out.println("Reservation Id: " + reservation.getReservationId() + ", Start Date: " + reservation.getStartDate() + ", End Date: " + reservation.getEndDate() + ", Number of Rooms: " + reservation.getNumRooms() + ", Room Type: " + reservation.getRoomType() + "\n");
                
                System.out.println("Would you like to view more reservations?\n");
                System.out.println("1. Yes");
                System.out.println("2. No");
                while (response < 1 || response > 2) {
                    response = getIntegerInput();
                    if (response == 1) {
                        this.viewReservationDetails();
                    } else if (response == 2) {
                        this.showManagerMenu();
                    }
                }
                
            }
        } catch (ReservationNotFoundException_Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void viewAllReservations() {
        try {
            List<Reservation> reservations = service.getHolidayReservationSystemWebServicePort().retrieveAllPartnerReservations(partner.getPartnerId());
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
                        this.showManagerMenu();
                    }
                }
            }
        } catch (ReservationNotFoundException_Exception ex) { 
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
    
    public XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
        try {
            
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(date);

            
            java.util.GregorianCalendar gregorianCalendar = new java.util.GregorianCalendar();
            gregorianCalendar.setTime(date);

            
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
            return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
