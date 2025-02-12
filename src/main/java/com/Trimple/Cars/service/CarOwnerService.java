package com.Trimple.Cars.service;
import com.Trimple.Cars.dto.CarDTO;
import com.Trimple.Cars.dto.CarOwnerDTO;
import com.Trimple.Cars.dto.LeaseDTO;

public interface CarOwnerService {
    // Registers a new car owner and returns the owner's DTO
    CarOwnerDTO registerOwner(CarOwnerDTO carOwnerDTO);

    // Registers a car for the specified owner and returns the car's DTO
    CarDTO registerCarForOwner(CarDTO carDTO, Long ownerId);

    // Retrieves the car owner information based on the owner ID
    CarOwnerDTO getOwner(Long ownerId);

    // Retrieves the lease history for a specific car based on its ID
    LeaseDTO getLeaseHistory(Long carId);
}
