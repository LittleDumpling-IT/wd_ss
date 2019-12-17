package com.wendu.config.security;

import com.wendu.model.Admin;
import com.wendu.service.AdminService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class MyAuthenticationProvider implements AuthenticationProvider {


    private AdminService adminService;


    public MyAuthenticationProvider(AdminService adminService) {
        this.adminService = adminService;
    }


    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        Admin admin = adminService.findByUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (admin == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!bCryptPasswordEncoder.matches(password, admin.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return usernamePasswordAuthenticationToken;

    }


    public boolean supports(Class<?> authentication) {
        return true;
    }


}
