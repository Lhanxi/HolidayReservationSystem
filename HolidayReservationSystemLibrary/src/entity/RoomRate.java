/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.RateTypeEnum;

/**
 *
 * @author jeremy
 */
@Entity
public class RoomRate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomRateId;
    @Column(nullable=false, unique = true, length = 64)
    @NotNull
    @Size(max=64)
    private String name; 
    @ManyToOne
    @JoinColumn(nullable=false)
    private RoomType roomType;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    @NotNull
    private RateTypeEnum rateTypeEnum;
    @Column(nullable=false)
    @NotNull
    private BigDecimal roomRateAmount; 
    @Temporal(TemporalType.DATE)
    @Column(nullable=false)
    @NotNull
    private Date startDate; //check if we want to use this 
    @Temporal(TemporalType.DATE)
    @Column(nullable=false)
    @NotNull
    private Date endDate;
    @Column(nullable=false)
    @NotNull
    private Boolean isDisabled;

    public RoomRate() {}
    
    public RoomRate(String name, RoomType roomType, RateTypeEnum rateTypeEnum, BigDecimal roomRateAmount, Date startDate, Date endDate) {
        this.name = name;
        this.roomType = roomType;
        this.rateTypeEnum = rateTypeEnum;
        this.roomRateAmount = roomRateAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDisabled = false;
    }
    

    public Long getRoomRateId() {
        return roomRateId;
    }

    public void setRoomRateId(Long roomRateId) {
        this.roomRateId = roomRateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RateTypeEnum getRateTypeEnum() {
        return rateTypeEnum;
    }

    public void setRateTypeEnum(RateTypeEnum rateTypeEnum) {
        this.rateTypeEnum = rateTypeEnum;
    }

    public BigDecimal getRoomRateAmount() {
        return roomRateAmount;
    }

    public void setRoomRateAmount(BigDecimal roomRateAmount) {
        this.roomRateAmount = roomRateAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomRateId != null ? roomRateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomRateId fields are not set
        if (!(object instanceof RoomRate)) {
            return false;
        }
        RoomRate other = (RoomRate) object;
        if ((this.roomRateId == null && other.roomRateId != null) || (this.roomRateId != null && !this.roomRateId.equals(other.roomRateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomRate[ id=" + roomRateId + " ]";
    }
    
}
