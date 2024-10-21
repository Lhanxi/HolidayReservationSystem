/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package horsmanagementclient;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import entity.Room;
import entity.RoomRate;
import entity.RoomType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.enumeration.RateTypeEnum;
/**
 *
 * @author jeremy
 */
public class MainApp {
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    private RoomSessionBeanRemote roomSessionBeanRemote; 
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    
    public MainApp() {
    }

    public MainApp(RoomTypeSessionBeanRemote roomTypeSessionBeanRemote, RoomSessionBeanRemote roomSessionBeanRemote, RoomRateSessionBeanRemote roomRateSessionBeanRemote) {
        this.roomTypeSessionBeanRemote = roomTypeSessionBeanRemote;
        this.roomSessionBeanRemote = roomSessionBeanRemote;
        this.roomRateSessionBeanRemote = roomRateSessionBeanRemote;
    }
    
    public void run() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("1: RoomType functions");
        System.out.println("2: Room functions");
        System.out.println("3: RoomRate functions");
        System.out.println(">"); 
        Integer r = scanner.nextInt();
        
        if (r == 1) {
            //For RoomType
        System.out.println("Select what to do:"); 
        System.out.println("1: Create new room type");
        System.out.println("2: View room type details");
        System.out.println("3: Update Room Type Details");
        System.out.println(">"); 
        
        
        Integer response = scanner.nextInt();
        
        if (response == 1) {
            createNewRoomType(); 
        } else if (response == 2) {
            viewRoomTypeDetails(); 
        } else if (response == 3) {
            updateRoomType(); 
        } else if (response == 5) {
            viewAllRoomTypes();
        }
        
        } else if (r ==2) {
             //for Room
        System.out.println("Select what to do:"); 
        System.out.println("1: Create New Room"); 
        System.out.println("2: Update Room");
        System.out.println("3: Delete Room");
        System.out.println("4: View All Rooms");
        System.out.println(">"); 
        Integer newResponse = scanner.nextInt(); 
        
        if (newResponse == 1) {
            createNewRoom(); 
        } else if (newResponse == 2) {
            updateRoom(); 
        } else if (newResponse == 3) {
            System.out.println("not avail yet LOL");
        } else if (newResponse == 4 ){
            viewAllRooms();
        }
        
        } else if (r ==3) {
            //for RoomRate
        System.out.println("Select what to do");
        System.out.println("1: Create New Room Rate"); 
        System.out.println("2: Update RoomRate ");
        System.out.println("3: Delete Room Rate");
        System.out.println("4: View All Room Rates");
        System.out.println(">"); 
        Integer re = scanner.nextInt();
        
        if (re == 1) {
            createNewRoomRate();
        } else if (re == 2) {
            System.out.println("not available yet LOL"); 
        } else if (re == 3) {
            System.out.println("not available yet LOL"); 
        } else if (re == 4 ) {
            viewAllRoomRates();
        }
        } 
        
        
        
        
       
        
        
        
        
    
    }
    
    private void createNewRoomType() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please enter the room type name.");
        System.out.println(">"); 
        String roomNameType = scanner.next();
        
        System.out.println("Please include the description for the room type"); 
        System.out.println(">"); 
        String description = scanner.next();
        
        System.out.println("Please indicate the size of the room"); 
        System.out.println(">"); 
        String size = scanner.next(); 
        
        System.out.println("Please indicate the bed capacity of the room"); 
        System.out.println(">"); 
        String bedCapacity = scanner.next(); 
        
        System.out.println("Please indicate the room amentities, separating them with a comma"); 
        System.out.println(">"); 
        String amenities = scanner.next();
        
        RoomType newRoomType = new RoomType(roomNameType, description, size, bedCapacity, amenities);
        
        System.out.println(newRoomType.toString()); //debugging line
        
        if(roomTypeSessionBeanRemote == null)
        {
            System.out.println("NULL NULL");
        }
        
        Long roomTypeId = roomTypeSessionBeanRemote.createNewRoomType(newRoomType); 
        
        System.out.println("Room Type Successfully created, Room Type Id: " + roomTypeId);
    }
    
    private void viewRoomTypeDetails() {
        Scanner scanner = new Scanner(System.in);
        
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getRoomTypeList(); 
        System.out.println("Select the room type that you would like to view");
                
        for (int i = 0; i < roomTypesList.size(); i++) {
            System.out.println(i + ": " + roomTypesList.get(i).getRoomTypeId() + " , " + roomTypesList.get(i).getName());
        }
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
        System.out.println("Select the room that you would like to update");
                
        for (int i = 0; i < roomTypesList.size(); i++) {
            System.out.println(i + ": " + roomTypesList.get(i).getRoomTypeId() + " , " + roomTypesList.get(i).getName());
        }
        Integer response = scanner.nextInt(); 
        RoomType roomType = roomTypesList.get(response);
        
        String output = String.format("roomId=%s; RoomName=%s; Description=%s; Size =%s; BedCapacity=%s; Amenities=%s", 
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
        
        while (true) {
            System.out.println("Select the detail you would like to change: "); 
            System.out.println("1: Room Name"); 
            System.out.println("2: Description"); 
            System.out.println("3: Size");
            System.out.println("4: Bed Capacity"); 
            System.out.println("5: Amenities"); 
            System.out.println("6: Done"); 
            response = scanner.nextInt();

            if (response == 1) {
                System.out.println("Please enter the new room type.");
                System.out.println(">");
                newRoomTypeName = scanner.next();

            } else if (response == 2) {
                System.out.println("Please enter new description"); 
                System.out.println(">");
                newDescription = scanner.next();
            } else if (response == 3) {
                System.out.println("Please enter the new size"); 
                System.out.println(">"); 
                newSize = scanner.next(); 
            } else if (response == 4) {
                System.out.println("Please enter the new bed capacity"); 
                System.out.println(">"); 
                newBedCapacity = scanner.next(); 
            } else if (response == 5) {
                System.out.println("Please enter the new amenities"); 
                System.out.println(">"); 
                newAmenities = scanner.next(); 
            } else if (response == 6) {
                break;
            }
        }
        roomTypeSessionBeanRemote.updateRoomTypeDetails(roomTypeId, newRoomTypeName, newDescription, newSize, newBedCapacity, newAmenities); 
        
    }
    
    private void viewAllRoomTypes() {
        List<RoomType> roomTypesList = roomTypeSessionBeanRemote.getRoomTypeList(); 
        
        for (RoomType roomType: roomTypesList) {
            String output = String.format("roomId=%s; RoomName=%s; Description=%s; Size=%s; BedCapacity=%s; Amenities=%s", 
                    roomType.getRoomTypeId(),
                    roomType.getName(),
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
            System.out.print(i + ": " + roomTypes.get(i).getName());
        }
        Integer response = scanner.nextInt();
        RoomType roomType = roomTypes.get(response);
        
        String roomNumber = "";
        
        while (true) {
            System.out.println("Enter the room number");
            System.out.println(">");
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
                System.out.println(">");

                response = scanner.nextInt();
            }
            
            if (response == 1) {
                System.out.println("Select the room type for the room"); 
                List<RoomType> roomTypes = roomTypeSessionBeanRemote.getRoomTypeList(); 
                for (int i = 0; i < roomTypes.size(); i++) {
                    System.out.print(i + ": " + roomTypes.get(i).getName());
                }
                System.out.println(">");
                Integer r = scanner.nextInt();
                roomType = roomTypes.get(r);
                     
            } else if (response == 2) {
                System.out.println("Please enter new room number"); 
                System.out.println(">");
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
        System.out.println(">");
        String name = scanner.next();
        
        System.out.println("Please select room type"); 
        List<RoomType> roomTypes = roomTypeSessionBeanRemote.getRoomTypeList(); 
        for (int i = 0; i < roomTypes.size(); i++) {
            System.out.print(i + ": " + roomTypes.get(i).getName());
        }
        Integer response = scanner.nextInt();
        RoomType roomType = roomTypes.get(response);
        
        response = 0;
        RateTypeEnum rateType = RateTypeEnum.PUBLISHED; 
        while (response < 1 || response > 4) {
            System.out.println("Please select the rate type");
            System.out.println("1: Published Rate");
            System.out.println("2: Normal Rate");
            System.out.println("3: Peak Rate");
            System.out.println("4: Promotion Rate");
            System.out.println(">"); 
            response = scanner.nextInt();
           
            if (response == 2) {
                rateType = RateTypeEnum.NORMAL; 
            } else if (response == 3) {
                rateType = RateTypeEnum.PEAK;
            } else if (response == 4) {
                rateType = RateTypeEnum.PUBLISHED;
            }
        }
        
        System.out.println("Please enter the rate per night");
        System.out.println(">"); 
        String r = scanner.next(); 
        BigDecimal ratePerNight = new BigDecimal(r);
        
        System.out.println("Please enter the start date in the format DD/MM/YYYY");
        System.out.println(">");
        String s = scanner.next();
        Date startDate = new Date(s);
        
        System.out.println("Please enter the end date in the format DD/MM/YYYY");
        System.out.println(">");
        String d = scanner.next(); 
        Date endDate = new Date(d);
        
        //still need to do some of the setting later
        
        RoomRate newRoomRate = new RoomRate(name, roomType, rateType, ratePerNight, startDate, endDate);
        
        roomRateSessionBeanRemote.createNewRoomRate(newRoomRate);
               
    }
    
    private void viewAllRoomRates(){
        List<RoomRate> roomRates = roomRateSessionBeanRemote.getAllRoomRates();
        for (RoomRate r : roomRates) {
            String output = String.format("roomId=%s, name=%s, roomType=%s; rateType=%s; ratePerNight=%s; validityPeriod=%s", 
                    r.getRoomRateId(), r.getName(), r.getRoomType(), r.getRateTypeEnum(), r.getRoomRate(), r.getStartDate(), r.getEndDate());
            System.out.println(output);
        }
    }
}
