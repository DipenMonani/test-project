package com.testtask.model;

import com.testtask.common.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {

    private Long id;
    private Integer roomsNumber;
    private BigDecimal square;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String city;
    private String country;
    private String zip;
    private String address1;
    private String address2;
    private Long userId;
    private List<PropertyImageDTO> propertyImages;

    public static PropertyDTO build(Property property) {
        return PropertyDTO.builder()
                .id(property.getId())
                .roomsNumber(property.getRoomsNumber())
                .square(property.getSquare())
                .description(property.getDescription())
                .latitude(property.getLatitude())
                .longitude(property.getLongitude())
                .city(property.getCity())
                .country(property.getCountry())
                .zip(property.getZip())
                .address1(property.getAddress1())
                .address2(property.getAddress2())
                .propertyImages(PropertyImageDTO.build(property.getPropertyImages()))
                .build();
    }

    public static List<PropertyDTO> build(List<Property> property) {
        if (CollectionUtils.isEmpty(property)) {
            return new ArrayList();
        }
        return property.stream().map(PropertyDTO::build).collect(Collectors.toList());
    }
}
