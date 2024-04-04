package ru.fusing.costprice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TestController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/role_admin")
    public String getName() {
        return "TestController with admin role is work fine";
    }

    @GetMapping("/hello")
    public String getHello() {
        return "hello with auth :^)";
    }
}
