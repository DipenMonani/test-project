package com.testtask.property.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.common.CommonUtils;
import com.testtask.model.CurrentLoggedInUser;
import com.testtask.model.PropertyDTO;
import com.testtask.property.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.testtask.common.CommonUtils.getCurrentLoggedInUser;
import static com.testtask.common.CommonUtils.validateUser;

@RestController
@RequestMapping("api/v1/property")
@AllArgsConstructor
public class PropertyController {

    private PropertyService propertyService;
    private ObjectMapper objectMapper;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<PropertyDTO> addProperties(@RequestParam(value = "request", required = true) String property,
                                                     @RequestParam(value = "property_image", required = false) List<MultipartFile> multipartFiles) throws JsonProcessingException {

        PropertyDTO propertyDTO = objectMapper.readValue(property, PropertyDTO.class);

        return new ResponseEntity(propertyService.addProperties(propertyDTO, multipartFiles), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<List<PropertyDTO>>> getAllProperties(Pageable pageable) {
        return new ResponseEntity(propertyService.getAllProperties(pageable), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PropertyDTO> updateProperty(
            @RequestParam(value = "request", required = true) String property,
            @RequestParam(value = "property_image", required = false) List<MultipartFile> multipartFiles) throws JsonProcessingException {
        PropertyDTO propertyDTO = objectMapper.readValue(property, PropertyDTO.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentLoggedInUser currentLoggedInUser = CommonUtils.getCurrentLoggedInUser(authentication);
        Boolean isAdmin = Boolean.FALSE;
        if (currentLoggedInUser.getIsAdmin()) {
            isAdmin = currentLoggedInUser.getIsAdmin();
        } else {
            validateUser(currentLoggedInUser, propertyDTO.getUserId());
        }

        return new ResponseEntity(propertyService.updateProperty(propertyDTO, isAdmin, multipartFiles), HttpStatus.OK);
    }

    @DeleteMapping("/{propertyId}/user/{userId}")
    public ResponseEntity<?> deletePropertie(@PathVariable Long propertyId,
                                             @PathVariable Long userId) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentLoggedInUser currentLoggedInUser = getCurrentLoggedInUser(authentication);
        Boolean isAdmin = Boolean.FALSE;
        if (currentLoggedInUser.getIsAdmin()) {
            isAdmin = currentLoggedInUser.getIsAdmin();
        } else {
            validateUser(currentLoggedInUser, userId);
        }
        propertyService.deleteProperty(propertyId, userId, isAdmin);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
