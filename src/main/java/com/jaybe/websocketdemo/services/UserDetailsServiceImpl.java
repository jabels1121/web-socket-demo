package com.jaybe.websocketdemo.services;

import com.jaybe.websocketdemo.models.Customer;
import com.jaybe.websocketdemo.repositories.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var appUserOptional = appUserRepository.findByUserName(username);

        var appUser = appUserOptional.orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found!");
        });

        return new Customer(appUser);
    }
}
