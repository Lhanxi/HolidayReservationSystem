/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package horsmanagementclient;

import ejb.sessions.stateless.RoomRateSessionBeanRemote;
import ejb.sessions.stateless.RoomSessionBeanRemote;
import ejb.sessions.stateless.RoomTypeSessionBeanRemote;
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
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(roomTypeSessionBeanRemote, roomSessionBeanRemote, roomRateSessionBeanRemote); 
        mainApp.run(); 
        
    }
    
}
