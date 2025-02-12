package com.Trimple.Cars.controller;
import java.util.List;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Trimple.Cars.dto.*; 
import com.Trimple.Cars.service.*;
 

@RestController
@RequestMapping("/customer")
public class EndCustomerController {

    private static final Logger logger = LoggerFactory.getLogger(EndCustomerController.class);

    @Autowired
    EndCustomerService endCustomerService;

    // Registers a new customer in the system
    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> customerRegister(@Valid @RequestBody CustomerDTO customerDTO) {
        try {
            logger.debug("Registering customer: {}", customerDTO);
            CustomerDTO registeredCustomer = endCustomerService.customerRegister(customerDTO);
            return ResponseEntity.ok(registeredCustomer);
        } catch (Exception e) {
            logger.error("Error registering customer", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieves a list of available cars for the customer
    @GetMapping("/availableCars")
    public ResponseEntity<List<CarDTO>> getAvailableCars() {
        try {
            logger.debug("Fetching available cars");
            List<CarDTO> availableCars = endCustomerService.getAvailableCars();
            return ResponseEntity.ok(availableCars);
        } catch (Exception e) {
            logger.error("Error fetching available cars", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Starts a lease for a customer
    @PostMapping("/startLease")
    public ResponseEntity<LeaseDTO> startLease(@Valid @RequestBody LeaseDTO leaseDTO) {
        try {
            logger.debug("Starting lease: {}", leaseDTO);
            LeaseDTO lease = endCustomerService.startLease(leaseDTO);
            return ResponseEntity.ok(lease);
        } catch (Exception e) {
            logger.error("Error starting lease", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Ends a lease for a customer
    @PostMapping("/endLease")
    public ResponseEntity<LeaseDTO> endLease(@Valid @RequestBody LeaseDTO leaseDTO) {
        try {
            logger.debug("Ending lease: {}", leaseDTO);
            LeaseDTO lease = endCustomerService.endLease(leaseDTO);
            return ResponseEntity.ok(lease);
        } catch (Exception e) {
            logger.error("Error ending lease", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieves the lease history for a customer by their ID
    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<LeaseDTO>> getLeaseHistory(@PathVariable Long customerId) {
        try {
            logger.debug("Fetching lease history for customer ID: {}", customerId);
            List<LeaseDTO> leaseHistory = endCustomerService.getLeaseHistory(customerId);
            return ResponseEntity.ok(leaseHistory);
        } catch (Exception e) {
            logger.error("Error fetching lease history for customer ID: {}", customerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
