package com.example.sae_mobile_api.sae_mobile_api.configs;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Base64;
public class SecurityConstant {
    public static final long JWTExpiration=3*31*24*60*60*1000L;// valable 3 mois
    
    private static String k = "abcdef";
    private static byte[] secretBytes=k.getBytes();
    public static final String secret64=Base64.getEncoder().encodeToString(secretBytes);
}
