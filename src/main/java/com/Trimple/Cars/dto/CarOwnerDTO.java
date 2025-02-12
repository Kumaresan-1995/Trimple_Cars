package com.Trimple.Cars.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class CarOwnerDTO {

    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    // Indian mobile number pattern validation
    // Pattern: (XX) XXXXXX or XXXXXXXXXX (10 digits)
    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be a valid Indian mobile number (starting with 6-9 and followed by 9 digits).")
    private String phone;

    private List<Long> carIds;  // List of Car IDs owned by the owner

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Long> getCarIds() {
        return carIds;
    }

    public void setCarIds(List<Long> carIds) {
        this.carIds = carIds;
    }
}
