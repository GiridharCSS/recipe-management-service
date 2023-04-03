package com.demo.recipemanagement.web.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.demo.recipemanagement.config.JwtTokenUtil;
import com.demo.recipemanagement.dto.JwtRequestDto;
import com.demo.recipemanagement.dto.JwtResponseDto;

class JwtAuthenticationControllerTest {

    private JwtAuthenticationController jwtAuthenticationController;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private UserDetailsService userDetailsService;
    private JwtRequestDto authenticationRequest;

    @BeforeEach
    public void setUp() {

	authenticationManager = mock(AuthenticationManager.class);
	jwtTokenUtil = mock(JwtTokenUtil.class);
	userDetailsService = mock(UserDetailsService.class);
	jwtAuthenticationController = new JwtAuthenticationController(authenticationManager, jwtTokenUtil,
		userDetailsService);

	authenticationRequest = new JwtRequestDto();
	authenticationRequest.setUsername("testuser");
	authenticationRequest.setPassword("testpassword");
    }

    @Test
    public void testCreateAuthenticationToken() throws Exception {

	UserDetails userDetails = mock(UserDetails.class);
	when(userDetailsService.loadUserByUsername(authenticationRequest.getUsername())).thenReturn(userDetails);
	String token = "testtoken";
	when(jwtTokenUtil.generateToken(userDetails)).thenReturn(token);
	ResponseEntity<?> response = jwtAuthenticationController.createAuthenticationToken(authenticationRequest);

	assertEquals(200, response.getStatusCodeValue());

	JwtResponseDto jwtResponse = (JwtResponseDto) response.getBody();
	assertEquals(token, jwtResponse.getToken());
    }

//    @Test
//    void testCreateAuthenticationToken() throws Exception {
//
//	final String username = "testuser";
//	final String password = "testpassword";
//	final String token = "testtoken";
//	JwtRequestDto jwtRequestDto = new JwtRequestDto();
//	jwtRequestDto.setUsername(username);
//	jwtRequestDto.setPassword(password);
//	UserDetails userDetails = User.builder().username(username).password(password).build();
//	when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
//	JwtAuthenticationController jwtAuthenticationController = new JwtAuthenticationController(authenticationManager,
//		jwtTokenUtil, userDetailsService);
//	when(jwtTokenUtil.generateToken(userDetails)).thenReturn(token);
//	JwtResponseDto jwtResponseDto = (JwtResponseDto) jwtAuthenticationController
//		.createAuthenticationToken(jwtRequestDto).getBody();
//	assertEquals(token, jwtResponseDto.getToken());
//    }

    @Test
    void testUserConstructorWithNullAuthorities() {

	assertThrows(IllegalArgumentException.class, () -> new User("username", "password", null));
    }

    @Test
    void testAuthenticate() throws Exception {

	final String username = "testuser";
	final String password = "testpassword";

	JwtRequestDto jwtRequestDto = new JwtRequestDto();
	jwtRequestDto.setUsername(username);
	jwtRequestDto.setPassword(password);

	jwtAuthenticationController.authenticate(username, password);
    }

}