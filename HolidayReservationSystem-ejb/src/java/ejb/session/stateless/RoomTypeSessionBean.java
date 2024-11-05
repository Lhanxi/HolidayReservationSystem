/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomType;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jeremy
 */
@Stateless
public class RoomTypeSessionBean implements RoomTypeSessionBeanRemote, RoomTypeSessionBeanLocal {

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;
    
    public RoomTypeSessionBean() {
    }
    
    @Override
    public Long createNewRoomType(RoomType newRoomType, Integer ranking) {
    try {
        List<RoomType> roomTypes = getRoomTypeList();
        
        if (roomTypes.size() > 1) {
            roomTypes.sort((r1, r2) -> Integer.compare(r1.getRanking(), r2.getRanking()));
            updateRoomTypeRankings(roomTypes, ranking);
        }

        newRoomType.setRanking(ranking);
        
        em.persist(newRoomType);
        em.flush();
        
        System.out.println("RoomType created with ID: " + newRoomType.getRoomTypeId());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error persisting RoomType: " + ex.getMessage());
        }
        return newRoomType.getRoomTypeId();
    }
    
    private void updateRoomTypeRankings(List<RoomType> roomTypes, Integer ranking) {
        //increases all the ranking of all room types after the newRanking
        //if the rnaking is at the end and more than the room size, does nothing
        for (int i = roomTypes.size() - 1; i >= ranking; i--) {
            RoomType roomType = roomTypes.get(i); 
            roomType.setRanking(i + 1);
        }
    }

    @Override
    public List<RoomType> getRoomTypeList() {
        Query query = em.createQuery("SELECT r FROM RoomType r"); 
        return query.getResultList();
    }
    
    @Override
    public List<RoomType> getEnabledRoomTypeList() {
        List<RoomType> roomTypes = getRoomTypeList(); 
        Iterator<RoomType> iterator = roomTypes.iterator();

        while (iterator.hasNext()) {
            RoomType roomType = iterator.next();
            if (roomType.isIsDisabled()) {
                iterator.remove();  // Safely removes the element
            }
        }

    return roomTypes;
    }
    
    @Override
    public void updateRoomTypeDetails(Long roomTypeId, String roomTypeName, String newDescription, String newSize, String newBedCapacity, String newAmenities, Integer newRanking) {
        RoomType roomType = em.find(RoomType.class, roomTypeId); 
        roomType.setName(roomTypeName);
        roomType.setDescription(newDescription);
        roomType.setSize(newSize);
        roomType.setBedCapacity(newBedCapacity);
        roomType.setAmenities(newAmenities);

        // Check if ranking needs to be updated
        //even if the roomType is disabled, just maintain the rankings
        if (!newRanking.equals(roomType.getRanking())) {
            updateRoomRanking(roomType, newRanking);
        }
    }

    private void updateRoomRanking(RoomType roomType, Integer newRanking) {
        List<RoomType> roomTypes = getRoomTypeList();
        int currentRanking = roomType.getRanking();

        // Temporarily set roomType's ranking to a value outside the normal range
        int tempRanking = roomTypes.size() + 1;
        roomType.setRanking(tempRanking);
        em.flush();  // Persist the temporary ranking change to prevent SQL conflicts

        // Sort room types by current ranking to ensure sequential updates
        roomTypes.sort(Comparator.comparingInt(RoomType::getRanking));

        // Shift rankings as needed to accommodate the new ranking
        if (newRanking < currentRanking) {
            for (RoomType r : roomTypes) {
                if (r.getRanking() >= newRanking && r.getRanking() < currentRanking) {
                    r.setRanking(r.getRanking() + 1);
                }
            }
        } else {
            for (RoomType r : roomTypes) {
                if (r.getRanking() <= newRanking && r.getRanking() > currentRanking) {
                    r.setRanking(r.getRanking() - 1);
                }
            }
        }

        // Set the updated ranking to roomType
        roomType.setRanking(newRanking);
    }
    

    @Override
    public String deleteRoomType(Long roomTypeId) {
        RoomType roomType = em.find(RoomType.class, roomTypeId);

        // Fetch the list of room types, sorted by ranking
        List<RoomType> roomTypes = getRoomTypeList();
        roomTypes.sort(Comparator.comparingInt(RoomType::getRanking));
        int index = roomTypes.indexOf(roomType); 
        roomTypes.remove(roomType);

        if (checkRoomTypeForRooms(roomType) && checkRoomTypeForRoomRates(roomType)) {
            em.remove(roomType);
            em.flush();
            updateRoomTypeRankingForDelete(roomTypes, index);
            return "Room Type successfully deleted";
        } else {
            roomType.setIsDisabled(true);
            return "Room Type deletion failed, Room Type is being used. Room Type has been disabled.";
        }
    }

    private void updateRoomTypeRankingForDelete(List<RoomType> roomTypes, Integer index) {
        for (int i = index; i < roomTypes.size(); i++) {
            RoomType roomType = roomTypes.get(i); 
            roomType.setRanking(roomType.getRanking() - 1);
        }
    }

    
    private boolean checkRoomTypeForRooms(RoomType roomType) {
        Query query = em.createQuery("SELECT r FROM Room r WHERE r.roomType = :roomType"); 
        query.setParameter("roomType", roomType);
        return query.getResultList().isEmpty();
    } 
    
    //for the case where there might not be any rooms that are currently using a room type
    private boolean checkRoomTypeForRoomRates(RoomType roomType) {
        Query query = em.createQuery("SELECT r FROM RoomRate r WHERE r.roomType = :roomType"); 
        query.setParameter("roomType", roomType);
        return query.getResultList().isEmpty();
    }
    
    @Override
    public RoomType getRoomTypeByName(String roomTypeName) {
        Query query = em.createQuery("SELECT r FROM RoomType r WHERE r.name =:roomTypeName");
        query.setParameter("roomTypeName", roomTypeName);
        return (RoomType) query.getSingleResult();
    }
   

}