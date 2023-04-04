package com.demo.recipemanagement.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceImplTest {

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Test
    void testLoadUserByUsername_When_Success() {

	String adminUsername = "admin";
	UserDetails adminUserDetails = new User(adminUsername,
		"$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", new ArrayList<>());
	UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(adminUsername);
	assertEquals(adminUserDetails.getUsername(), userDetails.getUsername());
	assertEquals(adminUserDetails.getPassword(), userDetails.getPassword());
	assertEquals(adminUserDetails.getAuthorities(), userDetails.getAuthorities());
    }

    @Test
    void testLoadUserByUsername_WhenFailure() {

	assertThrows(UsernameNotFoundException.class,
		() -> jwtUserDetailsService.loadUserByUsername("invalid_username"));
    }
}