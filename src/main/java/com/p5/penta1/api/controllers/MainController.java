package com.p5.penta1.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello folks.");
    }

    @PostMapping("/owner")
    public ResponseEntity<String> owner(@RequestParam(name = "name", required = false) String name) {

        String owner = name == null ? "world" : name;

        return ResponseEntity.ok("Hello, " + owner + " !");
    }
}
