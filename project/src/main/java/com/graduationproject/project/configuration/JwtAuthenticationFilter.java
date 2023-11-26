package com.graduationproject.project.configuration;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.graduationproject.project.token.TokenRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
private final JwtService jwtService;
private final UserDetailsService userDetailsService;
private final TokenRepository tokenRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                if(request.getServletPath().contains("/api/v1/auth")) {
                    filterChain.doFilter(request, response);
                    return;
                  }        
        final String header= request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header==null||!header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        } 
        final String jwt=header.substring(7);
        final String username=jwtService.extractUsername(header);
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            final UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            final boolean isStoredTokenValid= tokenRepository.findByToken(jwt).map(token-> !token.isExpired()&&!token.isRevoked()).orElse(false);
            if(jwtService.isTokenValid(userDetails, jwt)&&isStoredTokenValid){
               final UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authenticationToken); 
            }
        }
        filterChain.doFilter(request, response);
    }
    
}