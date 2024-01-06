package com.api.parkingcontrol.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.parkingcontrol.dto.ParkingSpotDto;
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
	public ResponseEntity<Object> save(ParkingSpotDto parkingSpotDto) {
        if(parkingSpotRepository.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        if(parkingSpotRepository.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        }
        if(parkingSpotRepository.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
        }
        
    	var parkingSpotModel = new ParkingSpotModel();
    	BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
    	parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		parkingSpotRepository.save(parkingSpotModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotModel);
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

	public ResponseEntity<Object> findById(UUID id) {
    	Optional<ParkingSpotModel> response = parkingSpotRepository.findById(id);
    	if(!response.isPresent()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
    	}
		     	return ResponseEntity.status(HttpStatus.OK).body(response.get());

	}

	public ResponseEntity<Object> delete(UUID id) {
    	Optional<ParkingSpotModel> response = parkingSpotRepository.findById(id);
    	if(!response.isPresent()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
    	}
		parkingSpotRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
	}
	
	public ResponseEntity<Object> update (UUID id, ParkingSpotDto parkingSpotDto){
    	Optional<ParkingSpotModel> response = parkingSpotRepository.findById(id);
    	if(!response.isPresent()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
    	}
      	var parkingSpotModel = new ParkingSpotModel();
    	BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setId(response.get().getId());
        parkingSpotModel.setRegistrationDate(response.get().getRegistrationDate());
    	return  ResponseEntity.status(HttpStatus.OK).body(parkingSpotRepository.save(parkingSpotModel));
	}

}
