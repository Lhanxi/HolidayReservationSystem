/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.sessions.stateless;

import entity.RoomRate;
import entity.RoomType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    public List<RoomRate> getAllRoomRates() {
        Query query = em.createQuery("SELECT r FROM RoomRate");
        return query.getResultList();
    }
    
}
