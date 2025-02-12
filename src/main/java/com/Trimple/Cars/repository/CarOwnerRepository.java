package com.Trimple.Cars.repository;

 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Trimple.Cars.entity.CarOwner;
@Repository
public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {
	 Optional<CarOwner> findById(Long id);
}
