package com.example.sae_mobile_api.sae_mobile_api.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sae_mobile_api.sae_mobile_api.models.Pharmacie;
import com.example.sae_mobile_api.sae_mobile_api.models.Signalement;
import com.example.sae_mobile_api.sae_mobile_api.models.RequestDataStructure.FilteredSignalementRequest;
import com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure.AuditSignalementResponse;
import com.example.sae_mobile_api.sae_mobile_api.repositorys.PharmacieRepository;

@Service
public class PharmacieService {
    @Autowired
    private PharmacieRepository pharmacieRepository;
    
    public List<Pharmacie> getPharmaciesWithPartialName(String partialName){
        return pharmacieRepository.findByNomPharmacieContaining(partialName);
    }
    public List<Pharmacie> getPharmaciesWithCodePostale(String codePostale){
        return pharmacieRepository.findByCodePostaleStartingWith(codePostale);
    }
    public List<Pharmacie> getPharmaciesWithCodeRegion(String codeRegion){
        return pharmacieRepository.findByRegionContaining(codeRegion);
    }
    
    public Optional<Pharmacie> getPharmacieWithID(int id){
        return pharmacieRepository.findById(id);
    }

    public List<Pharmacie> getAllPharmacies(){
        return pharmacieRepository.findAll();
    }
    
    public Map<String,List<Pharmacie>> getAllPharmaciesWithCodePostale(List<String> codePostales){
        Map<String,List<Pharmacie>> codePostalePharmacie=new HashMap<>();
        for (String code : codePostales) {
            codePostalePharmacie.put(code,getPharmaciesWithCodePostale(code));
        }
        return codePostalePharmacie;
    }
    public List<AuditSignalementResponse> auditByCodePostales(List<String> codePostales){
        Map<String,List<Pharmacie>> codePostaleToPharmacies=new HashMap<>();
        // recuperation pharmacie
        List<AuditSignalementResponse> result= new ArrayList<>();
        for(String codePostale: codePostales){
            // codePostaleToPharmacies.put(codePostale, getPharmaciesWithCodePostale(codePostale));
            for(Pharmacie pharmacie:getPharmaciesWithCodePostale(codePostale)){
                // AuditSignalementResponse auditSignalementResponse=new AuditSignalementResponse();
                //auditSignalementResponse.setCoord(pharmacie.getCoordonneesXY());
            }
        }

        return result;
    }
    // // part admin
    public Set<Integer> getIdPharmacieWithFilter(FilteredSignalementRequest fsr){
        Set<Integer> s=new HashSet<>();
        for (Pharmacie p : pharmacieRepository.findByRegionInOrDepartementInOrCodePostaleIn(fsr.getRegions(),fsr.getDepartements() ,fsr.getCodesPostales())) {
            s.add(p.getId());
        }
        return s;
    }
    // public List<Pharmacie> getWithCodePostale(String codePostale){
    //     return pharmacieRepository.findByCodePostaleStartingWith(codePostale);
    // }


}
