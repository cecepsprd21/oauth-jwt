package com.jwt.oauth2.controller;

import com.jwt.oauth2.filter.JwtRequestFilter;
import com.jwt.oauth2.model.UserEntity;
import com.jwt.oauth2.repository.UserRepository;
import com.jwt.oauth2.response.AuthenticationResponse;
import com.jwt.oauth2.service.UserDetailsServiceImpl;
import com.jwt.oauth2.service.UserService;
import com.jwt.oauth2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class OauthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserEntity user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username or password", e);
        }

        UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getUserName());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}