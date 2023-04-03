package com.demo.recipemanagement.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
class JwtTokenUtilTest {

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

	Field field = jwtTokenUtil.getClass().getDeclaredField("secret");
	field.setAccessible(true);
	field.set(jwtTokenUtil, "test");

    }

    @Test
    void testGenerateToken() {

	when(userDetails.getUsername()).thenReturn("testuser");
	String token = jwtTokenUtil.generateToken(userDetails);
	String username = jwtTokenUtil.getUsernameFromToken(token);
	assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void testGetIssuedAtDateFromToken() {

	Map<String, Object> claims = new HashMap<>();
	Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	UserDetails userDetails = new User("test", "test123", authorities);
	String token = jwtTokenUtil.doGenerateToken(claims, userDetails.getUsername());
	Date issuedAt = jwtTokenUtil.getIssuedAtDateFromToken(token);
	assertNotNull(issuedAt);
    }

    @Test
    void testGetExpirationDateFromToken() {

	Map<String, Object> claims = new HashMap<>();
	Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	UserDetails userDetails = new User("test", "test123", authorities);
	String token = jwtTokenUtil.doGenerateToken(claims, userDetails.getUsername());
	Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token);
	assertNotNull(expirationDate);
    }

    @Test
    void testGetClaimFromToken() {

	Map<String, Object> claims = new HashMap<>();
	claims.put("key1", "value1");
	claims.put("key2", "value2");
	Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	UserDetails userDetails = new User("test", "test123", authorities);
	String token = jwtTokenUtil.doGenerateToken(claims, userDetails.getUsername());
	Function<Claims, String> resolver = c -> (String) c.get("key1");
	String result = jwtTokenUtil.getClaimFromToken(token, resolver);
	assertEquals("value1", result);
    }

    @Test
    void testCanTokenBeRefreshed() {
	Map<String, Object> claims = new HashMap<>();
	Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	UserDetails userDetails = new User("test", "test123", authorities);
	String token = jwtTokenUtil.doGenerateToken(claims, userDetails.getUsername());
	assertTrue(jwtTokenUtil.canTokenBeRefreshed(token));
    }

    @Test
    void testValidateToken() {

	Map<String, Object> claims = new HashMap<>();
	Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	UserDetails userDetails = new User("test", "test123", authorities);
	String token = jwtTokenUtil.doGenerateToken(claims, userDetails.getUsername());
	boolean result = jwtTokenUtil.validateToken(token, userDetails);
	assertTrue(result);

	UserDetails userDetails_2 = new User("test", "test123", authorities);
	token = jwtTokenUtil.doGenerateToken(claims, userDetails_2.getUsername());
	result = jwtTokenUtil.validateToken(token, userDetails_2);
	assertTrue(result);
    }
}