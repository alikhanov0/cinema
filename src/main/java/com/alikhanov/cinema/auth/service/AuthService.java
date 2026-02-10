package com.alikhanov.cinema.auth.service;

import com.alikhanov.cinema.auth.dto.RegisterRequest;
import com.alikhanov.cinema.auth.entity.Role;
import com.alikhanov.cinema.auth.entity.User;
import com.alikhanov.cinema.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Email already registered");
        }

        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.USER);

        userRepository.save(user);
    }
}
