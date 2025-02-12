package com.Trimple.Cars.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "admin")  // This will explicitly set the table name to "admin" in the database
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Column name for 'id' field
    private Long id;

    @Column(name = "username")  // Column name for 'username' field
    private String username;

    @Column(name = "password")  // Column name for 'password' field
    private String password;

    @Column(name = "role")  // Column name for 'role' field
    private String role; // "Admin"

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
