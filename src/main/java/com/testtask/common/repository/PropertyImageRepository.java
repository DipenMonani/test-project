package com.testtask.common.repository;

import com.testtask.common.entity.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {

    List<PropertyImage> findAllByPropertyId(Long propertyId);

    void deleteAllByPropertyId(Long propertyId);
}
