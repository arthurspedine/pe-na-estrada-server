package com.penaestrada.config;

import com.penaestrada.model.Role;
import com.penaestrada.model.User;
import com.penaestrada.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        UserDetails userAdmin = repository.findByLogin("admin");

        if (userAdmin == null) {
            User user = new User();
            user.setLogin("admin");
            user.setPassword(encoder.encode("123"));
            user.setRole(Role.ADMIN);
            repository.save(user);
        } else {
            System.out.println("Admin já existe");
        }

    }
}
