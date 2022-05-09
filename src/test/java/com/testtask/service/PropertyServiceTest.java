package com.testtask.service;

import com.testtask.common.entity.Property;
import com.testtask.common.entity.User;
import com.testtask.common.exception.BadRequestException;
import com.testtask.common.exception.NotFoundException;
import com.testtask.common.repository.PropertyImageRepository;
import com.testtask.common.repository.PropertyRepository;
import com.testtask.common.repository.UserRepository;
import com.testtask.model.PropertyDTO;
import com.testtask.property.service.PropertyService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.testtask.common.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PropertyService.class})
public class PropertyServiceTest {

    @Autowired
    private PropertyService propertyService;

    @MockBean
    private PropertyRepository propertyRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PropertyImageRepository propertyImageRepository;

    @Before("")
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Add property success")
    public void add_property_should_return_success() {
        when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(User.builder()
                .email("test@test.com")
                .firstName("test")
                .build()));
        PropertyDTO response = propertyService.addProperties(getPropertyDTO(), new ArrayList<>());
        assertNotNull(response);
    }

    @Test
    @DisplayName("Add property throws user not found exception")
    public void add_property_should_return_userNotFoundException() {
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> propertyService.addProperties(getPropertyDTO(), new ArrayList<>()),
                "Expected doThing() to throw, but it didn't"
        );
        assertEquals("User not found for userId: 1", thrown.getMessage());
    }

    @Test
    @DisplayName("Add property throws BadRequest exception for null request")
    public void add_property_should_return_badRequestException_forNullAttributes() {
        PropertyDTO propertyDTO = null;
        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> propertyService.addProperties(propertyDTO, new ArrayList<>()),
                "Expected doThing() to throw, but it didn't"
        );
        assertEquals("Request is missing.", thrown.getMessage());
    }

    @Test
    @DisplayName("Add property throws BadRequest exception for userId is missing in request")
    public void add_property_should_return_badRequestException() {
        PropertyDTO propertyDTO = getPropertyDTO();
        propertyDTO.setUserId(null);
        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> propertyService.addProperties(propertyDTO, new ArrayList<>()),
                "Expected doThing() to throw, but it didn't"
        );
        assertEquals("UserId is missing in request.", thrown.getMessage());
    }

    @Test
    @DisplayName("Update property should return success")
    public void update_property_should_return_success() {
        when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(User.builder()
                .email("test@test.com")
                .firstName("test")
                .build()));
        when(propertyRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(getPropertyEntity(Long.valueOf(1))));
        PropertyDTO propertyDTO = getPropertyDTO();
        propertyDTO.setId(Long.valueOf(1));

        propertyDTO.setAddress1("Address test update");
        PropertyDTO response = propertyService.updateProperty(propertyDTO, true, new ArrayList<>());
        assertNotNull(response);
        assertEquals(propertyDTO.getAddress1(), response.getAddress1());
    }

    @Test
    @DisplayName("Update Property should return not found for wrong propertyId")
    public void update_property_should_return_notFoundExceptionForProperty() {
        PropertyDTO propertyDTO = getPropertyDTO();
        propertyDTO.setId(Long.valueOf(1));

        propertyDTO.setAddress1("Address test update");
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> propertyService.updateProperty(propertyDTO, true, new ArrayList<>()),
                "Expected doThing() to throw, but it didn't"
        );
        assertEquals("Property not found with id: 1", thrown.getMessage());
    }

    @Test
    @DisplayName("Delete Property Should Success")
    public void delete_property_should_success() {
        when(propertyRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(getPropertyEntity(Long.valueOf(1))));
        propertyService.deleteProperty(Long.valueOf(1), Long.valueOf(1), true);
        verify(propertyRepository).deleteById(Long.valueOf(1));
    }

    @Test
    @DisplayName("Delete Property Should success for non admin user")
    public void delete_property_should_success_for_non_admin_user() {
        when(propertyRepository.findByIdAndUserId(Long.valueOf(1), Long.valueOf(1))).thenReturn(Optional.of(getPropertyEntity(Long.valueOf(1))));
        propertyService.deleteProperty(Long.valueOf(1), Long.valueOf(1), false);
        verify(propertyRepository).deleteById(Long.valueOf(1));
    }

    @Test
    @DisplayName("Delete Property Should fail for non admin user")
    public void delete_property_should_fail_for_non_admin_user() {
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> propertyService.deleteProperty(Long.valueOf(1), Long.valueOf(1), false),
                "Expected doThing() to throw, but it didn't"
        );
        assertEquals("Property not belongs to logged in user: 1", thrown.getMessage());
    }


    @Test
    @DisplayName("Delete Property Should return badRequest exception for propertyId missing in request")
    public void delete_property_should_return_badRequestException() {
        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> propertyService.deleteProperty(null, Long.valueOf(1), true),
                "Expected doThing() to throw, but it didn't"
        );
        assertEquals("PropertyId is missing in request.", thrown.getMessage());
    }

    @Test
    @DisplayName("Delete Property Should return not found exception for property")
    public void delete_property_should_return_notFoundException() {
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> propertyService.deleteProperty(Long.valueOf(1), Long.valueOf(1), true),
                "Expected doThing() to throw, but it didn't"
        );
        assertEquals("Property not found with id: 1", thrown.getMessage());
    }

    @Test
    @DisplayName("getAllProperty should success")
    public void getAllProperties_property_should_success() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Property> properties = getPropertiesEntityList();
        when(propertyRepository.findAll(pageable)).thenReturn(new PageImpl(properties, pageable, properties.size()));

        Page<List<PropertyDTO>> propertyPage = propertyService.getAllProperties(pageable);
        assertNotNull(propertyPage.getContent());
        assertEquals(2, propertyPage.getTotalElements());
    }

    @Test
    @DisplayName("getAllProperty should return empty list")
    public void getAllProperties_property_should_Return_empty_list() {
        Pageable pageable = PageRequest.of(0, 10);
        when(propertyRepository.findAll(pageable)).thenReturn(new PageImpl(new ArrayList(), pageable, 0));

        Page<List<PropertyDTO>> propertyPage = propertyService.getAllProperties(pageable);
        assertEquals(0, propertyPage.getTotalElements());
    }

}
