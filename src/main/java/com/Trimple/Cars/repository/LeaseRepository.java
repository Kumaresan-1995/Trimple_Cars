package com.Trimple.Cars.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Trimple.Cars.entity.Lease;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByCustomerId(Long customerId);

    Optional<Lease> findByCarId(Long carId);

    long countByCustomerIdAndStatus(Long customerId, String string);

    List<Lease> findAll();
}
