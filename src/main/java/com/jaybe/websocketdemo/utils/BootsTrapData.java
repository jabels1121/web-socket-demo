package com.jaybe.websocketdemo.utils;

import com.jaybe.websocketdemo.models.AppUser;
import com.jaybe.websocketdemo.models.Role;
import com.jaybe.websocketdemo.repositories.AppUserRepository;
import com.jaybe.websocketdemo.repositories.RoleRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class BootsTrapData {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public BootsTrapData(AppUserRepository appUserRepository,
                         RoleRepository roleRepository,
                         PasswordEncoder encoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @EventListener
    @Transactional
    public void bootsTrapData(ContextRefreshedEvent event) {
        var adminRole = new Role();
        adminRole.setRole("ADMIN");

        var userRole = new Role();
        userRole.setRole("USER");

        roleRepository.saveAll(Arrays.asList(adminRole, userRole));

        var adminUser = new AppUser();
        adminUser.setUserName("foo");
        adminUser.setPassword(encoder.encode("foo"));
        adminUser.addRole(adminRole);

        var user = new AppUser();
        user.setUserName("user");
        user.setPassword(encoder.encode("pass"));
        user.addRole(userRole);

        appUserRepository.saveAll(Arrays.asList(adminUser, user));
    }

}
