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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author jeremy
 */
@Entity
public class RoomReservation implements Serializable {


    //used to create a reservation for a particular room
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomReservationId;
    @OneToOne 
    private Room room;
    @ManyToOne
    private RoomType roomType;
    @OneToOne(mappedBy="roomReservation")
    private AllocationExceptionReport allocationExceptionReport;

    public RoomReservation() {
    }

    public RoomReservation(Room room) {
        this.room = room;
    }

    public Long getRoomReservationId() {
        return roomReservationId;
    }

    public void setRoomReservationId(Long roomReservationId) {
        this.roomReservationId = roomReservationId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    
    /**
     * @return the roomType
     */
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * @param roomType the roomType to set
     */
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public AllocationExceptionReport getAllocationExceptionReport() {
        return allocationExceptionReport;
    }

    public void setAllocationExceptionReport(AllocationExceptionReport allocationExceptionReport) {
        this.allocationExceptionReport = allocationExceptionReport;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomReservationId != null ? roomReservationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomReservationId fields are not set
        if (!(object instanceof RoomReservation)) {
            return false;
        }
        RoomReservation other = (RoomReservation) object;
        if ((this.roomReservationId == null && other.roomReservationId != null) || (this.roomReservationId != null && !this.roomReservationId.equals(other.roomReservationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomReservation[ id=" + roomReservationId + " ]";
    }
    
}