package com.graduationproject.project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfigurationSource;

// import com.graduationproject.project.authentication.LoginFailureHandler;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {
private final JwtAuthenticationFilter authenticationFilter;    
private final LogoutHandler logoutHandler;
private final AuthenticationProvider authenticationProvider;
private final CorsConfigurationSource corsConfigurationSource;
// private final LoginFailureHandler loginFailureHandler;
    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception{
        http
        .csrf(CsrfConfigurer::disable)
        .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**","/subscription/plans").permitAll().anyRequest().authenticated())
        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authenticationProvider(authenticationProvider)
        // .requiresChannel(requiresChannel -> requiresChannel.anyRequest().requiresSecure())//using https
        // .formLogin(formLogin -> formLogin.failureHandler(loginFailureHandler))
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        // .logout(logout->logout.addLogoutHandler(logoutHandler).logoutSuccessHandler(
        // (request, response, authentication) -> SecurityContextHolder.clearContext()).logoutUrl("api/v1/auth/logout")
        .logout(t -> t.addLogoutHandler(logoutHandler).logoutUrl("api/v1/auth/logout").logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
            // );
        return http.build();
    } 
}
