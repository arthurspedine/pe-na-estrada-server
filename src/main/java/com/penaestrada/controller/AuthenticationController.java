package com.penaestrada.controller;

import com.penaestrada.dto.Login;
import com.penaestrada.dto.LoginResponse;
import com.penaestrada.infra.security.TokenService;
import com.penaestrada.model.User;
import com.penaestrada.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid Login data, HttpServletResponse response) {
        var user = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = manager.authenticate(user);
        String token = tokenService.genToken((User) auth.getPrincipal());
        Cookie cookie = new Cookie("pe_access_token", token);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
