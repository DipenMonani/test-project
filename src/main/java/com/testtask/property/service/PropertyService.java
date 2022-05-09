package com.testtask.property.service;

import com.testtask.common.entity.Property;
import com.testtask.common.entity.PropertyImage;
import com.testtask.common.entity.User;
import com.testtask.common.exception.BadRequestException;
import com.testtask.common.exception.NotFoundException;
import com.testtask.common.repository.PropertyImageRepository;
import com.testtask.common.repository.PropertyRepository;
import com.testtask.common.repository.UserRepository;
import com.testtask.model.PropertyDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PropertyService {

    private UserRepository userRepository;

    private PropertyRepository propertyRepository;

    private PropertyImageRepository propertyImageRepository;

    private static String UPLOADED_FOLDER = "D:\\propertiesImage\\";

    public PropertyDTO addProperties(PropertyDTO request, List<MultipartFile> multipartFiles) {
        if (request == null) {
            throw new BadRequestException("Request is missing.");
        }
        if (request.getUserId() == null) {
            throw new BadRequestException("UserId is missing in request.");
        }
        User user = userRepository.findById(request.getUserId()).
                orElseThrow(() -> new NotFoundException("User not found for userId: " + request.getUserId()));
        Property property = mapToProperty(new Property(), request);
        property.setUser(user);
        propertyRepository.save(property);
        if (CollectionUtils.isNotEmpty(multipartFiles)) {
            uploadImage(multipartFiles, property);
        }

        return PropertyDTO.build(property);
    }

    private List<PropertyImage> uploadImage(List<MultipartFile> multipartFiles, Property property) {
        List<PropertyImage> propertyImages = new ArrayList<>();
        multipartFiles.forEach(file -> {
            try {
                File path = new File(UPLOADED_FOLDER + file.getOriginalFilename());
                path.createNewFile();
                FileOutputStream output = new FileOutputStream(path);
                output.write(file.getBytes());
                output.close();
                PropertyImage propertyImage = PropertyImage.builder()
                        .imageUrl(String.format("%s%s", UPLOADED_FOLDER, file.getOriginalFilename()))
                        .property(property)
                        .build();
                propertyImages.add(propertyImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            property.setPropertyImages(propertyImages);
        });
        if (CollectionUtils.isNotEmpty(propertyImages)) {
            propertyImageRepository.saveAll(propertyImages);
        }
        return propertyImages;
    }

    public Page<List<PropertyDTO>> getAllProperties(Pageable pageable) {
        Page<Property> properties = propertyRepository.findAll(pageable);
        List<PropertyDTO> propertyDTOS = PropertyDTO.build(properties.getContent());
        return new PageImpl(propertyDTOS, pageable, properties.getTotalElements());
    }

    public PropertyDTO updateProperty(PropertyDTO updateProperty, Boolean isAdmin, List<MultipartFile> multipartFiles) {
        if (updateProperty == null) {
            throw new BadRequestException("Request is missing.");
        }
        Property property;
        if (isAdmin) {
            property = propertyRepository.findById(updateProperty.getId())
                    .orElseThrow(() -> new NotFoundException("Property not found with id: " + updateProperty.getId()));
        } else {
            property = propertyRepository.findByIdAndUserId(updateProperty.getId(), updateProperty.getUserId())
                    .orElseThrow(() -> new NotFoundException("Property not belongs to logged in user: " + updateProperty.getUserId()));
        }

        property = mapToProperty(property, updateProperty);
        deletePropertyImagesByPropertyId(updateProperty.getId());
        uploadImage(multipartFiles, property);
        propertyRepository.save(property);
        return PropertyDTO.build(property);
    }

    private void deletePropertyImagesByPropertyId(Long id) {
        List<PropertyImage> propertyImages = propertyImageRepository.findAllByPropertyId(id);
        if (CollectionUtils.isNotEmpty(propertyImages)) {
            propertyImageRepository.deleteAll(propertyImages);
        }
    }

    public void deleteProperty(Long propertyId, Long userId, Boolean isAdmin) {
        if (propertyId == null) {
            throw new BadRequestException("PropertyId is missing in request.");
        }
        if (isAdmin) {
            propertyRepository.findById(propertyId)
                    .orElseThrow(() -> new NotFoundException("Property not found with id: " + propertyId));
        } else {
            propertyRepository.findByIdAndUserId(propertyId, userId)
                    .orElseThrow(() -> new NotFoundException("Property not belongs to logged in user: " + userId));
        }
        deletePropertyImagesByPropertyId(propertyId);
        propertyRepository.deleteById(propertyId);
    }

    private Property mapToProperty(Property property, PropertyDTO request) {
        property.setRoomsNumber(request.getRoomsNumber());
        property.setSquare(request.getSquare());
        property.setDescription(request.getDescription());
        property.setLatitude(request.getLatitude());
        property.setLongitude(request.getLongitude());
        property.setCity(request.getCity());
        property.setCountry(request.getCountry());
        property.setZip(request.getZip());
        property.setAddress1(request.getAddress1());
        property.setAddress2(request.getAddress2());
        return property;
    }

}