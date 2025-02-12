package com.Trimple.Cars.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Trimple.Cars.dto.CarDTO;
import com.Trimple.Cars.dto.CarOwnerDTO;
import com.Trimple.Cars.dto.CustomerDTO;
import com.Trimple.Cars.dto.LeaseDTO;
import com.Trimple.Cars.entity.Car;
import com.Trimple.Cars.entity.CarOwner;
import com.Trimple.Cars.entity.Customer;
import com.Trimple.Cars.entity.Lease;
import com.Trimple.Cars.repository.CarOwnerRepository;
import com.Trimple.Cars.repository.CarRepository;
import com.Trimple.Cars.repository.EndCustomerRepository;
import com.Trimple.Cars.repository.LeaseRepository;
import com.Trimple.Cars.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private CarOwnerRepository carOwnerRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private EndCustomerRepository customerRepository;
    @Autowired
    private LeaseRepository leaseRepository;
    @Autowired
    private ObjectMapper objectMapper;

    // Registers a new car owner in the system
    @Override
    public CarOwnerDTO registerCarOwner(CarOwnerDTO carOwnerDTO) {
        try {
            logger.debug("Registering car owner: {}", carOwnerDTO);
            CarOwner carOwner = objectMapper.convertValue(carOwnerDTO, CarOwner.class);
            carOwner = carOwnerRepository.save(carOwner);
            return objectMapper.convertValue(carOwner, CarOwnerDTO.class);
        } catch (Exception e) {
            logger.error("Error registering car owner", e);
            throw new RuntimeException("Error registering car owner", e);
        }
    }

    // Retrieves a car owner by their ID
    @Override
    public CarOwnerDTO getCarOwner(Long ownerId) {
        try {
            logger.debug("Fetching car owner with ID: {}", ownerId);
            CarOwner carOwner = carOwnerRepository.findById(ownerId)
                    .orElseThrow(() -> new EntityNotFoundException("Car Owner not found"));
            return objectMapper.convertValue(carOwner, CarOwnerDTO.class);
        } catch (Exception e) {
            logger.error("Error fetching car owner with ID: {}", ownerId, e);
            throw new RuntimeException("Error fetching car owner", e);
        }
    }

    // Updates an existing car owner's details
    @Override
    public CarOwnerDTO updateCarOwner(CarOwnerDTO carOwnerDTO) {
        try {
            logger.debug("Updating car owner details: {}", carOwnerDTO);
            CarOwner carOwner = carOwnerRepository.findById(carOwnerDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Car Owner not found"));
            carOwner = objectMapper.convertValue(carOwnerDTO, CarOwner.class);
            carOwner = carOwnerRepository.save(carOwner);
            return objectMapper.convertValue(carOwner, CarOwnerDTO.class);
        } catch (Exception e) {
            logger.error("Error updating car owner", e);
            throw new RuntimeException("Error updating car owner", e);
        }
    }

    // Retrieves all cars managed by the admin
    @Override
    public List<CarDTO> getAllCars() {
        try {
            logger.debug("Fetching all cars");
            List<Car> cars = carRepository.findAll();
            return cars.stream()
                    .map(car -> objectMapper.convertValue(car, CarDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all cars", e);
            throw new RuntimeException("Error fetching all cars", e);
        }
    }

    // Registers a new car for a specific owner
    @Override
    public CarDTO registerCarForOwner(CarDTO carDTO, Long ownerId) {
        try {
            logger.debug("Registering car for owner ID: {}", ownerId);
            CarOwner carOwner = carOwnerRepository.findById(ownerId)
                    .orElseThrow(() -> new EntityNotFoundException("Car Owner not found"));
            Car car = objectMapper.convertValue(carDTO, Car.class);
            car.setCarOwner(carOwner);
            car = carRepository.save(car);
            return objectMapper.convertValue(car, CarDTO.class);
        } catch (Exception e) {
            logger.error("Error registering car for owner ID: {}", ownerId, e);
            throw new RuntimeException("Error registering car for owner", e);
        }
    }

    // Deletes a car based on its ID
    @Override
    public void deleteCar(Long carId) {
        try {
            logger.debug("Deleting car with ID: {}", carId);
            Car car = carRepository.findById(carId)
                    .orElseThrow(() -> new EntityNotFoundException("Car not found"));
            carRepository.delete(car);
        } catch (Exception e) {
            logger.error("Error deleting car with ID: {}", carId, e);
            throw new RuntimeException("Error deleting car", e);
        }
    }

    // Registers a new customer in the system
    @Override
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        try {
            logger.debug("Registering customer: {}", customerDTO);
            Customer customer = objectMapper.convertValue(customerDTO, Customer.class);
            customer = customerRepository.save(customer);
            return objectMapper.convertValue(customer, CustomerDTO.class);
        } catch (Exception e) {
            logger.error("Error registering customer", e);
            throw new RuntimeException("Error registering customer", e);
        }
    }

    // Retrieves a customer by their ID
    @Override
    public CustomerDTO getCustomer(Long customerId) {
        try {
            logger.debug("Fetching customer with ID: {}", customerId);
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            return objectMapper.convertValue(customer, CustomerDTO.class);
        } catch (Exception e) {
            logger.error("Error fetching customer with ID: {}", customerId, e);
            throw new RuntimeException("Error fetching customer", e);
        }
    }

    // Updates an existing customer's details
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        try {
            logger.debug("Updating customer details: {}", customerDTO);
            Customer customer = customerRepository.findById(customerDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            customer = objectMapper.convertValue(customerDTO, Customer.class);
            customer = customerRepository.save(customer);
            return objectMapper.convertValue(customer, CustomerDTO.class);
        } catch (Exception e) {
            logger.error("Error updating customer", e);
            throw new RuntimeException("Error updating customer", e);
        }
    }

    // Retrieves all leases managed by the admin
    @Override
    public List<LeaseDTO> getAllLeases() {
        try {
            logger.debug("Fetching all leases");
            List<Lease> leases = leaseRepository.findAll();
            return leases.stream()
                    .map(lease -> objectMapper.convertValue(lease, LeaseDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all leases", e);
            throw new RuntimeException("Error fetching all leases", e);
        }
    }

    // Updates an existing lease's details
    @Override
    public LeaseDTO updateLease(LeaseDTO leaseDTO) {
        try {
            logger.debug("Updating lease details: {}", leaseDTO);
            Lease lease = leaseRepository.findById(leaseDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Lease not found"));
            lease.setStatus(leaseDTO.getStatus());
            lease = leaseRepository.save(lease);
            return objectMapper.convertValue(lease, LeaseDTO.class);
        } catch (Exception e) {
            logger.error("Error updating lease", e);
            throw new RuntimeException("Error updating lease", e);
        }
    }
}
