package com.testtask.common;

import com.testtask.common.entity.Property;
import com.testtask.model.PropertyDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class TestUtils {

    public static List<Property> getPropertiesEntityList() {
        return Arrays.asList(getPropertyEntity(Long.valueOf(1)), getPropertyEntity(Long.valueOf(2)));
    }

    public static Property getPropertyEntity(Long id) {
        return Property.builder()
                .square(BigDecimal.valueOf(1234.12))
                .description("description")
                .latitude(BigDecimal.valueOf(1234.12))
                .longitude(BigDecimal.valueOf(2345.32))
                .country("India")
                .zip("123456")
                .address1("123123")
                .address2("esdf")
                .city("ahd")
                .build();
    }

    public static PropertyDTO getPropertyDTO() {
        return PropertyDTO.builder()
                .roomsNumber(12)
                .square(BigDecimal.valueOf(1234.12))
                .description("description")
                .latitude(BigDecimal.valueOf(1234.12))
                .longitude(BigDecimal.valueOf(2345.32))
                .country("India")
                .zip("123456")
                .address1("123123")
                .address2("esdf")
                .city("ahd")
                .userId(Long.valueOf(1))
                .build();
    }
}
