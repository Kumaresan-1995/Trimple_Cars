package com.Trimple.Cars.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "lease")  // Custom table name
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lease_id")  // Custom column name for 'id'
    private Long id;

    @Column(name = "car_id")  // Custom column name for 'carId'
    private Long carId;

    @Column(name = "customer_id")  // Custom column name for 'customerId'
    private Long customerId;

    @Column(name = "lease_start_date")  // Custom column name for 'leaseStartDate'
    private LocalDate leaseStartDate;

    @Column(name = "lease_end_date")  // Custom column name for 'leaseEndDate'
    private LocalDate leaseEndDate;

    @Column(name = "status")  // Custom column name for 'status'
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
