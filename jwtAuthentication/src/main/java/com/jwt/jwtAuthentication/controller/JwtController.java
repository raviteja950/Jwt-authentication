package com.jwt.jwtAuthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.jwtAuthentication.helper.JwtUtility;
import com.jwt.jwtAuthentication.model.JwtRequest;
import com.jwt.jwtAuthentication.model.JwtResponce;
import com.jwt.jwtAuthentication.service.CusUserDetailsService;

@RestController
public class JwtController {

	@Autowired
	private CusUserDetailsService service;
	
	@Autowired
	private JwtUtility utility;
	
	@Autowired
	private AuthenticationManager manager;
	
	@RequestMapping(value = "/gettoken", method = RequestMethod.POST)
	public ResponseEntity<?> Generatetoken(@RequestBody JwtRequest jwtRequest){
		
		
	try {
		
		this.manager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
		
		
	}
	catch (UsernameNotFoundException e) {
		e.printStackTrace();
		throw new UsernameNotFoundException("Bad username");
	}
		
	UserDetails user=this.service.loadUserByUsername(jwtRequest.getUsername());
		
		String token=utility.generateToken(user);
		System.out.println("JWT-TOKEN---->"+token); 
		
		
		
		return ResponseEntity.ok(new JwtResponce(token));
		
	}
	
	
}
