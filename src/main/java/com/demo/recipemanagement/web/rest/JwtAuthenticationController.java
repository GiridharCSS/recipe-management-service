package com.demo.recipemanagement.web.rest;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.recipemanagement.config.JwtTokenUtil;
import com.demo.recipemanagement.dto.JwtRequestDto;
import com.demo.recipemanagement.dto.JwtResponseDto;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
	    UserDetailsService jwtInMemoryUserDetailsService) {
	this.authenticationManager = authenticationManager;
	this.jwtTokenUtil = jwtTokenUtil;
	this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDto authenticationRequest)
	    throws BadCredentialsException, DisabledException {

	authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

	final UserDetails userDetails = jwtInMemoryUserDetailsService
		.loadUserByUsername(authenticationRequest.getUsername());

	final String token = jwtTokenUtil.generateToken(userDetails);

	return ResponseEntity.ok(new JwtResponseDto(token));
    }

    void authenticate(String username, String password) throws BadCredentialsException, DisabledException {
	Objects.requireNonNull(username);
	Objects.requireNonNull(password);

	try {
	    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	} catch (DisabledException e) {
	    throw new DisabledException("USER_DISABLED", e);
	} catch (BadCredentialsException e) {
	    throw new BadCredentialsException("INVALID_CREDENTIALS", e);
	}
    }
}