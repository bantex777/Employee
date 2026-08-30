package com.example.shop.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.shop.service.CustomDetailService;
import com.example.shop.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomDetailService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomDetailService userDetailService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
  
        String header =
                        request.getHeader("Authorization");
    
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token =
                    header.substring(7);
    
        try {
            String username =
                            jwtService.extractUsername(token);

            UserDetails userDetails = 
                            userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());      
                    
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authenticationToken);

        } catch (Exception exception) {
             SecurityContextHolder
                .clearContext();
        }

        filterChain.doFilter(request, response);
    }
    
}
