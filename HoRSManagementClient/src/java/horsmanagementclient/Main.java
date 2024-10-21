/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package horsmanagementclient;

import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
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
        if(roomTypeSessionBeanRemote == null)
        {
            System.out.println("NULL NULL 1");
        }
        
        if(roomSessionBeanRemote == null)
        {
            System.out.println("NULL NULL 2");
        }
        
        if(roomRateSessionBeanRemote == null)
        {
            System.out.println("NULL NULL 3");
        }
        
        MainApp mainApp = new MainApp(roomTypeSessionBeanRemote, roomSessionBeanRemote, roomRateSessionBeanRemote); 
        mainApp.run(); 
        
    }
    
}
