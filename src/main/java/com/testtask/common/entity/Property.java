package com.testtask.common.entity;

import com.testtask.common.entity.auditable.BaseRepoEntityAuditable;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "properties")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Property extends BaseRepoEntityAuditable<String, Long> {

    @Column(name = "room_number")
    private Integer roomsNumber;
    private BigDecimal square;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String city;
    private String country;
    private String zip;
    @Column(name = "address_1")
    private String address1;
    @Column(name = "address_2")
    private String address2;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PropertyImage> propertyImages;
}
