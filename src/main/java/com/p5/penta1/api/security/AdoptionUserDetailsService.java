package com.p5.penta1.api.security;

import com.p5.penta1.api.repository.users.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AdoptionUserDetailsService implements UserDetailsService
{
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final AdoptionPasswordEncoder passwordEncoder;

    public AdoptionUserDetailsService(UserRepo userRepo, RoleRepo roleRepo, AdoptionPasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepo;
        this.roleRepository = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if (!userOptional.isPresent())
        {
            throw new UsernameNotFoundException(username);
        }

        return new AdoptionUserPrincipal(userOptional.get());
    }

    @Bean
    private CommandLineRunner setUpDefaultUser()
    {
        return args -> {
            final String defaultEmail = "animalshelter@pentastagiu.io";
            final String defaultPassword = "password";

            Role moderatorRole = roleRepository.findByRole(RolesEnum.ROLE_MOD).orElseGet(() -> {
                Role role = new Role().setRole(RolesEnum.ROLE_MOD);
                return roleRepository.save(role);
            });

            userRepository.findByEmail(defaultEmail).orElseGet(() -> {
                User user = new User().setEmail(defaultEmail)
                        .setPassword(passwordEncoder.encode(defaultPassword))
                        .setRoles(Collections.singleton(moderatorRole));
                return userRepository.save(user);
            });
        };
    }
}