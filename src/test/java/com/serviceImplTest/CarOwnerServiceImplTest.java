package com.serviceImplTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.Trimple.Cars.serviceImpl.CarOwnerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CarOwnerServiceImplTest {

    @Mock
    private CarOwnerRepository carOwnerRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private LeaseRepository leaseRepository;

    @InjectMocks
    private CarOwnerServiceImpl carOwnerService;

    private CarOwnerDTO carOwnerDTO;
    private CarDTO carDTO;
    private LeaseDTO leaseDTO;
    private CarOwner carOwner;
    private Car car;
    private Lease lease;

    @BeforeEach
    public void setUp() {
        // Initialize DTO objects
        carOwnerDTO = new CarOwnerDTO();
        carOwnerDTO.setId(1L);
        carOwnerDTO.setName("John Doe");
        carOwnerDTO.setEmail("john.doe@example.com");
        carOwnerDTO.setPhone("123-456-7890");

        carDTO = new CarDTO();
        carDTO.setId(1L);
        carDTO.setModel("Tesla Model S");
        carDTO.setMake("Tesla");
        carDTO.setRegistrationNumber("123ABC");

        leaseDTO = new LeaseDTO();
        leaseDTO.setId(1L);
        leaseDTO.setCarId(1L);
        leaseDTO.setCustomerId(1L);
        leaseDTO.setLeaseStartDate(LocalDate.parse("2023-01-01"));
        leaseDTO.setLeaseEndDate(LocalDate.parse("2023-12-31"));
        leaseDTO.setStatus("ACTIVE");

        // Initialize Entity objects
        carOwner = new CarOwner();
        carOwner.setId(1L);
        carOwner.setName("John Doe");
        carOwner.setEmail("john.doe@example.com");
        carOwner.setPhone("123-456-7890");

        car = new Car();
        car.setId(1L);
        car.setModel("Tesla Model S");
        car.setMake("Tesla");
        car.setRegistrationNumber("123ABC");
        car.setCarOwner(carOwner);

        lease = new Lease();
        lease.setId(1L);
        lease.setCarId(1L);
        lease.setCustomerId(1L);
        lease.setLeaseStartDate(LocalDate.parse("2023-01-01"));
        lease.setLeaseEndDate(LocalDate.parse("2023-12-31"));
        lease.setStatus("ACTIVE");
    }

    @Test
    public void testRegisterOwner() {
        when(carOwnerRepository.save(any(CarOwner.class))).thenReturn(carOwner);
        CarOwnerDTO result = carOwnerService.registerOwner(carOwnerDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(carOwnerRepository, times(1)).save(any(CarOwner.class));
    }

    @Test
    public void testRegisterOwnerException() {
        when(carOwnerRepository.save(any(CarOwner.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> carOwnerService.registerOwner(carOwnerDTO));
    }

    @Test
    public void testRegisterCarForOwner() {
        when(carOwnerRepository.findById(1L)).thenReturn(Optional.of(carOwner));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        CarDTO result = carOwnerService.registerCarForOwner(carDTO, 1L);

        assertNotNull(result);
        assertEquals("Tesla Model S", result.getModel());
        assertEquals("Tesla", result.getMake());
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    public void testRegisterCarForOwnerCarOwnerNotFound() {
        when(carOwnerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CarOwnerNotFoundException.class, () -> carOwnerService.registerCarForOwner(carDTO, 1L));
    }

    @Test
    public void testGetLeaseHistory() {
        when(leaseRepository.findByCarId(1L)).thenReturn(Optional.of(lease));

        LeaseDTO result = carOwnerService.getLeaseHistory(1L);

        assertNotNull(result);
        assertEquals("ACTIVE", result.getStatus());
        verify(leaseRepository, times(1)).findByCarId(1L);
    }

    @Test
    public void testGetLeaseHistoryNotFound() {
        when(leaseRepository.findByCarId(1L)).thenReturn(Optional.empty());

        assertThrows(LeaseNotFoundException.class, () -> carOwnerService.getLeaseHistory(1L));
    }

    @Test
    public void testGetOwner() {
        carOwner.setCars(Collections.singletonList(car));  // Simulate owner having a car
        when(carOwnerRepository.findById(1L)).thenReturn(Optional.of(carOwner));

        CarOwnerDTO result = carOwnerService.getOwner(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(1, result.getCarIds().size());
        assertEquals(Long.valueOf(1L), result.getCarIds().get(0));
        verify(carOwnerRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetOwnerNotFound() {
        when(carOwnerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CarOwnerNotFoundException.class, () -> carOwnerService.getOwner(1L));
    }
}
