package com.example.shop.service;

import org.springframework.stereotype.Service;

import com.example.shop.model.User;

@Service
public class UserService {
    
    public User getUser() {
        return new User(
            1L,
            "John",
            "john@exmaol.com"
        );
    }
}
