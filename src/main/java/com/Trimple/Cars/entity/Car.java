package com.Trimple.Cars.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "car")  // Specify the table name as "car"
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Explicit column name for the 'id' field
    private Long id;

    @Column(name = "model")  // Explicit column name for 'model' field
    private String model;

    @Column(name = "make")  // Explicit column name for 'make' field
    private String make;

    @Column(name = "registration_number")  // Explicit column name for 'registrationNumber' field
    private String registrationNumber;

    @Column(name = "status")  // Explicit column name for 'status' field
    private String status; // "Ideal", "On Lease", "On Service"

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)  // Specifies the foreign key column
    private CarOwner carOwner;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CarOwner getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(CarOwner carOwner) {
        this.carOwner = carOwner;
    }
}
