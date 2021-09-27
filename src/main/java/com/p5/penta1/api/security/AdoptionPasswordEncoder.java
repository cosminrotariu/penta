package com.p5.penta1.api.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdoptionPasswordEncoder extends BCryptPasswordEncoder {
}