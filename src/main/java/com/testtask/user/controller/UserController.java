package com.testtask.user.controller;

import com.testtask.auth.service.UserAuthServiceImpl;
import com.testtask.common.CommonUtils;
import com.testtask.common.entity.User;
import com.testtask.common.exception.BadRequestException;
import com.testtask.config.JwtTokenUtil;
import com.testtask.model.UserDTO;
import com.testtask.model.UserLoginRequest;
import com.testtask.model.UserLoginResponse;
import com.testtask.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private JwtTokenUtil jwtTokenUtil;

    private UserAuthServiceImpl userAuthService;

    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest userLoginRequest) throws Exception {

        userAuthService.authenticate(userLoginRequest.getUserName(), userLoginRequest.getPassword());

        final UserDetails userDetails = userAuthService.loadUserByUsername(userLoginRequest.getUserName());

        final String token = jwtTokenUtil.generateToken(userDetails);
        User user = userAuthService.findByEmail(userLoginRequest.getUserName());
        return ResponseEntity.ok(new UserLoginResponse(token, user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user) throws Exception {
        if (!CommonUtils.isEmailIsValid(user.getEmail())) {
            throw new BadRequestException("Email is not valid.");
        }
        return ResponseEntity.ok(userService.registerUser(user));
    }

}