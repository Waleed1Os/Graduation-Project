package com.graduationproject.project.authentication;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
// import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.project.user.User;
import com.graduationproject.project.user.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
// @Service
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler{
    private final UserRepository userRepository;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
       final Map<String,String> json = new ObjectMapper().readValue(request.getInputStream(),Map.class); 
        final String username = json.get("username");
        final User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        user.incrementFailures();
        super.onAuthenticationFailure(request, response, exception);
    }
    


}
