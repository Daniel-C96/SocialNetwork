package com.example.SocialNetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Controller", description = "Administrator functions")
public class AdminController {

    @Operation(description = "This is a test endpoint only accessible by Admins.",
            summary = "Test endpoint")
    @GetMapping("/test")
    public String test() {
        return "This is only accessible by admins.";
    }
}
