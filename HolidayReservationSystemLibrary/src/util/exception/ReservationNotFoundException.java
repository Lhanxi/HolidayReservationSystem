/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.exception;

/**
 *
 * @author leunghanxi
 */
public class ReservationNotFoundException extends Exception {
    
    public ReservationNotFoundException()
    {
    }
    
    public ReservationNotFoundException(String msg)
    {
        super(msg);
    }
}