package com.jaybe.websocketdemo.utils;

import com.jaybe.websocketdemo.models.AppUser;
import com.jaybe.websocketdemo.models.Block;
import com.jaybe.websocketdemo.models.Role;
import com.jaybe.websocketdemo.repositories.AppUserRepository;
import com.jaybe.websocketdemo.repositories.BlockRepository;
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
    private final BlockRepository blockRepository;

    public BootsTrapData(AppUserRepository appUserRepository,
                         RoleRepository roleRepository,
                         PasswordEncoder encoder,
                         BlockRepository blockRepository) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.blockRepository = blockRepository;
    }

    @EventListener
    @Transactional
    public void bootsTrapData(ContextRefreshedEvent event) {
        var adminRole = new Role();
        adminRole.setRole("ADMIN");

        var userRole = new Role();
        userRole.setRole("USER");

        var wsRole = new Role();
        wsRole.setRole("WS_USER");

        roleRepository.saveAll(Arrays.asList(adminRole, userRole, wsRole));

        var block_1 = Block.createNewBlock("tst_bck");
        var block_2 = Block.createNewBlock("stg_bck");
        var block_3 = Block.createNewBlock("prd_bck");


        var adminUser = new AppUser();
        adminUser.setUserName("foo");
        adminUser.setPassword(encoder.encode("foo"));
        adminUser.addRole(adminRole);
        adminUser.addBlock(block_1).addBlock(block_2);

        var user = new AppUser();
        user.setUserName("user");
        user.setPassword(encoder.encode("pass"));
        user.addRole(userRole);

        var wsUser = new AppUser();
        wsUser.setUserName("wsuser");
        wsUser.setPassword(encoder.encode("pass"));
        wsUser.addRole(wsRole);
        wsUser.addBlock(block_3);

        block_1.setAppUser(adminUser);
        block_2.setAppUser(adminUser);
        block_3.setAppUser(wsUser);

        appUserRepository.saveAll(Arrays.asList(adminUser, user, wsUser));
        //blockRepository.saveAll(Arrays.asList(block_1, block_2, block_3));

    }

}
