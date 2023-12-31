package com.graduationproject.project.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.graduationproject.project.Checkers;
import com.graduationproject.project.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
 private final UserRepository userRepository;
 
 @Bean
 public UserDetailsService userDetailsService(){
   return usernameOrEmail -> Checkers.checkEmail(usernameOrEmail)?userRepository.findByEmail(usernameOrEmail).orElseThrow(()-> new UsernameNotFoundException("User not found"))
   :userRepository.findByUsername(usernameOrEmail).orElseThrow(()-> new UsernameNotFoundException("User not found"));

 }
 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
    return authenticationConfiguration.getAuthenticationManager();

 }
 @Bean
 public AuthenticationProvider authenticationProvider(){
final DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
daoAuthenticationProvider.setUserDetailsService(userDetailsService());
return daoAuthenticationProvider;
 }
 @Bean
 public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
 }
 @Bean
 public ModelMapper modelMapper(){
   return new ModelMapper();
 }

}
