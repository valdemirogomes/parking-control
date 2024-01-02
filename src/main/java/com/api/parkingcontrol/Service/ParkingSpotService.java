package com.api.parkingcontrol.Service;

import org.springframework.stereotype.Service;
import com.api.parkingcontrol.repository.ParkingSpotRepository;

@Service
public class ParkingSpotService {
	
	final ParkingSpotRepository parkingSpotService;

    ParkingSpotService(ParkingSpotRepository parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }


}
