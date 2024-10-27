/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package horsmanagementclient;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.HotelInventorySessionBeanRemote;
import ejb.session.stateless.ReserveRoomSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import entity.Employee;
import entity.Reservation;
import entity.Room;
import entity.RoomRate;
import entity.RoomType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.enumeration.EmployeeType;
import util.enumeration.RateTypeEnum;
import util.exception.InvalidLoginException;
/**
 *
 * @author jeremy
 */
public class MainApp {
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    private RoomSessionBeanRemote roomSessionBeanRemote; 
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;
    private HotelInventorySessionBeanRemote hotelInventorySessionBeanRemote;
    private ReserveRoomSessionBeanRemote reserveRoomSessionBeanRemote;
    
    public MainApp() {
    }

    public MainApp(RoomTypeSessionBeanRemote roomTypeSessionBeanRemote, RoomSessionBeanRemote roomSessionBeanRemote, RoomRateSessionBeanRemote roomRateSessionBeanRemote, EmployeeSessionBeanRemote employeeSessionBeanRemote, HotelInventorySessionBeanRemote hotelInventorySessionBeanRemote, ReserveRoomSessionBeanRemote reserveRoomSessionBeanRemote) {
        this.roomTypeSessionBeanRemote = roomTypeSessionBeanRemote;
        this.roomSessionBeanRemote = roomSessionBeanRemote;
        this.roomRateSessionBeanRemote = roomRateSessionBeanRemote;
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.hotelInventorySessionBeanRemote = hotelInventorySessionBeanRemote;
        this.reserveRoomSessionBeanRemote = reserveRoomSessionBeanRemote;
    }
    
    public void run() throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** Welcome to Holiday Reservation System Management Portal ***");
        Employee employee;
        
        while (true) {
            System.out.println("Please login to begin."); 
            System.out.print("Username: "); 
            String username = scanner.next(); 
            System.out.print("Password: "); 
            String password = scanner.next(); 
            
            try {
                employee = employeeSessionBeanRemote.employeeLogin(username, password);  
                break;
            } catch (InvalidLoginException e) {
               System.out.println("Invalid username or password. Please try again.");
            }
        }
        
        if (employee.getEmployeeType() == EmployeeType.SYSTEM_ADMIN) {
            
        } else if (employee.getEmployeeType() == EmployeeType.OPERATION_MANAGER) {
            System.out.println("Please select what you would like to do.");
            System.out.println("1: RoomType functions");
            System.out.println("2: Room functions");
            Integer r = getIntegerInput();
            if (r == 1) {
                while (true) {
                    //For RoomType
                    System.out.println("Select what to do:"); 
                    System.out.println("1: Create new room type");
                    System.out.println("2: View room type details");
                    System.out.println("3: Update Room Type Details");
                    System.out.println("4: Delete Room Type");
                    System.out.println("5: View all Room Type");
                    System.out.println("5: Exit");
                    Integer response = getIntegerInput();

                    if (response == 1) {
                        createNewRoomType(); 
                    } else if (response == 2) {
                        viewRoomTypeDetails(); 
                    } else if (response == 3) {
                        updateRoomType(); 
                    } else if (response == 4) {
                        deleteRoomType();
                    } else if (response == 5) {
                        viewAllRoomTypes();
                    } else if (response == 6) {
                        break;
                    }
                }
        
            } else if (r ==2) {
            //for Room
            System.out.println("Select what to do:"); 
            System.out.println("1: Create New Room"); 
            System.out.println("2: Update Room");
            System.out.println("3: Delete Room");
            System.out.println("4: View All Rooms");
            System.out.print(">"); 
            Integer newResponse = getIntegerInput();
        
            if (newResponse == 1) {
                createNewRoom(); 
            } else if (newResponse == 2) {
                updateRoom(); 
            } else if (newResponse == 3) {
                deleteRoom();
            } else if (newResponse == 4 ){
                viewAllRooms();
            }
            }
        } else if (employee.getEmployeeType() == EmployeeType.SALES_MANAGER) {
            //for RoomRate
            System.out.println("Select what to do");
            System.out.println("1: Create New Room Rate"); 
            System.out.println("2: Update RoomRate");
            System.out.println("3: Delete Room Rate");
            System.out.println("4: View All Room Rates");
            Integer re = getIntegerInput();

            if (re == 1) {
                createNewRoomRate();
            } else if (re == 2) {
                updateRoomRate();
            } else if (re == 3) {
                System.out.println("not available yet LOL"); 
            } else if (re == 4 ) {
                viewAllRoomRates();
            }
        } else if (employee.getEmployeeType() == EmployeeType.GUEST_RELATION_OFFICER) {
            while (true) {
                System.out.println("Select what to do");
                System.out.println("1: Walk-in Search Room"); 
                System.out.println("2: Check-in Guest");
                System.out.println("3: Check-out Guest");
                System.out.println("4: Done");
                Integer re = getIntegerInput();

                if (re == 1) {
                    walkInSearchRoom();
                } else if (re == 2) {
                    
                } else if (re == 3) {
                    
                } else if (re == 4) {
                    break;
                }
            }
        
        }
        return;
    }
    
    private void createNewRoomType() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please enter the room type name.");
        System.out.print(">"); 
        String roomNameType = scanner.next();
        
        System.out.println("Please include the description for the room type"); 
        System.out.print(">"); 
        String description = scanner.next();
        
        System.out.println("Please indicate the size of the room"); 
        System.out.print(">"); 
        String size = scanner.next(); 
        
        System.out.println("Please indicate the bed capacity of the room"); 
        System.out.print(">"); 
        String bedCapacity = scanner.next(); 
        
        System.out.println("Please indicate the room amentities, separating them with a comma"); 
        System.out.print(">"); 
        String amenities = scanner.next();
        
        System.out.println("Please indicate the room ranking"); 
        Integer ranking = getIntegerInput();
        
        RoomType newRoomType = new RoomType(roomNameType, description, size, bedCapacity, amenities, ranking);
        
        Long roomTypeId = roomTypeSessionBeanRemote.createNewRoomType(newRoomType); 
        
        System.out.println("Room Type Successfully created, Room Type Id: " + roomTypeId);
    }
    
    private void viewRoomTypeDetails() {
        Scanner scanner = new Scanner(System.in);
        
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getRoomTypeList(); 
        System.out.println("Select the room type that you would like to view");
                
        for (int i = 0; i < roomTypesList.size(); i++) {
            System.out.println(i + ": " + roomTypesList.get(i).getRoomTypeId() + " , " + roomTypesList.get(i).getRoomType());
        }
        System.out.print(">"); 
        Integer response = scanner.nextInt(); 

        System.out.println("RoomId: " + roomTypesList.get(response).getRoomTypeId());
        System.out.println("Room Name: " + roomTypesList.get(response).getRoomType());
        System.out.println("Description: " + roomTypesList.get(response).getDescription());
        System.out.println("Bed Capacity: " + roomTypesList.get(response).getBedCapacity());
        System.out.println("Amenities: " + roomTypesList.get(response).getAmenities());
    }
    
    private void updateRoomType() {
        Scanner scanner = new Scanner(System.in);
        
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getRoomTypeList(); 
        System.out.println("Select the room that you would like to update");
                
        for (int i = 0; i < roomTypesList.size(); i++) {
            System.out.println(i + ": " + roomTypesList.get(i).getRoomTypeId() + " , " + roomTypesList.get(i).getRoomType());
        }
        
        Integer response = scanner.nextInt(); 
        RoomType roomType = roomTypesList.get(response);
        
        String output = String.format("roomId=%s; RoomName=%s; Description=%s; Size =%s; BedCapacity=%s; Amenities=%s", 
                    roomType.getRoomTypeId(),
                    roomType.getRoomType(),
                    roomType.getDescription(),
                    roomType.getSize(),
                    roomType.getBedCapacity(), 
                    roomType.getAmenities());
        System.out.println("Selected room" + output);
        
        Long roomTypeId = roomType.getRoomTypeId();
        String newRoomTypeName = roomType.getRoomType();
        String newDescription = roomType.getDescription();
        String newSize = roomType.getSize();
        String newBedCapacity = roomType.getBedCapacity();
        String newAmenities = roomType.getAmenities();
        
        while (true) {
            System.out.println("Select the detail you would like to change: "); 
            System.out.println("1: Room Name"); 
            System.out.println("2: Description"); 
            System.out.println("3: Size");
            System.out.println("4: Bed Capacity"); 
            System.out.println("5: Amenities"); 
            System.out.println("6: Done"); 
            response = getIntegerInput();

            if (response == 1) {
                System.out.println("Please enter the new room type.");
                System.out.print(">");
                newRoomTypeName = scanner.next();

            } else if (response == 2) {
                System.out.println("Please enter new description"); 
                System.out.print(">");
                newDescription = scanner.nextLine();
            } else if (response == 3) {
                System.out.println("Please enter the new size"); 
                System.out.print(">"); 
                newSize = scanner.next(); 
            } else if (response == 4) {
                System.out.println("Please enter the new bed capacity"); 
                System.out.print(">"); 
                newBedCapacity = scanner.next(); 
            } else if (response == 5) {
                System.out.println("Please enter the new amenities"); 
                System.out.print(">"); 
                newAmenities = scanner.next(); 
            } else if (response == 6) {
                break;
            }
        }
        roomTypeSessionBeanRemote.updateRoomTypeDetails(roomTypeId, newRoomTypeName, newDescription, newSize, newBedCapacity, newAmenities); 
        
    }
    
    private void deleteRoomType() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please select the room type that you want to delete.");
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getRoomTypeList(); 
        for (int i = 0; i < roomTypesList.size(); i++) {
            System.out.println(i + ": " + roomTypesList.get(i).getRoomType()); 
        }
        Integer response = getIntegerInput();
        RoomType roomType = roomTypesList.get(response); 
        
        String output = roomTypeSessionBeanRemote.deleteRoomType(roomType);
        System.out.println(output);
    }
    
    private void viewAllRoomTypes() {
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getRoomTypeList(); 
        
        for (RoomType roomType: roomTypesList) {
            String output = String.format("roomId=%s; RoomName=%s; Description=%s; Size=%s; BedCapacity=%s; Amenities=%s", 
                    roomType.getRoomTypeId(),
                    roomType.getRoomType(),
                    roomType.getDescription(),
                    roomType.getSize(),
                    roomType.getBedCapacity(), 
                    roomType.getAmenities());
            System.out.println(output);
        }
    }
    
    private void createNewRoom() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Select the room type for the room"); 
        List<RoomType> roomTypes = roomTypeSessionBeanRemote.getRoomTypeList(); 
        for (int i = 0; i < roomTypes.size(); i++) {
            System.out.print(i + ": " + roomTypes.get(i).getRoomType());
        }
        System.out.print(">"); 
        Integer response = getIntegerInput();
        RoomType roomType = roomTypes.get(response);
        
        String roomNumber = "";
        
        while (true) {
            System.out.println("Enter the room number");
            System.out.print(">");
            roomNumber = scanner.next();
            
            //checks if there is a duplicate room number in the database
            if (roomSessionBeanRemote.isValidRoomNumber(roomNumber)) {
                break;
            } else {
                System.out.println("Invalid room number. A room already exists with that number. Please enter another number.");
            }
        }
        
        Room room = new Room(roomType, roomNumber, true);
        
        roomSessionBeanRemote.createNewRoom(room); 
    }
    
    private void deleteRoom() {
        Scanner scanner = new Scanner(System.in);
        String roomNumber;
        while (true) {
            System.out.println("Please enter the room number that you would like to delete."); 
            System.out.print(">");
            roomNumber = scanner.next();
            
            //isValidRoomNumber was used to check when creating the roomNumber, hence the inverse boolean is used here
            if (!roomSessionBeanRemote.isValidRoomNumber(roomNumber)) {
                break;
            } else {
                System.out.println("Room with that room number does not exist, please try again"); 
            }
        }
        
        roomSessionBeanRemote.deleteRoom(roomNumber);
        System.out.println("Room " + roomNumber + " was successfully deleted");
    }
    
    private void updateRoom() {
        
        Scanner scanner = new Scanner(System.in);
        String roomNumber = "0";
        
        while (true) {
            System.out.println("Please enter the room number that you want to update");
            roomNumber = scanner.next();
            //valid rooms are ones where there is no room with that number, want to check that there is a room with that number here
            if (!roomSessionBeanRemote.isValidRoomNumber(roomNumber)) {
                break;
            } else {
                System.out.println("Invalid room number. No room with that room number exist. Please enter another number.");
            }
        }
        
        Room room = roomSessionBeanRemote.getRoom(roomNumber);
        boolean status = room.getRoomStatus();
        RoomType roomType = room.getRoomType();
        
        while (true) {
            Integer response = 0; 
            while (response < 1 || response > 4) {
                System.out.println("Please select the detail that you would like to update"); 
                System.out.println("1: Room Type"); 
                System.out.println("2: Room Number"); //need to pay more attention to the changing of the roomType 
                System.out.println("3: Toggle Room Status"); 
                System.out.println("4: Done"); 
                System.out.print(">");

                response = getIntegerInput();
            }
            
            if (response == 1) {
                System.out.println("Select the room type for the room"); 
                List<RoomType> roomTypes = roomTypeSessionBeanRemote.getRoomTypeList(); 
                for (int i = 0; i < roomTypes.size(); i++) {
                    System.out.print(i + ": " + roomTypes.get(i).getRoomType());
                }
                System.out.println(">");
                Integer r = getIntegerInput();
                roomType = roomTypes.get(r);
                     
            } else if (response == 2) {
                System.out.println("Please enter new room number"); 
                System.out.print(">");
                roomNumber = scanner.next();
            } else if (response == 3) {
                if (status) {
                    System.out.println("Toggled to unavailable"); 
                    status = false;
                } else {
                    System.out.println("Toggled to available");
                    status = true;
                    }
            } else if (response == 4) {
                    break;
            }
        }
        
        roomSessionBeanRemote.updateRoom(room, roomType, roomNumber, status);
        
    }
    
    //update this afterwards
    private void viewAllRooms() {
        List<Room> rooms = roomSessionBeanRemote.viewAllRooms(); 
        for (Room r : rooms) {
            String output = String.format("roomId=%s, roomType=%s, roomStatus=%s", 
                    r.getRoomId(), r.getRoomType(), r.getRoomStatus());
            System.out.println(output);
        }
    }
    
    
    private void createNewRoomRate () {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please enter the room rate name"); 
        System.out.print(">");
        String name = scanner.next();
        
        System.out.println("Please select room type"); 
        List<RoomType> roomTypes = roomTypeSessionBeanRemote.getRoomTypeList(); 
        for (int i = 0; i < roomTypes.size(); i++) {
            System.out.print(i + ": " + roomTypes.get(i).getRoomType());
        }
        Integer response = getIntegerInput();
        RoomType roomType = roomTypes.get(response);
        
        response = 0;
        RateTypeEnum rateType = RateTypeEnum.PUBLISHED; 
        while (response < 1 || response > 4) {
            System.out.println("Please select the rate type");
            System.out.println("1: Published Rate");
            System.out.println("2: Normal Rate");
            System.out.println("3: Peak Rate");
            System.out.println("4: Promotion Rate");
            response = getIntegerInput();
           
            if (response == 2) {
                rateType = RateTypeEnum.NORMAL; 
            } else if (response == 3) {
                rateType = RateTypeEnum.PEAK;
            } else if (response == 4) {
                rateType = RateTypeEnum.PUBLISHED;
            }
        }
        
        System.out.println("Please enter the rate per night");
        System.out.print(">"); 
        String r = scanner.next(); 
        BigDecimal ratePerNight = new BigDecimal(r);
        
        System.out.println("Please enter the start date in the format DD/MM/YYYY");
        System.out.print(">");
        String s = scanner.next();
        Date startDate = new Date(s);
        
        System.out.println("Please enter the end date in the format DD/MM/YYYY");
        System.out.print(">");
        String d = scanner.next(); 
        Date endDate = new Date(d);
        
        //still need to do some of the setting later
        
        RoomRate newRoomRate = new RoomRate(name, roomType, rateType, ratePerNight, startDate, endDate);
        
        roomRateSessionBeanRemote.createNewRoomRate(newRoomRate);
               
    }
    
    private void updateRoomRate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select the room rate record that you would like to update");
        
        List<RoomRate> roomRates = roomRateSessionBeanRemote.getAllRoomRates();
        for (int i = 0; i < roomRates.size(); i++) {
            RoomRate r = roomRates.get(i);
            String output = String.format("roomId=%s, name=%s, roomType=%s; rateType=%s; ratePerNight=%s; validityPeriod=%s", 
                    r.getRoomRateId(), r.getName(), r.getRoomType(), r.getRateTypeEnum(), r.getRoomRate(), r.getStartDate(), r.getEndDate());
            System.out.println(i+ ": " + output);
        }
        Integer response = getIntegerInput();
        
        RoomRate selectedRoomRate = roomRates.get(response);
        
        String name = selectedRoomRate.getName();
        RoomType roomType = selectedRoomRate.getRoomType();
        RateTypeEnum rateType = selectedRoomRate.getRateTypeEnum(); 
        BigDecimal roomRate = selectedRoomRate.getRoomRate();
        Date startDate = selectedRoomRate.getStartDate(); 
        Date endDate = selectedRoomRate.getEndDate(); 
        
        while (true) {
            System.out.println("Please select which part of the room rate you would like to update"); 
            System.out.println("1: Name");
            System.out.println("2: RoomType");
            System.out.println("3: RateType");
            System.out.println("4: RoomRate");
            System.out.println("5: StartDate ");
            System.out.println("6: EndDate");
            System.out.println("7: Done");
            response = getIntegerInput();
            
            if (response == 1) {
                name = scanner.next();
            } else if (response == 2) {
                roomType = selectRoomType();
            } else if (response == 3) {
                rateType = selectRateTypeEnum();
            } else if (response == 4) {
                roomRate = scanner.nextBigDecimal();
            } else if (response == 5) {
                startDate = new Date(scanner.next());
            } else if (response == 6) {
                endDate = new Date(scanner.next());
            } else if (response == 7) {
                break;
            }
            
        }
        
    }
    
    private void deleteRoomRate() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please select the room rate"); 
        Long roomRateId = selectRoomRate();
        //roomRateSessionBeanRemote.deleteRoomRate();
    }
    
    private void viewAllRoomRates(){
        List<RoomRate> roomRates = roomRateSessionBeanRemote.getAllRoomRates();
        for (RoomRate r : roomRates) {
            String output = String.format("roomId=%s, name=%s, roomType=%s; rateType=%s; ratePerNight=%s; validityPeriod=%s", 
                    r.getRoomRateId(), r.getName(), r.getRoomType(), r.getRateTypeEnum(), r.getRoomRate(), r.getStartDate(), r.getEndDate());
            System.out.println(output);
        }
    }
    
    private RateTypeEnum selectRateTypeEnum() {
        Scanner scanner = new Scanner(System.in);
        RateTypeEnum rateType = RateTypeEnum.PUBLISHED;
            System.out.println("Please select the rate type");
            System.out.println("1: Published Rate");
            System.out.println("2: Normal Rate");
            System.out.println("3: Peak Rate");
            System.out.println("4: Promotion Rate");
            Integer response = getIntegerInput();
           
            if (response == 2) {
                rateType = RateTypeEnum.NORMAL; 
            } else if (response == 3) {
                rateType = RateTypeEnum.PEAK;
            } else if (response == 4) {
                rateType = RateTypeEnum.PUBLISHED;
            }
        
        return rateType;
    }
    
    private RoomType selectRoomType() {
        Scanner scanner = new Scanner(System.in);
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getRoomTypeList(); 
        System.out.println("Please select the room type");
        
        for (int i = 0; i < roomTypesList.size(); i++) {
            RoomType roomType = roomTypesList.get(i);
            
            String output = String.format("roomId=%s; RoomName=%s; Description=%s; Size=%s; BedCapacity=%s; Amenities=%s", 
                    roomType.getRoomTypeId(),
                    roomType.getRoomType(),
                    roomType.getDescription(),
                    roomType.getSize(),
                    roomType.getBedCapacity(), 
                    roomType.getAmenities());
            System.out.println(i + ":" + output);
        }
        Integer response = getIntegerInput();
        
        return roomTypesList.get(response);
    }
    
    private Long selectRoomRate() {
        Scanner scanner = new Scanner(System.in);
        List<RoomRate> roomRates = roomRateSessionBeanRemote.getAllRoomRates();
        for (int i = 0; i < roomRates.size(); i++) {
            RoomRate r = roomRates.get(i);
            String output = String.format("roomId=%s, name=%s, roomType=%s; rateType=%s; ratePerNight=%s; validityPeriod=%s", 
                    r.getRoomRateId(), r.getName(), r.getRoomType(), r.getRateTypeEnum(), r.getRoomRate(), r.getStartDate(), r.getEndDate());
            System.out.println(i + ": " + output);
        }
        Integer response = getIntegerInput();
        return roomRates.get(response).getRoomRateId();
    }
    
    private void walkInSearchRoom() {
        Scanner scanner = new Scanner(System.in);
        RoomType roomType;
        Date startDate, endDate;
        while (true) {
            System.out.println("Please select the Room Type");
            roomType = selectRoomType();
            Integer ranking = roomType.getRanking();
            String name = roomType.getRoomType();
            System.out.println("Please enter start date (DD/MM/YYYY)"); 
            startDate = new Date(getValidDate());//walk-in check in should only be done on the current day
            System.out.println("Please enter end date (DD/MM/YYYY)"); 
            endDate = new Date(getValidDate());
            
            while (!hotelInventorySessionBeanRemote.roomTypeIsAvailableForReservation(startDate, endDate, ranking)) {
                System.out.println("Sorry there are no availabilities for that Room Type, please select another Room Type.");
                roomType = selectRoomType(); 
                //assume that the other details will remain the same
            }
            
            System.out.println("Continue to reserve this Room Type?"); 
            System.out.println("1: Yes"); 
            System.out.println("2: No, select a different Room Type"); 
            Integer response = getIntegerInput();
            
            if (response == 1) {
                break;
            } else {
                continue;
            }
        }
        
        walkInReserveRoom(roomType, startDate, endDate);
        
    }
    
    private void walkInReserveRoom(RoomType roomType, Date startDate, Date endDate) {
        Scanner scanner = new Scanner(System.in);
        int ranking = roomType.getRanking();
        
        int numAvailRooms = hotelInventorySessionBeanRemote.numberOfAvailableRoomsForReservation(startDate, endDate, ranking);
        
        System.out.println("Please select the number of rooms to reserve. You can select up to " + numAvailRooms + " rooms.");
        Integer numRooms = getIntegerInput();
        
        while (numRooms > numAvailRooms || numRooms < 1) {
            System.out.println("Invalid number, please select a valid number"); 
            numRooms = getIntegerInput();
        }
        
        Reservation newReservation = new Reservation(startDate, endDate, numRooms);
        
        reserveRoomSessionBeanRemote.reserveRooms(newReservation, roomType);
        
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
