/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package holidayreservationsystemwebclient;

import ws.client.HolidayReservationSystemWebService_Service;

/**
 *
 * @author leunghanxi
 */
public class HolidayReservationSystemWebClient {

    public static void main(String[] args)
    { 
        HolidayReservationSystemWebService_Service service = new HolidayReservationSystemWebService_Service(); 
        MainApp mainApp = new MainApp(service);
        mainApp.runApp();
    }    
}
