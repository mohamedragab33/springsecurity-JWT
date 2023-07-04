package com.springsecurity.spring.security.config;

import com.springsecurity.spring.security.services.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Autowired
    private CustomUserService customUserService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(requests -> requests
                .requestMatchers(new AntPathRequestMatcher("/token")).permitAll()
                .anyRequest().authenticated())
                .sessionManagement( session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        );
        http.authenticationProvider(daoAuthenticationProvider());
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider obj = new DaoAuthenticationProvider();
        obj.setUserDetailsService(customUserService);
        obj.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return obj;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}