package com.kidneystone.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kidneystone.dto.AuthResponse;
import com.kidneystone.dto.LoginRequest;
import com.kidneystone.dto.RegisterRequest;
import com.kidneystone.model.User;
import com.kidneystone.repository.UserRepository;
import com.kidneystone.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                encodedPassword
        );

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);

        try {
            emailService.sendEmail(
                    savedUser.getEmail(),
                    "Welcome to KidneyStone Manager",
                    "Hello " + savedUser.getUsername() + ",\n\n"
                            + "Your KidneyStone Manager account was created successfully.\n\n"
                            + "You can now log in and manage your kidney stone analysis records.\n\n"
                            + "Best regards,\n"
                            + "KidneyStone Manager"
            );
        } catch (Exception e) {
            System.out.println("Email could not be sent: " + e.getMessage());
        }

        return new AuthResponse(
                "User registered successfully",
                token,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                "Login successful",
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}