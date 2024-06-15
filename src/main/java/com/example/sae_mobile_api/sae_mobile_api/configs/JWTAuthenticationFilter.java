package com.example.sae_mobile_api.sae_mobile_api.configs;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.sae_mobile_api.sae_mobile_api.services.CustomUserDetailService;
import org.springframework.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String token = getJwtFromRequest(request);
        if(StringUtils.hasText(token) && jwtGenerator.validateToken(token)){
            String userName=jwtGenerator.getUserNameFromJWT(token);
            UserDetails userDetails=customUserDetailService.loadUserByUsername(userName);
            System.out.println("userDetails ");
            userDetails.getAuthorities().stream().forEach(auth->System.out.println(auth.getAuthority()));
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    public String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        String tokenHeader = "Bearer ";
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            //System.out.println("BEARER TOKEN SA MERE "+bearerToken.substring(tokenHeader.length(), bearerToken.length()));
            return bearerToken.substring(tokenHeader.length(), bearerToken.length());
        }
        return null;
    }
}
