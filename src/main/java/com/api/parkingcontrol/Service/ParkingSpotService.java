package com.api.parkingcontrol.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.api.parkingcontrol.model.ParkingSpotModel;
import com.api.parkingcontrol.repository.ParkingSpotRepository;
import jakarta.transaction.Transactional;

@Service
public class ParkingSpotService {
	
	final ParkingSpotRepository parkingSpotRepository;

	public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
		
		this.parkingSpotRepository = parkingSpotRepository;
	}
	
	@Transactional
	public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
		return parkingSpotRepository.save(parkingSpotModel);
	}
	public List<ParkingSpotModel>findAll(){
		return parkingSpotRepository.findAll();
	}
	
	 public boolean existsByLicensePlateCar(String licensePlateCar) {
	        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
	    }

	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
	        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
	    }

	public boolean existsByApartmentAndBlock(String apartment, String block) {
	        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
	    }

	public Optional<ParkingSpotModel> findById(UUID id) {
		return parkingSpotRepository.findById(id);
	}

}
