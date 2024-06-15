package com.example.sae_mobile_api.sae_mobile_api.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;


import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import com.example.sae_mobile_api.sae_mobile_api.models.Role;
import com.example.sae_mobile_api.sae_mobile_api.services.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.csrf(csrf->csrf.disable())
        .exceptionHandling(exception->exception.authenticationEntryPoint(jwtAuthEntryPoint))
        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
        request->request.requestMatchers("/admin/register","/user/register").permitAll()//permet les enregistrements
        .requestMatchers("/admin/login","/user/login").permitAll()//permet les logins
        .requestMatchers("/admin/test","/user/test").permitAll()// A ENLEVER EN PROD §§§§§§§§§§§§§§§§§§§§
        .requestMatchers("/admin/findById","/user/findById").permitAll()// A ENLEVER EN PROD §§§§§§§§§§§§§§§§§§§§

        .requestMatchers("/admin/reset-password-get-token","/user/reset-password-get-token").permitAll()//permet l'envoie des code6 au reset de mdp
        .requestMatchers("/admin/reset-password","/user/reset-password").permitAll()//permet le reset de mdp
        // .requestMatchers("/admin/**").hasAnyAuthority(Role.ADMIN.name()) a remettre en prod
        // .requestMatchers("/user/**").hasAnyAuthority(Role.USER.name()) a remettre en prod
        .anyRequest().permitAll()
        )
        .httpBasic(Customizer.withDefaults());
        
        
        return http.build();
    }
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
     
    @Bean
    public AuthenticationManager  AuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Allow all origins
        config.addAllowedMethod("*"); // Allow all HTTP methods
        config.addAllowedHeader("*"); // Allow all headers
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
