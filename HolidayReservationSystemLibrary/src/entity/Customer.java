/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author leunghanxi
 */
@Entity
public class Customer extends Visitor implements Serializable {
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;

    public Customer() {
        super();
    }
    
    public Customer(String username, String password, String name, String passportNumber) {
        super(name, passportNumber);
        this.username = username;
        this.password = password;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getGuestId() != null ? getGuestId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the guestId fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.getGuestId() == null && other.getGuestId() != null) || (this.getGuestId() != null && !this.guestId.equals(other.guestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Guest[ id=" + getGuestId() + " ]";
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
        /**
     * @return the reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * @param reservations the reservations to set
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Long getGuestId() {
        return super.getGuestId();
    }

    public void setGuestId(Long guestId) {
        super.guestId = guestId;
    }
}