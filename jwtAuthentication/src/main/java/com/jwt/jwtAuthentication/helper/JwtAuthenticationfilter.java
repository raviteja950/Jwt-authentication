package com.jwt.jwtAuthentication.helper;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.jwtAuthentication.service.CusUserDetailsService;

@Component
public class JwtAuthenticationfilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtility jwtUtility;

	@Autowired
	private CusUserDetailsService cusUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String TokenHeader = request.getHeader("Authorization");
		String username = null;
		String token = null;

		if (TokenHeader != null && TokenHeader.startsWith("Bearer ")) {

			token = TokenHeader.substring(7);

			try {

				username = jwtUtility.getUsernameFromToken(token);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			UserDetails userDetails= cusUserDetailsService.loadUserByUsername(username);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			} else {
				System.out.println("Token is not valid");
			}
		}
		filterChain.doFilter(request, response);

	}

}
