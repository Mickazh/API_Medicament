package com.example.sae_mobile_api.sae_mobile_api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.sae_mobile_api.sae_mobile_api.models.Personne;

public interface PersonneRepository extends JpaRepository<Personne,Integer>{
    
    public Personne findByEmail(String email);
}