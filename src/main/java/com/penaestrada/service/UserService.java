package com.penaestrada.service;

import com.penaestrada.dto.Login;
import com.penaestrada.model.Role;
import com.penaestrada.model.User;
import com.penaestrada.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User createUser(Login data) {
        if (data.email() == null || data.email().isEmpty())
            throw new IllegalArgumentException("O email é obrigatório.");
        if (data.password() == null || data.password().isEmpty())
            throw new IllegalArgumentException("A senha é obrigatória.");

        if (repository.findByLogin(data.email()) != null)
            throw new IllegalArgumentException("Já existe um usuário com o email informado.");

        User newUser = new User();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        newUser.setLogin(data.email());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(Role.USER);
        repository.save(newUser);
        return newUser;
    }
}
