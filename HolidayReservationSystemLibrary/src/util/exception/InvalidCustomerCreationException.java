/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.exception;

/**
 *
 * @author leunghanxi
 */
public class InvalidCustomerCreationException extends Exception {
    
    public InvalidCustomerCreationException()
    {
    }
    
    public InvalidCustomerCreationException(String msg)
    {
        super(msg);
    }
}