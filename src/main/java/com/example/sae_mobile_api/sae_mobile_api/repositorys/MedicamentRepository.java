package com.example.sae_mobile_api.sae_mobile_api.repositorys;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.sae_mobile_api.sae_mobile_api.models.Medicament;


public interface MedicamentRepository extends JpaRepository<Medicament,Integer>{
    public Medicament findByCIP13(Long cIP13);
    public Medicament findByCIP7(String cIP7);
    public Medicament findByCIS(String cIS);
    public List<Medicament> findByDenominationContaining(String partialName);
    public List<Medicament> findByDenominationIn(List<String> noms);
    public List<Medicament> findByCIP13In(List<Long> cip13);
    public Page<Medicament> findByDenominationContaining(String partialName, Pageable pageable);
    public Page<Medicament> findByDenominationStartingWith(String partialName, Pageable pageable);
}