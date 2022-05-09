package com.testtask.common.entity;

import com.testtask.common.entity.auditable.BaseRepoEntityAuditable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseRepoEntityAuditable<String, Long> {
    private String roleName;
    private String description;
}
