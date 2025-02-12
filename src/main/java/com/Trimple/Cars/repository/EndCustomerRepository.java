package com.Trimple.Cars.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Trimple.Cars.entity.Customer;
@Repository
public interface EndCustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findById(Long id);

}
