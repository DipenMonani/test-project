package com.testtask.model;

import com.testtask.common.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CurrentLoggedInUser {

    private Long id;
    private String Username;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isAdmin;
    private List<RoleDTO> roles;

    public static CurrentLoggedInUser build(User user, Boolean isAdmin) {
        return CurrentLoggedInUser.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isAdmin(isAdmin)
                .roles(RoleDTO.build(user.getRoles()))
                .build();
    }
}
