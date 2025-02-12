package com.Trimple.Cars.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

    private Long id;

    @NotEmpty(message = "Model cannot be empty")
    private String model;

    @NotEmpty(message = "Make cannot be empty")
    private String make;

    // Indian vehicle registration number pattern
    // Indian registration number format is typically like 'KA 01 AB 1234'
    @NotEmpty(message = "Registration number cannot be empty")
    @Pattern(regexp = "^[A-Z]{2}\\s[0-9]{2}\\s[A-Z]{1,2}\\s[0-9]{4}$",
             message = "Invalid registration number format. It should be in the format 'KA 01 AB 1234'.")
    private String registrationNumber;

    @NotNull(message = "Status cannot be null")
    private String status;

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



}

    
    


