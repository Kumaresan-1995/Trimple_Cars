package com.Trimple.Cars.controller;

 
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Trimple.Cars.service.AdminService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import com.Trimple.Cars.dto.*; 
 
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    // Registers a new car owner in the system
    @PostMapping("/registerOwner")
    public ResponseEntity<CarOwnerDTO> registerOwner(@Valid @RequestBody CarOwnerDTO carOwnerDTO) {
        try {
            logger.debug("Registering new car owner: {}", carOwnerDTO);
            CarOwnerDTO registeredOwner = adminService.registerCarOwner(carOwnerDTO);
            return new ResponseEntity<>(registeredOwner, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering car owner", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieves a car owner by their unique ID
    @GetMapping("/getOwner/{ownerId}")
    public ResponseEntity<CarOwnerDTO> getOwner(@PathVariable Long ownerId) {
        try {
            logger.debug("Fetching car owner with ID: {}", ownerId);
            CarOwnerDTO carOwner = adminService.getCarOwner(ownerId);
            if (carOwner == null) {
                logger.warn("Car owner with ID {} not found", ownerId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(carOwner);
        } catch (Exception e) {
            logger.error("Error fetching car owner with ID: {}", ownerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Updates an existing car owner's details
    @PostMapping("/updateOwner")
    public ResponseEntity<CarOwnerDTO> updateOwner(@Validated @RequestBody CarOwnerDTO carOwnerDTO) {
        try {
            logger.debug("Updating car owner details: {}", carOwnerDTO);
            CarOwnerDTO updatedOwner = adminService.updateCarOwner(carOwnerDTO);
            return ResponseEntity.ok(updatedOwner);
        } catch (Exception e) {
            logger.error("Error updating car owner", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieves all cars managed by the admin
    @GetMapping("/manageCars")
    public ResponseEntity<List<CarDTO>> manageCars() {
        try {
            logger.debug("Fetching all cars");
            List<CarDTO> cars = adminService.getAllCars();
            return ResponseEntity.ok(cars);
        } catch (Exception e) {
            logger.error("Error fetching cars", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Registers a new car for a specific owner
    @PostMapping("/registerCar")
    public ResponseEntity<CarDTO> registerCar(@Valid @RequestBody CarDTO carDTO, @RequestParam Long ownerId) {
        try {
            logger.debug("Registering new car for owner ID: {}", ownerId);
            CarDTO registeredCar = adminService.registerCarForOwner(carDTO, ownerId);
            return new ResponseEntity<>(registeredCar, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering car for owner ID: {}", ownerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Deletes a car based on the car's unique ID
    @PostMapping("/deleteCar/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
        try {
            logger.debug("Deleting car with ID: {}", carId);
            adminService.deleteCar(carId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting car with ID: {}", carId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Registers a new customer in the system
    @PostMapping("/registerCustomer")
    public ResponseEntity<CustomerDTO> registerCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        try {
            logger.debug("Registering new customer: {}", customerDTO);
            CustomerDTO registeredCustomer = adminService.registerCustomer(customerDTO);
            return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering customer", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieves a customer by their unique ID
    @GetMapping("/getCustomer/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId) {
        try {
            logger.debug("Fetching customer with ID: {}", customerId);
            CustomerDTO customer = adminService.getCustomer(customerId);
            if (customer == null) {
                logger.warn("Customer with ID {} not found", customerId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            logger.error("Error fetching customer with ID: {}", customerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Updates an existing customer's details
    @PostMapping("/updateCustomer")
    public ResponseEntity<CustomerDTO> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        try {
            logger.debug("Updating customer details: {}", customerDTO);
            CustomerDTO updatedCustomer = adminService.updateCustomer(customerDTO);
            return ResponseEntity.ok(updatedCustomer);
        } catch (Exception e) {
            logger.error("Error updating customer", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieves all leases managed by the admin
    @GetMapping("/manageLeases")
    public ResponseEntity<List<LeaseDTO>> manageLeases() {
        try {
            logger.debug("Fetching all leases");
            List<LeaseDTO> leases = adminService.getAllLeases();
            return ResponseEntity.ok(leases);
        } catch (Exception e) {
            logger.error("Error fetching leases", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Updates an existing lease's details
    @PostMapping("/updateLease")
    public ResponseEntity<LeaseDTO> updateLease(@Valid @RequestBody LeaseDTO leaseDTO) {
        try {
            logger.debug("Updating lease details: {}", leaseDTO);
            LeaseDTO updatedLease = adminService.updateLease(leaseDTO);
            return ResponseEntity.ok(updatedLease);
        } catch (Exception e) {
            logger.error("Error updating lease", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
