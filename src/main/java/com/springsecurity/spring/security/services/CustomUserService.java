package com.springsecurity.spring.security.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

authList.add(new SimpleGrantedAuthority("UserHd"));
authList.add(new SimpleGrantedAuthority("User-Admin"));
authList.add(new SimpleGrantedAuthority("User-SuperAdmin"));
authList.add(new SimpleGrantedAuthority("UserHd-Super"));
authList.add(new SimpleGrantedAuthority("UserHd-organization"));
authList.add(new SimpleGrantedAuthority("UserHd-Tester"));

      if (username.equals("hero")){
          return new User("hero","123456",authList);
      }else
         throw new UsernameNotFoundException("user not found");
    }
}
