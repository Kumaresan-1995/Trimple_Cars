package com.Trimple.Cars.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class LeaseDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotNull(message = "Car ID cannot be null")
    private Long carId;

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotNull(message = "Lease start date cannot be null")
    @PastOrPresent(message = "Lease start date must be in the past or present")
    private LocalDate leaseStartDate;

    @NotNull(message = "Lease end date cannot be null")
    @FutureOrPresent(message = "Lease end date must be in the future or present")
    private LocalDate leaseEndDate;

    @NotEmpty(message = "Status cannot be empty")
    private String status;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(LocalDate leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public LocalDate getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(LocalDate leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
