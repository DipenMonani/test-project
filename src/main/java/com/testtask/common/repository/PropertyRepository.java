package com.testtask.common.repository;

import com.testtask.common.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByIdAndUserId(Long id, Long propertyId);
}
