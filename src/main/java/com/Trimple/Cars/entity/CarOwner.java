package com.Trimple.Cars.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "car_owner")  // Specifies the table name in the database as "car_owner"
public class CarOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Explicit column name for the 'id' field
    private Long id;

    @Column(name = "name")  // Explicit column name for 'name' field
    private String name;

    @Column(name = "email")  // Explicit column name for 'email' field
    private String email;

    @Column(name = "phone")  // Explicit column name for 'phone' field
    private String phone;

    @OneToMany(mappedBy = "carOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Car> cars;

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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
