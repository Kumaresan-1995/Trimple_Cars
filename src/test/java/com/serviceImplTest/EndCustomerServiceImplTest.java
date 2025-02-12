package com.serviceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 

import com.Trimple.Cars.dto.CarDTO;
import com.Trimple.Cars.dto.CustomerDTO;
import com.Trimple.Cars.dto.LeaseDTO;
import com.Trimple.Cars.entity.Car;
import com.Trimple.Cars.entity.Customer;
import com.Trimple.Cars.entity.Lease;
import com.Trimple.Cars.exception.LeaseLimitExceededException;
import com.Trimple.Cars.repository.CarRepository;
import com.Trimple.Cars.repository.EndCustomerRepository;
import com.Trimple.Cars.repository.LeaseRepository;
import com.Trimple.Cars.serviceImpl.EndCustomerServiceImpl;
 
 

public class EndCustomerServiceImplTest {

    @InjectMocks
    private EndCustomerServiceImpl endCustomerService; // The service to be tested

    @Mock
    private CarRepository carRepository; // Mocking CarRepository

    @Mock
    private LeaseRepository leaseRepository; // Mocking LeaseRepository

    @Mock
    private EndCustomerRepository endCustomerRepository; // Mocking EndCustomerRepository

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testCustomerRegister_Success() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("John Doe");
        customerDTO.setEmail("john.doe@example.com");
        customerDTO.setPhone("1234567890");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());

        when(endCustomerRepository.save(any(Customer.class))).thenReturn(customer); // Mock save behavior

        CustomerDTO result = endCustomerService.customerRegister(customerDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("1234567890", result.getPhone());
        assertEquals(1L, result.getId());

        verify(endCustomerRepository, times(1)).save(any(Customer.class)); // Verify save method is called once
    }

    @Test
    public void testGetAvailableCars_Success() {
        Car car1 = new Car();
        car1.setModel("Model X");
        car1.setMake("Tesla");
        car1.setRegistrationNumber("ABC123");
        car1.setStatus("Ideal");

        Car car2 = new Car();
        car2.setModel("Model Y");
        car2.setMake("Tesla");
        car2.setRegistrationNumber("XYZ789");
        car2.setStatus("Ideal");

        when(carRepository.findByStatus("Ideal")).thenReturn(Arrays.asList(car1, car2));

        List<CarDTO> result = endCustomerService.getAvailableCars();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Model X", result.get(0).getModel());
        assertEquals("Model Y", result.get(1).getModel());

        verify(carRepository, times(1)).findByStatus("Ideal");
    }

    @Test
    public void testStartLease_Success() {
        LeaseDTO leaseDTO = new LeaseDTO();
        leaseDTO.setCustomerId(1L);
        leaseDTO.setCarId(1L);
        leaseDTO.setLeaseStartDate(LocalDate.parse("2023-01-01"));
        leaseDTO.setLeaseEndDate(LocalDate.parse("2023-12-31"));

        when(leaseRepository.countByCustomerIdAndStatus(1L, "ACTIVE")).thenReturn(1L); // Mock customer having one active lease
        Lease lease = new Lease();
        lease.setId(1L);
        lease.setCarId(leaseDTO.getCarId());
        lease.setCustomerId(leaseDTO.getCustomerId());
        lease.setLeaseStartDate(leaseDTO.getLeaseStartDate());
        lease.setLeaseEndDate(leaseDTO.getLeaseEndDate());
        lease.setStatus("ACTIVE");

        when(leaseRepository.save(any(Lease.class))).thenReturn(lease);

        LeaseDTO result = endCustomerService.startLease(leaseDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ACTIVE", result.getStatus());

        verify(leaseRepository, times(1)).countByCustomerIdAndStatus(1L, "ACTIVE");
        verify(leaseRepository, times(1)).save(any(Lease.class));
    }

    @Test
    public void testStartLease_LimitExceeded() {
        LeaseDTO leaseDTO = new LeaseDTO();
        leaseDTO.setCustomerId(1L);
        leaseDTO.setCarId(1L);
        leaseDTO.setLeaseStartDate(LocalDate.parse("2023-01-01"));
        leaseDTO.setLeaseEndDate(LocalDate.parse("2023-12-31"));

        when(leaseRepository.countByCustomerIdAndStatus(1L, "ACTIVE")).thenReturn(2L); // Mock customer having two active leases

        LeaseLimitExceededException exception = assertThrows(LeaseLimitExceededException.class, () -> {
            endCustomerService.startLease(leaseDTO);
        });

        assertEquals("Customer cannot lease more than 2 cars at a time.", exception.getMessage());

        verify(leaseRepository, times(1)).countByCustomerIdAndStatus(1L, "ACTIVE");
        verify(leaseRepository, times(0)).save(any(Lease.class)); // Save should not be called
    }

    @Test
    public void testEndLease_Success() {
        LeaseDTO leaseDTO = new LeaseDTO();
        leaseDTO.setId(1L);
        leaseDTO.setCarId(1L);
        leaseDTO.setCustomerId(1L);

        Lease lease = new Lease();
        lease.setId(1L);
        lease.setCarId(leaseDTO.getCarId());
        lease.setCustomerId(leaseDTO.getCustomerId());
        lease.setLeaseStartDate(LocalDate.parse("2023-01-01"));
        lease.setLeaseEndDate(null);
        lease.setStatus("ACTIVE");

        when(leaseRepository.findById(1L)).thenReturn(java.util.Optional.of(lease));

        LeaseDTO result = endCustomerService.endLease(leaseDTO);

        assertNotNull(result);
        assertEquals("Completed", result.getStatus());
        assertEquals(LocalDate.now(), result.getLeaseEndDate());

        verify(leaseRepository, times(1)).findById(1L);
        verify(leaseRepository, times(1)).save(any(Lease.class));
    }

    @Test
    public void testGetLeaseHistory_Success() {
        Lease lease1 = new Lease();
        lease1.setId(1L);
        lease1.setCarId(1L);
        lease1.setCustomerId(1L);
        lease1.setLeaseStartDate(LocalDate.parse("2023-01-01"));
        lease1.setLeaseEndDate(LocalDate.parse("2023-12-31"));
        lease1.setStatus("Completed");

        Lease lease2 = new Lease();
        lease2.setId(2L);
        lease2.setCarId(2L);
        lease2.setCustomerId(1L);
        lease2.setLeaseStartDate(LocalDate.parse("2022-05-01"));
        lease2.setLeaseEndDate(LocalDate.parse("2022-11-01"));
        lease2.setStatus("Completed");

        when(leaseRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(lease1, lease2));

        List<LeaseDTO> result = endCustomerService.getLeaseHistory(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getCarId());
        assertEquals(2L, result.get(1).getCarId());

        verify(leaseRepository, times(1)).findByCustomerId(1L);
    }
}

