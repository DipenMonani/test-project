package com.testtask.model;

import com.testtask.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<RoleDTO> roles;

    public static UserDTO build(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(RoleDTO.build(user.getRoles()))
                .build();
    }

}
