package com.flyaway.controller;

import com.flyaway.dto.UserRegisterRequest;
import com.flyaway.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserRegisterRequest req) {
        Long id = userService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        var user = userService.getUserById(id);
        return ResponseEntity.ok(Map.of("id", user.getId(), "email", user.getEmail(),
                "firstName", user.getFirstName(), "lastName", user.getLastName()));
    }
}
