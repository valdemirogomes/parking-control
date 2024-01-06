package com.api.parkingcontrol.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    	return parkingSpotService.save(parkingSpotDto);
    }
    
    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkSpots(){
    	return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<Object> getOneParkSpot(@PathVariable(value="id")UUID id){
    	return parkingSpotService.findById(id);
   
    }
    
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Object> deletOneParkSpot(@PathVariable(value="id")UUID id){
    	return parkingSpotService.delete(id);
    }
    
    @PutMapping(value="/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value="id") UUID id, 
    												@RequestBody 
    												@Valid ParkingSpotDto parkingSpotDto){
        return parkingSpotService.update(id, parkingSpotDto);
    	
    }
}
