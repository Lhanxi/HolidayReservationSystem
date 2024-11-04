/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package horsreservationclient;

import javax.ejb.EJB;
import ejb.session.stateless.HotelInventorySessionBeanRemote;
import ejb.session.stateless.ReserveRoomSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import ejb.session.stateless.GuestSessionBeanRemote;
/**
 *
 * @author leunghanxi
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    @EJB
    private static RoomRateSessionBeanRemote roomRateSessionBeanRemote;

    @EJB
    private static RoomSessionBeanRemote roomSessionBeanRemote;
    
    @EJB
    private static RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    @EJB
    private static HotelInventorySessionBeanRemote hotelInventorySessionBeanRemote;
    @EJB 
    private static ReserveRoomSessionBeanRemote reserveRoomSessionBeanRemote;
    @EJB
    private static GuestSessionBeanRemote guestSessionBeanRemote;
    public static void main(String[] args) throws Exception {
        MainApp mainApp = new MainApp(roomTypeSessionBeanRemote, roomSessionBeanRemote, roomRateSessionBeanRemote, hotelInventorySessionBeanRemote, reserveRoomSessionBeanRemote, guestSessionBeanRemote); 
        mainApp.runApp(); 
    }
}