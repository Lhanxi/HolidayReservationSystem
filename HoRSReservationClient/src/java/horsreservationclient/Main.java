/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package horsreservationclient;

import javax.ejb.EJB;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;

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
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(roomRateSessionBeanRemote, roomSessionBeanRemote, roomTypeSessionBeanRemote);
        mainApp.runApp();
    }
}