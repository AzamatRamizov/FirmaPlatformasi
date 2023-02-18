package com.example.firmaplatformasi.Filter;

import com.example.firmaplatformasi.Service.UserService;
import com.example.firmaplatformasi.Token.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Filtr extends OncePerRequestFilter {
    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth=request.getHeader("Auth");
        if(auth!=null){
            if (tokenGenerator.tokenCheck(auth)) {
                String token1 = tokenGenerator.getUsername(auth);
                if (token1 != null) {
                    UserDetails userDetails = userService.loadUserByUsername(token1);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
