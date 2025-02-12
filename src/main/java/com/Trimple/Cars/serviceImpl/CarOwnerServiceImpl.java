package com.Trimple.Cars.serviceImpl;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Trimple.Cars.dto.CarDTO;
import com.Trimple.Cars.dto.CarOwnerDTO;
import com.Trimple.Cars.dto.LeaseDTO;
import com.Trimple.Cars.entity.Car;
import com.Trimple.Cars.entity.CarOwner;
import com.Trimple.Cars.entity.Lease;
import com.Trimple.Cars.exception.CarOwnerNotFoundException;
import com.Trimple.Cars.exception.LeaseNotFoundException;
import com.Trimple.Cars.repository.CarOwnerRepository;
import com.Trimple.Cars.repository.CarRepository;
import com.Trimple.Cars.repository.LeaseRepository;
import com.Trimple.Cars.service.CarOwnerService; 

@Service
public class CarOwnerServiceImpl implements CarOwnerService {

    private static final Logger logger = LoggerFactory.getLogger(CarOwnerServiceImpl.class);

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarOwnerRepository carOwnerRepository;
    @Autowired
    private LeaseRepository leaseRepository;

    // Registers a new car owner in the system
    @Override
    public CarOwnerDTO registerOwner(CarOwnerDTO carOwnerDTO) {
        try {
            logger.debug("Registering new car owner: {}", carOwnerDTO);
            CarOwner carOwner = new CarOwner();
            carOwner.setName(carOwnerDTO.getName());
            carOwner.setEmail(carOwnerDTO.getEmail());
            carOwner.setPhone(carOwnerDTO.getPhone());

            carOwnerRepository.save(carOwner);
            carOwnerDTO.setId(carOwner.getId());
            return carOwnerDTO;
        } catch (Exception e) {
            logger.error("Error registering car owner: {}", carOwnerDTO, e);
            throw new RuntimeException("Error registering car owner", e);
        }
    }

    // Registers a new car for a specific owner
    @Override
    public CarDTO registerCarForOwner(CarDTO carDTO, Long ownerId) {
        try {
            logger.debug("Registering car for owner with ID: {}", ownerId);
            CarOwner carOwner = carOwnerRepository.findById(ownerId)
                    .orElseThrow(() -> new CarOwnerNotFoundException("Car Owner not found"));

            Car car = new Car();
            car.setModel(carDTO.getModel());
            car.setMake(carDTO.getMake());
            car.setRegistrationNumber(carDTO.getRegistrationNumber());
            car.setStatus("Ideal");
            car.setCarOwner(carOwner);

            car = carRepository.save(car);
            carDTO.setId(car.getId());
            return carDTO;
        } catch (Exception e) {
            logger.error("Error registering car for owner with ID: {}", ownerId, e);
            throw new RuntimeException("Error registering car for owner", e);
        }
    }

    // Retrieves lease history for a car by carId
    @Override
    public LeaseDTO getLeaseHistory(Long carId) {
        try {
            logger.debug("Fetching lease history for car ID: {}", carId);
            Lease lease = leaseRepository.findByCarId(carId)
                    .orElseThrow(() -> new LeaseNotFoundException("Lease not found for car ID: " + carId));

            LeaseDTO leaseDTO = new LeaseDTO();
            leaseDTO.setId(lease.getId());
            leaseDTO.setCarId(lease.getCarId());
            leaseDTO.setCustomerId(lease.getCustomerId());
            leaseDTO.setLeaseStartDate(lease.getLeaseStartDate());
            leaseDTO.setLeaseEndDate(lease.getLeaseEndDate());
            leaseDTO.setStatus(lease.getStatus());

            return leaseDTO;
        } catch (Exception e) {
            logger.error("Error fetching lease history for car ID: {}", carId, e);
            throw new RuntimeException("Error fetching lease history", e);
        }
    }

    // Retrieves car owner details by ownerId
    @Override
    public CarOwnerDTO getOwner(Long ownerId) {
        try {
            logger.debug("Fetching car owner with ID: {}", ownerId);
            CarOwner carOwner = carOwnerRepository.findById(ownerId)
                    .orElseThrow(() -> new CarOwnerNotFoundException("Car Owner not found"));

            CarOwnerDTO carOwnerDTO = new CarOwnerDTO();
            carOwnerDTO.setId(carOwner.getId());
            carOwnerDTO.setName(carOwner.getName());
            carOwnerDTO.setEmail(carOwner.getEmail());
            carOwnerDTO.setPhone(carOwner.getPhone());
            carOwnerDTO.setCarIds(carOwner.getCars().stream().map(Car::getId).collect(Collectors.toList()));

            return carOwnerDTO;
        } catch (Exception e) {
            logger.error("Error fetching car owner with ID: {}", ownerId, e);
            throw new RuntimeException("Error fetching car owner", e);
        }
    }
}
