/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomRate;
import entity.RoomType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.NoResultException;
import util.enumeration.RateTypeEnum;
import util.enumeration.RoomRateNotFoundException;

/**
 *
 * @author jeremy
 */
@Remote
public interface RoomRateSessionBeanRemote {
    public Long createNewRoomRate(RoomRate roomRate);

    public List<RoomRate> getAllRoomRates();
    public void updateRoomRate(Long roomRateId, String name, RoomType roomType, RateTypeEnum rateTypeEnum, BigDecimal roomRateAmount, Date startDate, Date endDate);
    public BigDecimal calculateRoomRateAmount(RoomType roomType, Date startDate, Date endDate, int noOfRooms);
    public Long getRoomRateForRoomType(RoomType roomType, RateTypeEnum rateTypeEnum);
    public String deleteRoomRate(Long roomRateId);
    public List<RoomRate> getEnabledRoomRates();
    public RoomRate getRoomRateByName(String roomRateName) throws RoomRateNotFoundException;
}
