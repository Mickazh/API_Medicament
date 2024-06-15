package com.example.sae_mobile_api.sae_mobile_api.services;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.sae_mobile_api.sae_mobile_api.models.Personne;

public class CustomUserDetail implements UserDetails {
    private Personne personne;
    public CustomUserDetail(Personne p){
        this.personne=p;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return List.of(()->personne.getRole());
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.personne.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.personne.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
    
}
