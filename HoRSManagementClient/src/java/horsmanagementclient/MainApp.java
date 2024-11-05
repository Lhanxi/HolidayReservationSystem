/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package horsmanagementclient;
import ejb.session.stateless.AllocateRoomSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.HotelInventorySessionBeanRemote;
import ejb.session.stateless.ReserveRoomSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import ejb.session.stateless.PartnerSessionBeanRemote;
import entity.Employee;
import entity.Partner;
import entity.Reservation;
import entity.Room;
import entity.RoomRate;
import entity.RoomType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import util.enumeration.EmployeeType;
import util.enumeration.PartnerType;
import util.enumeration.RateTypeEnum;
import util.exception.DuplicateUsernameException;
import util.exception.InvalidLoginException;
import util.exception.InvalidPartnerCreationException;
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
    private PartnerSessionBeanRemote partnerSessionBeanRemote;
    private AllocateRoomSessionBeanRemote allocateRoomSessionBeanRemote;
    
    public MainApp() {
    }

    public MainApp(RoomTypeSessionBeanRemote roomTypeSessionBeanRemote, RoomSessionBeanRemote roomSessionBeanRemote, RoomRateSessionBeanRemote roomRateSessionBeanRemote, EmployeeSessionBeanRemote employeeSessionBeanRemote, HotelInventorySessionBeanRemote hotelInventorySessionBeanRemote, ReserveRoomSessionBeanRemote reserveRoomSessionBeanRemote, AllocateRoomSessionBeanRemote allocateRoomSessionBeanRemote, PartnerSessionBeanRemote partnerSessionBeanRemote) {
        this.roomTypeSessionBeanRemote = roomTypeSessionBeanRemote;
        this.roomSessionBeanRemote = roomSessionBeanRemote;
        this.roomRateSessionBeanRemote = roomRateSessionBeanRemote;
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.hotelInventorySessionBeanRemote = hotelInventorySessionBeanRemote;
        this.reserveRoomSessionBeanRemote = reserveRoomSessionBeanRemote;
        this.partnerSessionBeanRemote = partnerSessionBeanRemote;
        this.allocateRoomSessionBeanRemote = allocateRoomSessionBeanRemote;
    }
    
    public void run() throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("*** Welcome to Holiday Reservation System Management Portal ***");
        Employee employee;
        boolean exitSystem = false;
        
        while (!exitSystem) {
        
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
            
            boolean loggedIn = true;
            
            while (loggedIn) {
                
                if (employee.getEmployeeType() == EmployeeType.SYSTEM_ADMIN) {
                    System.out.println("");
                    System.out.println("============");
                    System.out.println("Select what to do:"); 
                    System.out.println("");
                    System.out.println("1: Create new employees"); 
                    System.out.println("2: View all employees");
                    System.out.println("3: Create new partner");
                    System.out.println("4: View all partners");
                    System.out.println("5: Logout");
                    Integer response = getIntegerInput(); 

                    while (response < 1 || response > 5) {
                        System.out.println("Invalid input. Please try again."); 
                        response = getIntegerInput();
                    }

                    if (response == 1) {
                        createNewEmployee();
                    } else if (response == 2) {
                        viewAllEmployees();
                    } else if (response == 3) {
                        createNewPartner();
                    } else if (response == 4) {
                        viewAllPartners();
                    } else if (response ==5) {
                        loggedIn = false;
                        break;
                    } 
                    
                } else if (employee.getEmployeeType() == EmployeeType.OPERATION_MANAGER) {
                    while (true) {
                        System.out.println("Please select what you would like to do.");
                        System.out.println("1: RoomType functions");
                        System.out.println("2: Room functions");
                        System.out.println("3: Logout");
                        Integer r = getIntegerInput();
                        if (r == 1) {
                            while (true) {
                                //For RoomType
                                System.out.println("");
                                System.out.println("============");
                                System.out.println("Select what to do:"); 
                                System.out.println("");
                                System.out.println("1: Create new room type");
                                System.out.println("2: View room type details");
                                System.out.println("3: Update Room Type Details");
                                System.out.println("4: Delete Room Type");
                                System.out.println("5: View all Room Type");
                                System.out.println("6: Back");
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
                            while (true) {
                                //for Room
                                System.out.println("");
                                System.out.println("============");
                                System.out.println("Select what to do:"); 
                                System.out.println("");
                                System.out.println("1: Create New Room"); 
                                System.out.println("2: Update Room");
                                System.out.println("3: Delete Room");
                                System.out.println("4: View All Rooms");
                                System.out.println("5: Back");
                                Integer newResponse = getIntegerInput();

                                if (newResponse == 1) {
                                    createNewRoom(); 
                                } else if (newResponse == 2) {
                                    updateRoom(); 
                                } else if (newResponse == 3) {
                                    deleteRoom();
                                } else if (newResponse == 4 ){
                                    viewAllRooms();
                                } else if (newResponse == 5) {
                                    break;
                                }
                            }
                        } else if (r == 3) {
                            loggedIn = false;
                            break;
                        }
                    }
                } else if (employee.getEmployeeType() == EmployeeType.SALES_MANAGER) {
                    //for RoomRate
                    while (true) {
                        System.out.println("");
                        System.out.println("============");
                        System.out.println("Select what to do:"); 
                        System.out.println("");
                        System.out.println("1: Create New Room Rate"); 
                        System.out.println("2: Update RoomRate");
                        System.out.println("3: Delete Room Rate");
                        System.out.println("4: View All Room Rates");
                        System.out.println("5: Logout");
                        Integer re = getIntegerInput();

                        if (re == 1) {
                            createNewRoomRate();
                        } else if (re == 2) {
                            updateRoomRate();
                        } else if (re == 3) {
                            System.out.println("not available yet LOL"); 
                        } else if (re == 4 ) {
                            viewAllRoomRates();
                        } else if (re == 5) {
                            loggedIn = false;
                            break;
                        }
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
                            loggedIn = false;
                            break;
                        }
                    }

                } else if (employee.getEmployeeType() == EmployeeType.SYSTEM) {
                    while (true) {
                        System.out.println("1: Allocate Rooms to Current Day Reservations"); 
                        System.out.println("2: Logout");
                        Integer response = 0;

                        while (response < 1 || response > 2) {
                            response = getIntegerInput();
                        }
                        
                        if (response == 1) {
                            allocateRoomstoCurrentDayReservations();
                        } else if (response == 2) {
                            loggedIn = false;
                            break;
                        }
                    }
                }
            }
        }
    }
    
    private void createNewEmployee() throws DuplicateUsernameException {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("===Create New Employee==="); 
        System.out.println("Please enter employee username");
        System.out.print(">"); 
        String username = scanner.next();
        
        System.out.println("Please enter employee password");
        System.out.print(">"); 
        String password = scanner.next();
        
        while (response < 1 || response > 4) {
            System.out.println("Please select the employee type");
            System.out.println("1: System Admin");
            System.out.println("2: Operation Manager");
            System.out.println("3: Sales Manager");
            System.out.println("4: Guest Relation Officer");
            response = getIntegerInput();
        }
        
        EmployeeType employeeType = EmployeeType.SYSTEM_ADMIN; 
        
        if (response == 2) {
            employeeType = EmployeeType.OPERATION_MANAGER; 
        } else if (response == 3) {
            employeeType = EmployeeType.SALES_MANAGER;
        } else if (response == 4) {
            employeeType = EmployeeType.GUEST_RELATION_OFFICER;
        }
        boolean isCreated = false;
        
        do {
            Employee employee = new Employee(username, password, employeeType); 
            try {
                employeeSessionBeanRemote.createNewEmployee(employee);
                System.out.println("Employee created successfully!");
                isCreated = true;
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage() + " Please enter a different username.");
                System.out.println("Please enter employee username:");
                System.out.print("> "); 
                username = scanner.next();
            }
        } while (!isCreated);
    }
    
    private void viewAllEmployees() {
        List<Employee> employees = employeeSessionBeanRemote.retrieveListOfAllEmployees();
        
        System.out.println("===List of all employees==="); 
        System.out.println("");
        
        for (Employee e : employees) {
            String output = String.format("employeeId=%d; username=%s; password=%s; employeeType=%s", 
                    e.getEmployeeId(), 
                    e.getUsername(), 
                    e.getPassword(), 
                    e.getEmployeeType()); 
            System.out.println(output);
        }
        
        System.out.println("");
    }
    
    private void createNewPartner() {
        try {
            Scanner scanner = new Scanner(System.in);
            Integer response = 0;
            PartnerType partnerType = PartnerType.EMPLOYEE;

            System.out.println("===Create New Partner==="); 
            System.out.println("Please enter employee username");
            System.out.print(">"); 
            String username = scanner.next();

            System.out.println("Please enter employee password");
            System.out.print(">"); 
            String password = scanner.next();

            while (response < 1 || response > 4) {
                System.out.println("Please select the partner type");
                System.out.println("1: Partner Employee");
                System.out.println("2: Partner Reservation Manager");
                response = getIntegerInput();
            }

            if (response == 2) {
                partnerType = PartnerType.RESERVATION_MANAGER;
            }
            Partner partner = new Partner(username, password, partnerType);
            partnerSessionBeanRemote.createNewPartner(partner);
        } catch (InvalidPartnerCreationException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void viewAllPartners() {
        List<Partner> partners = partnerSessionBeanRemote.retrieveListOfAllPartners();
        System.out.println("===List of all partners==="); 
        System.out.println("");
        
        for (Partner p : partners) {
            String output = String.format("partnerId=%d; username=%s; password=%s; partnerType=%s", 
                    p.getPartnerId(), 
                    p.getUsername(), 
                    p.getPassword(), 
                    p.getPartnerType()); 
            System.out.println(output);
        }
       
        System.out.println("");
    }
    
    private void createNewRoomType() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please enter the room type name.");
        System.out.print(">"); 
        String roomNameType = scanner.next();
        scanner.nextLine();
        
        System.out.println("Please include the description for the room type"); 
        System.out.print(">"); 
        String description = scanner.nextLine();
        
        System.out.println("Please indicate the size of the room"); 
        System.out.print(">"); 
        String size = scanner.next(); 
        
        System.out.println("Please indicate the bed capacity of the room"); 
        System.out.print(">"); 
        String bedCapacity = scanner.next(); 
        scanner.nextLine();
        
        System.out.println("Please indicate the room amentities, separating them with a comma"); 
        System.out.print(">"); 
        String amenities = scanner.nextLine();
        
        Integer ranking = getRoomTypeRanking();
        
        RoomType newRoomType = new RoomType(roomNameType, description, size, bedCapacity, amenities, false);
        
        Long roomTypeId = roomTypeSessionBeanRemote.createNewRoomType(newRoomType, ranking); 
        
        System.out.println("Room Type Successfully created, Room Type Id: " + roomTypeId);
    }
    
    private void viewRoomTypeDetails() {
        Scanner scanner = new Scanner(System.in);
        
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getRoomTypeList(); 
        System.out.println("Select the room type that you would like to view");
                
        for (int i = 0; i < roomTypesList.size(); i++) {
            System.out.println(i + ": " + roomTypesList.get(i).getName());
        }
        System.out.print(">"); 
        Integer response = scanner.nextInt(); 

        System.out.println("RoomId: " + roomTypesList.get(response).getRoomTypeId());
        System.out.println("Room Name: " + roomTypesList.get(response).getName());
        System.out.println("Description: " + roomTypesList.get(response).getDescription());
        System.out.println("Bed Capacity: " + roomTypesList.get(response).getBedCapacity());
        System.out.println("Amenities: " + roomTypesList.get(response).getAmenities());
    }
    
    private void updateRoomType() {
        Scanner scanner = new Scanner(System.in);
        
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getRoomTypeList(); 
        System.out.println("Select the room type that you would like to update");
                
        for (int i = 0; i < roomTypesList.size(); i++) {
            System.out.println(i + ": " + roomTypesList.get(i).getName());
        }
        
        Integer response = scanner.nextInt(); 
        RoomType roomType = roomTypesList.get(response);
        
        String output = String.format("roomId: %s; RoomName: %s; Description: %s; Size: %s; BedCapacit: %s; Amenities: %s", 
                    roomType.getRoomTypeId(),
                    roomType.getName(),
                    roomType.getDescription(),
                    roomType.getSize(),
                    roomType.getBedCapacity(), 
                    roomType.getAmenities());
        System.out.println("Selected room" + output);
        
        Long roomTypeId = roomType.getRoomTypeId();
        String newRoomTypeName = roomType.getName();
        String newDescription = roomType.getDescription();
        String newSize = roomType.getSize();
        String newBedCapacity = roomType.getBedCapacity();
        String newAmenities = roomType.getAmenities();
        Integer ranking = roomType.getRanking();
        
        while (true) {
            System.out.println("Select the detail you would like to change: "); 
            System.out.println("1: Room Name"); 
            System.out.println("2: Description"); 
            System.out.println("3: Size");
            System.out.println("4: Bed Capacity"); 
            System.out.println("5: Amenities"); 
            System.out.println("6: Ranking");
            System.out.println("7: Done"); 
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
                ranking = getRoomTypeRanking();
            }  else if (response == 7) {
                break;
            }
        }
        roomTypeSessionBeanRemote.updateRoomTypeDetails(roomTypeId, newRoomTypeName, newDescription, newSize, newBedCapacity, newAmenities, ranking); 
        
    }
    
    private void deleteRoomType() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please select the room type that you want to delete.");
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getRoomTypeList(); 
        for (int i = 0; i < roomTypesList.size(); i++) {
            System.out.println(i + ": " + roomTypesList.get(i).getName()); 
        }
        Integer response = getIntegerInput();
        RoomType roomType = roomTypesList.get(response); 
        Long roomTypeId = roomType.getRoomTypeId();
        
        String output = roomTypeSessionBeanRemote.deleteRoomType(roomTypeId);
        System.out.println(output);
    }
    
    private void viewAllRoomTypes() {
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getRoomTypeList(); 
        
        for (RoomType roomType: roomTypesList) {
            String output = String.format("roomId: %s; RoomName: %s; Description: %s; Size: %s; BedCapacity: %s; Amenities: %s; Ranking: %d", 
                    roomType.getRoomTypeId(),
                    roomType.getName(),
                    roomType.getDescription(),
                    roomType.getSize(),
                    roomType.getBedCapacity(), 
                    roomType.getAmenities(),
                    roomType.getRanking());
            System.out.println(output);
        }
    }
    
    private void createNewRoom() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Select the room type for the room"); 
        List<RoomType> roomTypes = roomTypeSessionBeanRemote.getEnabledRoomTypeList(); 
        
        for (int i = 0; i < roomTypes.size(); i++) {
            System.out.println(i + ": " + roomTypes.get(i).getName());
        }
        
        Integer response = -1;
        while (response < 0 || response > roomTypes.size() - 1) {
            response = getIntegerInput();
        }
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
        
        Room room = new Room(roomType, roomNumber, true, false);
        
        Long roomID = roomSessionBeanRemote.createNewRoom(room);
        
        System.out.println("Room successfully created! Room ID: " + roomID);
        System.out.println("");
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
        
        String output = roomSessionBeanRemote.deleteRoom(roomNumber);
        System.out.println(output);
    }
    
    private void updateRoom() {
        
        Scanner scanner = new Scanner(System.in);
        String roomNumber = "0";
        
        while (true) {
            System.out.println("Please enter the room number that you want to update");
            System.out.print(">");
            roomNumber = scanner.next();
            //valid rooms are ones where there is no room with that number, want to check that there is a room with that number here
            if (!roomSessionBeanRemote.isValidRoomNumber(roomNumber)) {
                break;
            } else {
                System.out.println("Invalid room number. No room with that room number exist. Please enter another number.");
            }
        }
        
        Room room = roomSessionBeanRemote.getRoom(roomNumber);
        Long roomId = room.getRoomId();
        boolean status = room.getRoomStatus();
        RoomType roomType = room.getRoomType();
        boolean isDisabled = room.isDisabled();
        
        while (true) {
            Integer response = 0; 
            while (response < 1 || response > 5) {
                System.out.println("Please select the detail that you would like to update"); 
                System.out.println("1: Room Type"); 
                System.out.println("2: Room Number"); //need to pay more attention to the changing of the roomType 
                System.out.println("3: Toggle Room Status"); 
                System.out.println("4: Enable/Disable Room");
                System.out.println("5: Done"); 

                response = getIntegerInput();
            }
            
            if (response == 1) {
                
                Integer r = -1;
                List<RoomType> roomTypes = roomTypeSessionBeanRemote.getEnabledRoomTypeList(); 
                
                while (r < 0 || r > roomTypes.size()) {
                    System.out.println("Select the room type for the room"); 
                    for (int i = 0; i < roomTypes.size(); i++) {
                        System.out.println(i + ": " + roomTypes.get(i).getName());
                    }
                    System.out.println(">");
                    r = getIntegerInput();
                    roomType = roomTypes.get(r);
                }
            } else if (response == 2) {
                boolean isValidRoomNumber = false;
                
                while (!isValidRoomNumber) {
                    System.out.println("Please enter new room number"); 
                    System.out.print(">");
                    roomNumber = scanner.next();
                    isValidRoomNumber = roomSessionBeanRemote.isValidRoomNumber(roomNumber);
                    if (!isValidRoomNumber) {
                        System.out.println("A room with that room number exists, please enter a different room number.");
                        System.out.println(""); 
                    }
                }
            } else if (response == 3) {
                if (status) {
                    System.out.println("Toggled to unavailable"); 
                    status = false;
                } else {
                    System.out.println("Toggled to available");
                    status = true;
                    }
            } else if (response == 4) {
                if (isDisabled) {
                    isDisabled = false;
                    System.out.println("Room set to enabled");
                } else {
                    isDisabled = true; 
                    System.out.println("Room set to disabled");
                }
            } else if (response == 5) {
                break;
            }
                
        }
        
        roomSessionBeanRemote.updateRoom(roomId, roomType, roomNumber, status, isDisabled);
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
        List<RoomType> roomTypes = roomTypeSessionBeanRemote.getEnabledRoomTypeList(); 
        for (int i = 0; i < roomTypes.size(); i++) {
            System.out.println(i + ": " + roomTypes.get(i).getName());
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
        
        BigDecimal ratePerNight = getBigDecimalInput("Please enter rate per night");
        Date startDate, endDate;
        
        while (true) {
            System.out.println("Please enter the start date in the format DD/MM/YYYY");
            startDate = getDateInput();

            System.out.println("Please enter the end date in the format DD/MM/YYYY");
            endDate = getDateInput();

            if (endDate.before(startDate)) {
                System.out.println("End Date cannot be before start date. Please try again.");
            } else {
                break; 
            }
        }
        //still need to do some of the setting later
        
        RoomRate newRoomRate = new RoomRate(name, roomType, rateType, ratePerNight, startDate, endDate);
        
        roomRateSessionBeanRemote.createNewRoomRate(newRoomRate);
        System.out.println("New Room Rate successfully created!");
    }
    
    private void updateRoomRate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select the room rate record that you would like to update");
        
        List<RoomRate> roomRates = roomRateSessionBeanRemote.getAllRoomRates();
        for (int i = 0; i < roomRates.size(); i++) {
            RoomRate r = roomRates.get(i);
            String output = String.format("roomId=%s, name=%s, roomType=%s; rateType=%s; ratePerNight=%s; startDate=%s; endDate=%s", 
                    r.getRoomRateId(), r.getName(), r.getRoomType(), r.getRateTypeEnum(), r.getRoomRateAmount(), r.getStartDate(), r.getEndDate());
            System.out.println(i+ ": " + output);
        }
        Integer response = getIntegerInput();
        
        RoomRate selectedRoomRate = roomRates.get(response);
        Long selectedRoomRateId = selectedRoomRate.getRoomRateId();
        
        String name = selectedRoomRate.getName();
        RoomType roomType = selectedRoomRate.getRoomType();
        RateTypeEnum rateType = selectedRoomRate.getRateTypeEnum(); 
        BigDecimal roomRateAmount = selectedRoomRate.getRoomRateAmount();
        Date startDate = selectedRoomRate.getStartDate(); 
        Date endDate = selectedRoomRate.getEndDate(); 
        
        while (true) {
            System.out.println("");
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
                System.out.println("Please enter new room rate name");
                System.out.print(">");
                name = scanner.next();
            } else if (response == 2) {
                roomType = selectRoomType();
            } else if (response == 3) {
                rateType = selectRateTypeEnum();
            } else if (response == 4) {
                roomRateAmount = getBigDecimalInput("Please enter new room rate amount");
            } else if (response == 5) {
                System.out.println("Please enter new start date");
                Date s = getDateInput();
                if (s.after(endDate)) {
                    System.out.println("Invalid start date. Start date cannot be after end date. Please change end date first");
                } else {
                    startDate = s;
                }
                
            } else if (response == 6) {
                System.out.println("Please enter new end date");
                
                Date e = getDateInput();
                if (e.before(startDate)) {
                    System.out.println("Invalid end date. End date cannot be before start date. Please change start date first");
                } else {
                    endDate = e;
                }
               
            } else if (response == 7) {
                break;
            }
            
        }
        roomRateSessionBeanRemote.updateRoomRate(selectedRoomRateId, name, roomType, rateType, roomRateAmount, startDate, endDate);
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
            String output = String.format("roomId=%s, name=%s, roomType=%s; rateType=%s; ratePerNight=%s; startDate=%s, endDate=%s", 
                    r.getRoomRateId(), r.getName(), r.getRoomType(), r.getRateTypeEnum(), r.getRoomRateAmount(), r.getStartDate(), r.getEndDate());
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
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getEnabledRoomTypeList(); 
        
        System.out.println("Please select the room type:");
        
        for (int i = 0; i < roomTypesList.size(); i++) {
            RoomType roomType = roomTypesList.get(i);
            
            String output = String.format("roomId: %s; RoomName: %s; Description: %s; Size: %s; BedCapacity: %s; Amenities: %s; Ranking: %d", 
                    roomType.getRoomTypeId(),
                    roomType.getName(),
                    roomType.getDescription(),
                    roomType.getSize(),
                    roomType.getBedCapacity(), 
                    roomType.getAmenities(),
                    roomType.getRanking());
            System.out.println(i + ": " + output);
        }
        Integer response = getIntegerInput();
        
        return roomTypesList.get(response);
    }
    
    private int getRoomTypeRanking() { 
        Scanner scanner = new Scanner(System.in);
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getEnabledRoomTypeList(); 
        if (roomTypesList.size() == 0) {
            System.out.println("First room type to be created, ranking 0 assigned"); 
            return 0; //this means that it is the first room Type to be created
        } 
        
        System.out.println("Please select the room type that you would like to insert the room type before.");
        roomTypesList.sort((r1, r2) -> Integer.compare(r1.getRanking(), r2.getRanking()));

        for (int i = 0; i < roomTypesList.size(); i++) {
            RoomType roomType = roomTypesList.get(i);
            
            String output = String.format("RoomName: %s; Ranking: %d", 
                    roomType.getName(), 
                    roomType.getRanking());
            System.out.println(i + ": " + output);
        }
        System.out.println((roomTypesList.size()) + ": Insert Room Type at the end");
        Integer response = -1; 
        
        while (response < 0 || response > roomTypesList.size()) {
            response = getIntegerInput();
        }
        return response; 
    }
    
    private Long selectRoomRate() {
        Scanner scanner = new Scanner(System.in);
        List<RoomRate> roomRates = roomRateSessionBeanRemote.getAllRoomRates();
        for (int i = 0; i < roomRates.size(); i++) {
            RoomRate r = roomRates.get(i);
            String output = String.format("roomId=%s, name=%s, roomType=%s; rateType=%s; ratePerNight=%s; validityPeriod=%s", 
                    r.getRoomRateId(), r.getName(), r.getRoomType(), r.getRateTypeEnum(), r.getRoomRateAmount(), r.getStartDate(), r.getEndDate());
            System.out.println(i + ": " + output);
        }
        Integer response = getIntegerInput();
        return roomRates.get(response).getRoomRateId();
    }
    
    private void walkInSearchRoom() {
        Scanner scanner = new Scanner(System.in);
        
        Date startDate, endDate;
        
        while (true) {
            System.out.println("Please enter start date in the format DD/MM/YYYY"); 
            startDate = getDateInput();
            System.out.println("");
            
            System.out.println("Please enter end date in the format DD/MM/YYYY"); 
            endDate = getDateInput();
            System.out.println("");
            
            if (endDate.before(startDate)) {
                System.out.println("End Date cannot be before start date. Please try again.");
            } else {
                break; 
            }
        }
        
        HashMap<String, Integer> availableRoomTypes = hotelInventorySessionBeanRemote.getAvailableRoomTypes(startDate, endDate);
        List<String> roomTypes = new ArrayList<>();
        
        for (Map.Entry<String, Integer> entry : availableRoomTypes.entrySet()) {
            roomTypes.add(entry.getKey());
        }
        
        System.out.println("Available room types. Please select which room type to make a reservation");
        for (int i = 0; i < roomTypes.size(); i++) {
            System.out.println(i + ": " + roomTypes.get(i) + ": " + availableRoomTypes.get(roomTypes.get(i)));
        }
        
        Integer response = -1;
        String roomTypeName;
        while (response < 0 || response > roomTypes.size()) {
            response = getIntegerInput();
            if (response < 0 || response > roomTypes.size()) {
                System.out.println("Invalid response, please try again");
                continue;
            }
        }
        roomTypeName = roomTypes.get(response);
        walkInReserveRoom(roomTypeName, startDate, endDate, availableRoomTypes.get(roomTypeName));
    }


    private void walkInReserveRoom(String roomTypeName, Date startDate, Date endDate, Integer numAvailRooms) {
        Scanner scanner = new Scanner(System.in);
        RoomType roomType = roomTypeSessionBeanRemote.getRoomTypeByName(roomTypeName);
        Long roomTypeId = roomType.getRoomTypeId();
        
        System.out.println("Please select the number of rooms to reserve. You can select up to " + numAvailRooms + " rooms.");
        Integer numRooms = getIntegerInput();
        
        while (numRooms > numAvailRooms || numRooms < 1) {
            System.out.println("Invalid number, please select a valid number"); 
            numRooms = getIntegerInput();
        }
        
        Reservation newReservation = new Reservation(startDate, endDate, numRooms);
        
        Long newRoomTypeId = reserveRoomSessionBeanRemote.createReservation(newReservation, roomType);
        BigDecimal totalCost = reserveRoomSessionBeanRemote.calculateReservationPriceForWalkIn(newReservation, newRoomTypeId);
        System.out.println("Total cost: " + totalCost);
    }
    
    private void allocateRoomstoCurrentDayReservations() {
        allocateRoomSessionBeanRemote.allocateRooms();
        System.out.println("Rooms have been allocated");
        
        Date currentDate = getCurrentDate();
        System.out.println(currentDate);
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
    
    public BigDecimal getBigDecimalInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        BigDecimal value = null;
        boolean validInput = false;

        while (!validInput) {
            System.out.println(prompt);
            System.out.print("> ");
            String input = scanner.nextLine();

            try {
                value = new BigDecimal(input);
                if (value.signum() < 0) {
                    throw new NumberFormatException("Value must be non-negative.");
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid BigDecimal number.");
            }
        }

        return value;
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
    
        
    private Date getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
    
    
}
