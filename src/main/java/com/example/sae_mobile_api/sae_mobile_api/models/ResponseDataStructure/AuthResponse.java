package com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure;

import com.example.sae_mobile_api.sae_mobile_api.models.Personne;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String tokenType;
    private Personne personne;
    public AuthResponse(String accessToken,Personne p){
        this.accessToken=accessToken;
        this.tokenType="Bearer ";

        //ne pas renvoyer ces infos au front
        p.setPassword(null);
        p.setRole(null);
        this.personne=p;
    }
}
