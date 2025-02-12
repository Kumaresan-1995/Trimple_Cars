package com.Trimple.Cars.service;

import java.util.List;

import com.Trimple.Cars.dto.CarDTO;
import com.Trimple.Cars.dto.CarOwnerDTO;
import com.Trimple.Cars.dto.CustomerDTO;
import com.Trimple.Cars.dto.LeaseDTO;

public interface AdminService {
	// Car Owner Methods
    CarOwnerDTO registerCarOwner(CarOwnerDTO carOwnerDTO);
    CarOwnerDTO getCarOwner(Long ownerId);
    CarOwnerDTO updateCarOwner(CarOwnerDTO carOwnerDTO);

    // Car Methods
    List<CarDTO> getAllCars();
    CarDTO registerCarForOwner(CarDTO carDTO, Long ownerId);
    void deleteCar(Long carId);

    // Customer Methods
    CustomerDTO registerCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomer(Long customerId);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    // Lease Methods
    List<LeaseDTO> getAllLeases();
    LeaseDTO updateLease(LeaseDTO leaseDTO);
}
