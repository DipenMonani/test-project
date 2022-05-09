package com.testtask.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String userName;
    private String password;

    //default constructor for JSON Parsing
    public UserLoginRequest() {
    }

    public UserLoginRequest(String userName, String password) {
        this.setUserName(userName);
        this.setPassword(password);
    }

}