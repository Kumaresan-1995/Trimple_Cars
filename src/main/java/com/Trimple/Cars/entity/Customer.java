package com.Trimple.Cars.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "customer")  // Explicit table name in the database
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Explicit column name for 'id'
    private Long id;

    @Column(name = "name")  // Explicit column name for 'name'
    private String name;

    @Column(name = "email")  // Explicit column name for 'email'
    private String email;

    @Column(name = "phone")  // Explicit column name for 'phone'
    private String phone;

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
}
