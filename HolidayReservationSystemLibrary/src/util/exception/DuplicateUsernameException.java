/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.exception;

/**
 *
 * @author jeremy
 */
public class DuplicateUsernameException extends Exception {

    public DuplicateUsernameException() {
    }
    
    public DuplicateUsernameException(String message) {
        super(message);
    }
    
}