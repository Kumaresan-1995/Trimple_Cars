package com.serviceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.Trimple.Cars.serviceImpl.AdminServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private CarOwnerRepository carOwnerRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private EndCustomerRepository customerRepository;

    @Mock
    private LeaseRepository leaseRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AdminServiceImpl adminService;

    private CarOwnerDTO carOwnerDTO;
    private CarDTO carDTO;
    private CustomerDTO customerDTO;
    private LeaseDTO leaseDTO;
    private CarOwner carOwner;
    private Car car;
    private Customer customer;
    private Lease lease;

    @BeforeEach
    public void setUp() {
        // Initialize DTO objects
        carOwnerDTO = new CarOwnerDTO();
        carOwnerDTO.setId(1L);
        carOwnerDTO.setName("Owner");

        carDTO = new CarDTO();
        carDTO.setId(1L);
        carDTO.setModel("Model");
        carDTO.setMake("Make");

        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("Customer");

        leaseDTO = new LeaseDTO();
        leaseDTO.setId(1L);
        leaseDTO.setStatus("ACTIVE");

        // Initialize Entity objects
        carOwner = new CarOwner();
        carOwner.setId(1L);
        carOwner.setName("Owner");

        car = new Car();
        car.setId(1L);
        car.setModel("Model");
        car.setMake("Make");
        car.setCarOwner(carOwner);

        customer = new Customer();
        customer.setId(1L);
        customer.setName("Customer");

        lease = new Lease();
        lease.setId(1L);
        lease.setStatus("ACTIVE");
        lease.setCarId(1L);
        lease.setCustomerId(1L);
    }

    @Test
    public void testRegisterCarOwner() {
        when(objectMapper.convertValue(carOwnerDTO, CarOwner.class)).thenReturn(carOwner);
        when(carOwnerRepository.save(carOwner)).thenReturn(carOwner);
        when(objectMapper.convertValue(carOwner, CarOwnerDTO.class)).thenReturn(carOwnerDTO);

        CarOwnerDTO result = adminService.registerCarOwner(carOwnerDTO);

        assertNotNull(result);
        assertEquals("Owner", result.getName());
        verify(carOwnerRepository, times(1)).save(carOwner);
    }

    @Test
    public void testGetCarOwner() {
        when(carOwnerRepository.findById(1L)).thenReturn(Optional.of(carOwner));
        when(objectMapper.convertValue(carOwner, CarOwnerDTO.class)).thenReturn(carOwnerDTO);

        CarOwnerDTO result = adminService.getCarOwner(1L);

        assertNotNull(result);
        assertEquals("Owner", result.getName());
        verify(carOwnerRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCarOwnerNotFound() {
        when(carOwnerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.getCarOwner(1L));
    }

    @Test
    public void testRegisterCarForOwner() {
        when(carOwnerRepository.findById(1L)).thenReturn(Optional.of(carOwner));
        when(objectMapper.convertValue(carDTO, Car.class)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        when(objectMapper.convertValue(car, CarDTO.class)).thenReturn(carDTO);

        CarDTO result = adminService.registerCarForOwner(carDTO, 1L);

        assertNotNull(result);
        assertEquals("Model", result.getModel());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    public void testRegisterCarForOwnerNotFound() {
        when(carOwnerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.registerCarForOwner(carDTO, 1L));
    }

    @Test
    public void testDeleteCar() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        adminService.deleteCar(1L);

        verify(carRepository, times(1)).delete(car);
    }

    @Test
    public void testDeleteCarNotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.deleteCar(1L));
    }

    @Test
    public void testRegisterCustomer() {
        when(objectMapper.convertValue(customerDTO, Customer.class)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(objectMapper.convertValue(customer, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO result = adminService.registerCustomer(customerDTO);

        assertNotNull(result);
        assertEquals("Customer", result.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testGetCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(objectMapper.convertValue(customer, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO result = adminService.getCustomer(1L);

        assertNotNull(result);
        assertEquals("Customer", result.getName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.getCustomer(1L));
    }

    @Test
    public void testUpdateLease() {
        when(leaseRepository.findById(1L)).thenReturn(Optional.of(lease));
        lease.setStatus("Completed");
        when(leaseRepository.save(lease)).thenReturn(lease);
        when(objectMapper.convertValue(lease, LeaseDTO.class)).thenReturn(leaseDTO);

        LeaseDTO result = adminService.updateLease(leaseDTO);

        assertNotNull(result);
        assertEquals("Completed", result.getStatus());
        verify(leaseRepository, times(1)).save(lease);
    }

    @Test
    public void testUpdateLeaseNotFound() {
        when(leaseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.updateLease(leaseDTO));
    }
}
