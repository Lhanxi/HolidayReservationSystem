/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author jeremy
 */
@Stateless
public class AllocateRoomSessionBean implements AllocateRoomSessionBeanRemote, AllocateRoomSessionBeanLocal {

    public AllocateRoomSessionBean() {
    }

    @Schedule(hour = "2", minute = "0", second = "0")
    public void automaticAllocation() {
        
    } 
   
}
