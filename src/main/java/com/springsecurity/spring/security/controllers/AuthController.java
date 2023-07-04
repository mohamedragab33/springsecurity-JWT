package com.springsecurity.spring.security.controllers;

import com.springsecurity.spring.security.entity.JWTRequest;
import com.springsecurity.spring.security.services.CustomUserService;
import com.springsecurity.spring.security.utility.JwtUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private  CustomUserService customUserService;
    @Autowired
    private  JwtUtility jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(CustomUserService customUserService, AuthenticationManager authenticationManager) {
        this.customUserService = customUserService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/token")
public ResponseEntity<String> token(@RequestBody JWTRequest jwtRequest){

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(),jwtRequest.getPassword()));
 return new ResponseEntity<>(jwtUtility.generateToken(jwtRequest.getUserName()),HttpStatus.OK);
}


}
