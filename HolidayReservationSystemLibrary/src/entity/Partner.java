/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.OneToMany;


/**
 *
 * @author leunghanxi
 */
@Entity
public class Partner implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long PartnerId;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @OneToMany
    private List<Reservation> reservations;
    
    
    public Partner() {
       reservations = new ArrayList<Reservation>();
    }

    public Partner(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public Long getPartnerId() {
        return PartnerId;
    }

    public void setPartnerId(Long PartnerId) {
        this.PartnerId = PartnerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (PartnerId != null ? PartnerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the PartnerId fields are not set
        if (!(object instanceof Partner)) {
            return false;
        }
        Partner other = (Partner) object;
        if ((this.PartnerId == null && other.PartnerId != null) || (this.PartnerId != null && !this.PartnerId.equals(other.PartnerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Partner[ id=" + PartnerId + " ]";
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

    
}