package com.Trimple.Cars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Trimple.Cars.entity.Car; 

 
 

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(String status);
    List<Car> findAll();
}
