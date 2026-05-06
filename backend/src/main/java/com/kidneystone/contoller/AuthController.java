package com.kidneystone.contoller;

import com.kidneystone.dto.AuthResponse;
import com.kidneystone.dto.LoginRequest;
import com.kidneystone.dto.RegisterRequest;
import com.kidneystone.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        AuthResponse response=authService.register(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@Valid @RequestBody LoginRequest request){
        AuthResponse response=authService.login(request);
        return ResponseEntity.ok(response);
    }

}
