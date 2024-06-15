package com.example.sae_mobile_api.sae_mobile_api.configs;

import java.util.Date;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Component
public class JWTGenerator {
    public String generateToken(Authentication authentication){
        String userName=authentication.getName();
        Date currentDate=new Date();
        Date expireDate=new Date(currentDate.getTime()+SecurityConstant.JWTExpiration);
        System.out.println("CURRENT DATE "+currentDate);
        System.out.println("EXPIRE DATE "+expireDate);
        String token= Jwts.builder()
                        .setSubject(userName)
                        .setIssuedAt(currentDate)
                        .setExpiration(expireDate)
                        .signWith(SignatureAlgorithm.HS512, SecurityConstant.secret64)
                        .compact();
        return token;
    }

    public String getUserNameFromJWT(String token){
        Claims claims=Jwts.parser()
                        .setSigningKey(SecurityConstant.secret64)
                        .parseClaimsJws(token)
                        .getBody();
        System.out.println("CLAIMS SUBJECT "+claims.getSubject()); 
        return claims.getSubject();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SecurityConstant.secret64).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationCredentialsNotFoundException("JWT expired");
        }
    }
}
