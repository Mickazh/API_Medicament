package com.example.sae_mobile_api.sae_mobile_api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.sae_mobile_api.sae_mobile_api.models.Pharmacie;


public interface PharmacieRepository extends JpaRepository<Pharmacie,Integer>{

    public List<Pharmacie> findByNomPharmacieContaining(String nomPharmacie);
    public List<Pharmacie> findByRegionContaining(String region);
    public List<Pharmacie> findByCodePostaleStartingWith(String codePostale);

    public List<Pharmacie> findByRegionInOrDepartementInOrCodePostaleIn(List<String>regions,List<String>departements,List<String>codePostale);
} 
