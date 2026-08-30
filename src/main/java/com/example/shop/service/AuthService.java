package com.example.shop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.shop.dto.RegisterRequest;
import com.example.shop.model.User;
import com.example.shop.repository.UserRepository;

@Service
public class AuthService {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(
        String username,
        String password
    ) {
        User user =
                userRepository
                    .findByUsername(username)
                    .orElseThrow(() ->
                        new RuntimeException("Invalid credentials")
                    );
               
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }        
        
        return jwtService.generateToken(user.getUsername());
    }

    public User register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(
            passwordEncoder.encode(request.getPassword())
        );

        user.setRole(
            request.getRole() != null ? request.getRole() : "USER"
        );

        return userRepository.save(user);
    }

}
