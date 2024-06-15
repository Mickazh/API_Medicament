package com.example.sae_mobile_api.sae_mobile_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.sae_mobile_api.sae_mobile_api.configs.JWTGenerator;
import com.example.sae_mobile_api.sae_mobile_api.models.CustomJsonString;
import com.example.sae_mobile_api.sae_mobile_api.models.Personne;
import com.example.sae_mobile_api.sae_mobile_api.models.Role;
import com.example.sae_mobile_api.sae_mobile_api.models.RequestDataStructure.PasswordResetRequest;
import com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure.AuthResponse;
import com.example.sae_mobile_api.sae_mobile_api.repositorys.PersonneRepository;
import com.example.sae_mobile_api.sae_mobile_api.services.PasswordResetService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.hibernate.grammars.hql.HqlParser.SecondContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
public abstract class AuthController {
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private PersonneRepository personneRepository;
    @Autowired
    private PasswordResetService passwordResetService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired 
    private JWTGenerator jwtGenerator;

    @PostMapping("/register")
    public ResponseEntity<CustomJsonString> register(@RequestBody Personne p) { 
        System.out.println(p);   
        
        Personne personne=personneRepository.findByEmail(p.getEmail());
        if(personne!=null){
            return new ResponseEntity<>(new CustomJsonString("email existe deja"),HttpStatus.BAD_REQUEST);
        }
        personne=p;
        personne.setPassword(passwordEncoder.encode(p.getPassword()));
        personne.setRole(getRole());
        personneRepository.save(personne);
        return new ResponseEntity<>(new CustomJsonString(personne.getNom()+" "+personne.getPrenom()+" enregistré"),HttpStatus.OK);
    }
    protected abstract String getRole();

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Personne p) {
        System.out.println(p);
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(p.getEmail(), p.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String role = authentication.getAuthorities().stream()
        .findFirst()
        .map(GrantedAuthority::getAuthority)
        .orElse("");
        System.out.println("ROLE / "+role);

        Personne pFromDB = personneRepository.findByEmail(p.getEmail());

        if(!role.equals(getRole())){
            return new ResponseEntity<>(new AuthResponse("acces refusé",null),HttpStatus.UNAUTHORIZED);
        }
        String token= jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponse(token,pFromDB),HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<CustomJsonString> resetPasswordPost(@RequestBody PasswordResetRequest p){
        String email=p.getEmail();
        String newPassword=p.getNewPassword();
        String tokenOriginale=p.getTokenOriginale();
        String code6=p.getCode6();
        
        if(passwordResetService.resetPassword(email, newPassword,tokenOriginale,getTokenClient(email, code6)))
            return new ResponseEntity<>(new CustomJsonString("mdp reset avec succès "), HttpStatus.OK);
        return  new ResponseEntity<>(new CustomJsonString("erreur reinitialisation mot de passe "), HttpStatus.OK);
    }

    @GetMapping("/reset-password-get-token")
    public ResponseEntity<CustomJsonString>  resetPasswordGet(@RequestParam("email") String email){
        System.out.println("envoie du code6 à "+email);
        return passwordResetService.sendPasswordResetRequest(email); 
    }    
    @GetMapping("/test")
    public String getMethodName() {
        return "test auith";
    }


    private String getTokenClient(String email,String code6){
        return String.format("%s|%s|%s", email,code6,getCurrentDate());

    }
    private String getCurrentDate(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
}
