/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomRate;
import entity.RoomType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.RateTypeEnum;

/**
 *
 * @author jeremy
 */
@Stateless
public class RoomRateSessionBean implements RoomRateSessionBeanRemote, RoomRateSessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    
    public RoomRateSessionBean() {
    }
    
    @Override
    public Long createNewRoomRate(RoomRate roomRate) {
        em.persist(roomRate); 
        em.flush();
        return roomRate.getRoomRateId();
    }
    
    @Override 
    public List<RoomRate> getAllRoomRates() {
        Query query = em.createQuery("SELECT r FROM RoomRate r");
        return query.getResultList();
    }
    
    @Override
    public List<RoomRate> getEnabledRoomRates() {
        List<RoomRate> enabled = new ArrayList<RoomRate>();
        List<RoomRate> roomRates = getAllRoomRates(); 
        for (RoomRate r : roomRates) {
            if (!r.getIsDisabled()) {
                enabled.add(r);
            }
        }
        return enabled;
    }
    
    @Override
    public void updateRoomRate(Long roomRateId, String name, RoomType roomType, RateTypeEnum rateTypeEnum, BigDecimal roomRateAmount, Date startDate, Date endDate){
        RoomRate roomRate = em.find(RoomRate.class, roomRateId); 
        roomRate.setName(name);
        roomRate.setRoomType(roomType);
        roomRate.setRateTypeEnum(rateTypeEnum);
        roomRate.setRoomRateAmount(roomRateAmount);
        roomRate.setStartDate(startDate);
        roomRate.setEndDate(endDate);
    }
    
    @Override
    public Long getRoomRateForRoomType(RoomType roomType, RateTypeEnum rateTypeEnum) {
        Query query = em.createQuery("SELECT r FROM RoomRate r WHERE r.roomType =:roomType AND r.rateTypeEnum =:rateTypeEnum");
        query.setParameter("roomType", roomType); 
        query.setParameter("rateTypeEnum", rateTypeEnum);
        RoomRate roomRate = (RoomRate) query.getResultList().get(0); 
        return roomRate.getRoomRateId();
    }
    
    @Override
    public String deleteRoomRate(Long roomRateId) {
        RoomRate roomRate = em.find(RoomRate.class, roomRateId); 
        
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.roomRate =:roomRate");
        query.setParameter("roomRate", roomRate); 
        
        if (query.getResultList().size() == 0) { //there are no reservations
            em.remove(roomRate);
            return "Successfully deleted room rate";
        }
        roomRate.setIsDisabled(true);
        return "Unable to delete room rate as it is being used, room rate set to disabled";
        
    }
    
}
