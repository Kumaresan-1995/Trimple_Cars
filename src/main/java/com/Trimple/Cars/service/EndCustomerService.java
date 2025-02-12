package com.Trimple.Cars.service;

import java.util.List;

import com.Trimple.Cars.dto.CarDTO;
import com.Trimple.Cars.dto.CustomerDTO;
import com.Trimple.Cars.dto.LeaseDTO;

 

public interface EndCustomerService {
    // Retrieves a list of available cars for leasing
    List<CarDTO> getAvailableCars();

    // Starts a lease for the given customer and car, and returns lease details
    LeaseDTO startLease(LeaseDTO leaseDTO);

    // Ends the lease for a customer and car, and updates lease status
    LeaseDTO endLease(LeaseDTO leaseDTO);

    // Retrieves the lease history of a customer based on their ID
    List<LeaseDTO> getLeaseHistory(Long customerId);

    // Registers a new customer and returns the customer's DTO
    CustomerDTO customerRegister(CustomerDTO customerDTO);
}

