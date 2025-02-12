package com.Trimple.Cars.serviceImpl;

import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Trimple.Cars.dto.*;import com.Trimple.Cars.repository.*;
import com.Trimple.Cars.service.*;
import com.Trimple.Cars.entity.*;
import com.Trimple.Cars.exception.LeaseLimitExceededException;
import com.Trimple.Cars.exception.LeaseNotFoundException;

@Service
public class EndCustomerServiceImpl implements EndCustomerService {

    private static final Logger logger = LoggerFactory.getLogger(EndCustomerServiceImpl.class);

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private LeaseRepository leaseRepository;
    @Autowired
    private EndCustomerRepository endCustomerRepository;

    // Registers a new customer and returns the CustomerDTO
    @Override
    public CustomerDTO customerRegister(CustomerDTO customerDTO) {
        try {
            logger.debug("Registering customer: {}", customerDTO);
            // Map CustomerDTO to Customer entity
            Customer customer = new Customer();
            customer.setName(customerDTO.getName());
            customer.setEmail(customerDTO.getEmail());
            customer.setPhone(customerDTO.getPhone());

            // Save the customer to the repository
            customer = endCustomerRepository.save(customer);

            // Map saved Customer entity back to CustomerDTO
            customerDTO.setId(customer.getId());
            return customerDTO;
        } catch (Exception e) {
            logger.error("Error registering customer: {}", customerDTO, e);
            throw new RuntimeException("Error registering customer", e);
        }
    }

    // Retrieves available cars that are in "Ideal" status
    @Override
    public List<CarDTO> getAvailableCars() {
        try {
            logger.debug("Fetching available cars");
            List<Car> cars = carRepository.findByStatus("Ideal");
            return cars.stream().map(car -> {
                CarDTO carDTO = new CarDTO();
                carDTO.setModel(car.getModel());
                carDTO.setMake(car.getMake());
                carDTO.setRegistrationNumber(car.getRegistrationNumber());
                carDTO.setStatus(car.getStatus());
                return carDTO;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching available cars", e);
            throw new RuntimeException("Error fetching available cars", e);
        }
    }

    // Starts a new lease for a customer, with validation for active leases
    @Override
    public LeaseDTO startLease(LeaseDTO leaseDTO) {
        try {
            logger.debug("Starting lease for customer with ID: {}", leaseDTO.getCustomerId());
            // Validation: Check if the customer is already leasing two cars
            Long customerId = leaseDTO.getCustomerId();
            long activeLeases = leaseRepository.countByCustomerIdAndStatus(customerId, "ACTIVE");

            if (activeLeases >= 2) {
                throw new LeaseLimitExceededException("Customer cannot lease more than 2 cars at a time.");
            }

            // Create and save Lease entity from LeaseDTO
            Lease lease = new Lease();
            lease.setCarId(leaseDTO.getCarId());
            lease.setCustomerId(leaseDTO.getCustomerId());
            lease.setLeaseStartDate(leaseDTO.getLeaseStartDate());
            lease.setLeaseEndDate(leaseDTO.getLeaseEndDate());
            lease.setStatus("ACTIVE");

            lease = leaseRepository.save(lease);

            // Convert the saved Lease entity back to LeaseDTO
            leaseDTO.setId(lease.getId());
            leaseDTO.setStatus(lease.getStatus());

            return leaseDTO;
        } catch (Exception e) {
            logger.error("Error starting lease for customer ID: {}", leaseDTO.getCustomerId(), e);
            throw new RuntimeException("Error starting lease", e);
        }
    }

    // Ends a lease by updating the lease status to "Completed" and the end date to today
    @Override
    public LeaseDTO endLease(LeaseDTO leaseDTO) {
        try {
            logger.debug("Ending lease with ID: {}", leaseDTO.getId());
            Lease lease = leaseRepository.findById(leaseDTO.getId())
                    .orElseThrow(() -> new LeaseNotFoundException("Lease not found"));

            lease.setLeaseEndDate(LocalDate.now());
            lease.setStatus("Completed");
            leaseRepository.save(lease);

            return leaseDTO;
        } catch (Exception e) {
            logger.error("Error ending lease with ID: {}", leaseDTO.getId(), e);
            throw new RuntimeException("Error ending lease", e);
        }
    }

    // Retrieves the lease history for a customer by customerId
    @Override
    public List<LeaseDTO> getLeaseHistory(Long customerId) {
        try {
            logger.debug("Fetching lease history for customer ID: {}", customerId);
            List<Lease> leases = leaseRepository.findByCustomerId(customerId);
            return leases.stream().map(lease -> {
                LeaseDTO leaseDTO = new LeaseDTO();
                leaseDTO.setCarId(lease.getCarId());
                leaseDTO.setCustomerId(lease.getCustomerId());
                leaseDTO.setLeaseStartDate(lease.getLeaseStartDate());
                leaseDTO.setLeaseEndDate(lease.getLeaseEndDate());
                leaseDTO.setStatus(lease.getStatus());
                return leaseDTO;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching lease history for customer ID: {}", customerId, e);
            throw new RuntimeException("Error fetching lease history", e);
        }
    }
}
