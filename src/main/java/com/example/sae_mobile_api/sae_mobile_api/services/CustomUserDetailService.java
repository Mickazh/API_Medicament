package com.example.sae_mobile_api.sae_mobile_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.sae_mobile_api.sae_mobile_api.models.Personne;
import com.example.sae_mobile_api.sae_mobile_api.repositorys.PersonneRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
    @Autowired
    private PersonneRepository userRepository;

    @Override
    //charge les infos d'une personne donné 
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Personne personne = userRepository.findByEmail(email);
        if (personne==null){
            throw new UsernameNotFoundException(email+" n'est pas repertorié dans notre base de donées");
        }
        return new CustomUserDetail(personne);
        
    }
    
}
