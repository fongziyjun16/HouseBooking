package com.cyj.hb.service;

import com.cyj.hb.model.Stay;
import com.cyj.hb.repository.LocationRepository;
import com.cyj.hb.repository.StayRepository;
import com.cyj.hb.repository.StayReservationDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SearchService {

    private StayRepository stayRepository;
    private StayReservationDateRepository stayReservationDateRepository;
    private LocationRepository locationRepository;

    @Autowired
    public SearchService(StayRepository stayRepository, StayReservationDateRepository stayReservationDateRepository, LocationRepository locationRepository) {
        this.stayRepository = stayRepository;
        this.stayReservationDateRepository = stayReservationDateRepository;
        this.locationRepository = locationRepository;
    }

    public List<Stay> search(int guestNumber, LocalDate checkinDate, LocalDate checkoutDate, double lat, double lon, String distance) {
        List<Long> stayIDs = locationRepository.searchByDistance(lat, lon, distance);
        if (stayIDs == null || stayIDs.isEmpty()) {
            return new ArrayList<>();
        }

        Set<Long> reservedStayIds =
                stayReservationDateRepository.findByIdInAndDateBetween(stayIDs, checkinDate, checkoutDate.minusDays(1));

        List<Long> filteredStayIds = new ArrayList<>();
        for (Long stayID : stayIDs) {
            if (!reservedStayIds.contains(stayID)) {
                filteredStayIds.add(stayID);
            }
        }
        return stayRepository.findByIdInAndGuestNumberGreaterThanEqual(filteredStayIds, guestNumber);
    }

}
