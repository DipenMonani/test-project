package com.testtask.common.entity;

import com.testtask.common.entity.auditable.BaseRepoEntityAuditable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "properties_image")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PropertyImage extends BaseRepoEntityAuditable<String, Long> {

    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "properties_id")
    private Property property;
}
