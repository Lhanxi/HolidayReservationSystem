/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package horsmanagementclient;

import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.HotelInventorySessionBeanRemote;
import ejb.session.stateless.ReserveRoomSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import ejb.session.stateless.PartnerSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author jeremy
 */
public class Main {
    @EJB 
    private static RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    @EJB
    private static RoomSessionBeanRemote roomSessionBeanRemote; 
    @EJB
    private static RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    @EJB 
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    @EJB
    private static HotelInventorySessionBeanRemote hotelInventorySessionBeanRemote;
    @EJB 
    private static ReserveRoomSessionBeanRemote reserveRoomSessionBeanRemote;
    @EJB
    private static PartnerSessionBeanRemote partnerSessionBeanRemote;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        MainApp mainApp = new MainApp(roomTypeSessionBeanRemote, roomSessionBeanRemote, roomRateSessionBeanRemote, employeeSessionBeanRemote, hotelInventorySessionBeanRemote, reserveRoomSessionBeanRemote, partnerSessionBeanRemote); 
        mainApp.run(); 
        
    }
    
}
