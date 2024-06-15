package com.example.sae_mobile_api.sae_mobile_api.services;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RSAService {
    private static PublicKey publicKey=null;
    private static PrivateKey privateKey=null;

    @Value("${publicKey64}")
    private String publicKeyString;
    @Value("${privateKey64}")
    private String privateKeyString;

    public String encrypt(String plaintext) throws Exception {
        PublicKey publicKey = getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String ciphertext) throws Exception {
        PrivateKey privateKey = getPrivateKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes);
    }

    private PublicKey getPublicKey() throws Exception {
        if(RSAService.publicKey==null){
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAService.publicKey=keyFactory.generatePublic(keySpec);
        }
        return RSAService.publicKey;
    }

    private PrivateKey getPrivateKey() throws Exception {
        if(RSAService.privateKey==null){
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAService.privateKey=keyFactory.generatePrivate(keySpec);
        }

        return RSAService.privateKey;
    }
}