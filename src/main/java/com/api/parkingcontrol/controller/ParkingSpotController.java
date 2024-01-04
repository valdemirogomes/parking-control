package com.api.parkingcontrol.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.parkingcontrol.Service.ParkingSpotService;
import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.model.ParkingSpotModel;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {
	
	final ParkingSpotService parkingSpotService;

    ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }
    
    @PostMapping
    public ResponseEntity<Object> saveParkSpot (@RequestBody @Valid ParkingSpotDto parkingSpotDto){
        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        }
        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
        }
    	var parkingSpotModel = new ParkingSpotModel();
    	BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
    	parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
    	return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }
    
    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkSpots(){
    	return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<Object> getOneParkSpot(@PathVariable(value="id")UUID id){
    	Optional<ParkingSpotModel> response = parkingSpotService.findById(id);
    	if(!response.isPresent()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
    	}
    	return ResponseEntity.status(HttpStatus.OK).body(response.get());
    }

}
