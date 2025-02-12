package com.Trimple.Cars.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Trimple.Cars.dto.*;
import com.Trimple.Cars.service.*;


@RestController
@RequestMapping("/api/carowners")
public class CarOwnerController {

    // Logger for debugging and logging purposes
    private static final Logger logger = LoggerFactory.getLogger(CarOwnerController.class);

    // Autowiring the service class to handle the business logic
    @Autowired
    private   CarOwnerService carOwnerService;


//    public CarOwnerController(CarOwnerService carOwnerService) {
//        this.carOwnerService = carOwnerService;
//    }

    // Registers a new Car Owner
    @PostMapping("/registerOwner")
    public ResponseEntity<CarOwnerDTO> registerOwner(@Valid @RequestBody CarOwnerDTO carOwnerDTO) {
        try {
            logger.info("Received request to register car owner: {}", carOwnerDTO.getName());
            CarOwnerDTO registeredOwner = carOwnerService.registerOwner(carOwnerDTO);
            logger.info("Successfully registered car owner with ID: {}", registeredOwner.getId());
            return ResponseEntity.ok(registeredOwner);
        } catch (Exception e) {
            logger.error("Error while registering car owner: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Registers a new Car for an Owner
    @PostMapping("/registerCar")
    public ResponseEntity<CarDTO> registerCar(@Valid @RequestBody CarDTO carDTO, @RequestParam Long ownerId) {
        try {
            logger.info("Received request to register car for owner ID: {}", ownerId);
            CarDTO registeredCar = carOwnerService.registerCarForOwner(carDTO, ownerId);
            logger.info("Successfully registered car with ID: {}", registeredCar.getId());
            return ResponseEntity.ok(registeredCar);
        } catch (Exception e) {
            logger.error("Error while registering car for owner with ID {}: {}", ownerId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieves the details of a car owner by ID
    @GetMapping("/getOwner/{ownerId}")
    public ResponseEntity<CarOwnerDTO> getOwner(@PathVariable Long ownerId) {
        try {
            logger.info("Received request to get car owner with ID: {}", ownerId);
            CarOwnerDTO carOwner = carOwnerService.getOwner(ownerId);
            if (carOwner != null) {
                logger.info("Successfully retrieved car owner with ID: {}", ownerId);
                return ResponseEntity.ok(carOwner);
            } else {
                logger.warn("Car owner with ID: {} not found", ownerId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while retrieving car owner with ID {}: {}", ownerId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieves the lease history for a given car
    @GetMapping("/getleasehistory/{carId}")
    public ResponseEntity<LeaseDTO> getLeaseHistory(@PathVariable Long carId) {
        try {
            logger.info("Received request to get lease history for car with ID: {}", carId);
            LeaseDTO leaseDTO = carOwnerService.getLeaseHistory(carId);
            if (leaseDTO != null) {
                logger.info("Successfully retrieved lease history for car with ID: {}", carId);
                return ResponseEntity.ok(leaseDTO);
            } else {
                logger.warn("Lease history for car with ID: {} not found", carId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while retrieving lease history for car with ID {}: {}", carId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
