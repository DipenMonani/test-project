package com.testtask.model;

import com.testtask.common.entity.PropertyImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyImageDTO {

    private Long id;
    private String imageUrl;


    public static PropertyImageDTO build(PropertyImage propertyImages) {
        return PropertyImageDTO.builder()
                .id(propertyImages.getId())
                .imageUrl(propertyImages.getImageUrl())
                .imageUrl(propertyImages.getImageUrl())
                .build();
    }

    public static List<PropertyImageDTO> build(List<PropertyImage> propertyImages) {
        if (CollectionUtils.isEmpty(propertyImages)) {
            return new ArrayList<>();
        }
        return propertyImages.stream().map(PropertyImageDTO::build).collect(Collectors.toList());
    }

}
