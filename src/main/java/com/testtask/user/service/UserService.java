package com.testtask.user.service;

import com.testtask.common.entity.Role;
import com.testtask.common.entity.User;
import com.testtask.common.exception.BadRequestException;
import com.testtask.common.repository.RoleRepository;
import com.testtask.common.repository.UserRepository;
import com.testtask.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public User registerUser(UserDTO userDTO) {
        if (userDTO == null) throw new BadRequestException("Request is null or empty.");

        Boolean isUserExists = userRepository.existsByEmail(userDTO.getEmail());
        if (isUserExists) {
            throw new BadRequestException("User is already registered with email: " + userDTO.getEmail());
        }

        Role role = roleRepository.findByRoleName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = mapToUser(userDTO, roles);
        return userRepository.save(user);
    }

    private User mapToUser(UserDTO userDTO, Set<Role> roles) {
        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .roles(roles)
                .build();
    }
}
