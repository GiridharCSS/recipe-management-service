package com.demo.recipemanagement.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.recipemanagement.service.impl.JwtUserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class JwtRequestFilterTest {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String VALID_JWT_TOKEN = "validJwtToken";
    private static final String INVALID_JWT_TOKEN = "invalidJwtToken";

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @Mock
    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    @Mock
    private WebAuthenticationDetailsSource webAuthenticationDetailsSource;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private OncePerRequestFilter oncePerRequestFilter;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    public void init() {
	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoFilterInternalWithValidJwtToken() throws ServletException, IOException {

	when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn(BEARER_PREFIX + VALID_JWT_TOKEN);
	when(jwtTokenUtil.getUsernameFromToken(VALID_JWT_TOKEN)).thenReturn("user");
	when(jwtTokenUtil.validateToken(VALID_JWT_TOKEN, userDetails)).thenReturn(true);
	when(jwtUserDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
	jwtRequestFilter.doFilterInternal(request, response, filterChain);
	verify(request, times(1)).getHeader(AUTHORIZATION_HEADER);
	verify(jwtTokenUtil, times(1)).getUsernameFromToken(VALID_JWT_TOKEN);
	verify(jwtTokenUtil, times(1)).validateToken(VALID_JWT_TOKEN, userDetails);
	verify(jwtUserDetailsService, times(1)).loadUserByUsername("user");
	verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternalWithInvalidJwtToken() throws ServletException, IOException {

	when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn(BEARER_PREFIX + INVALID_JWT_TOKEN);
	when(jwtTokenUtil.getUsernameFromToken(INVALID_JWT_TOKEN)).thenReturn(null);
	jwtRequestFilter.doFilterInternal(request, response, filterChain);
	verify(request, times(1)).getHeader(AUTHORIZATION_HEADER);
	verify(jwtTokenUtil, times(1)).getUsernameFromToken(INVALID_JWT_TOKEN);
	verify(jwtTokenUtil, never()).validateToken(anyString(), any());
	verify(jwtUserDetailsService, never()).loadUserByUsername(anyString());
	verify(securityContext, never()).setAuthentication(any());
	verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternalWithNoJwtToken() throws ServletException, IOException {

	when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn(null);
	jwtRequestFilter.doFilterInternal(request, response, filterChain);
	verify(request, times(1)).getHeader(AUTHORIZATION_HEADER);
    }
}