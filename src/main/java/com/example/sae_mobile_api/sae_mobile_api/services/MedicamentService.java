package com.example.sae_mobile_api.sae_mobile_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.sae_mobile_api.sae_mobile_api.models.Medicament;
import com.example.sae_mobile_api.sae_mobile_api.repositorys.MedicamentRepository;

@Service
public class MedicamentService {
    @Autowired
    private MedicamentRepository medicamentRepository;

    // public Medicament getMedicamentWithCIS(String CIS){
    //     return medicamentRepository.findByCIS(Integer.parseInt(CIS));
    // }
    // public Medicament getMedicamentWithCIS(int CIS){
    //     return medicamentRepository.findByCIS(CIS);
    // }

    // public Medicament getMedicamentWithCIP7(String CIP7){
    //     return medicamentRepository.findByCIP7(CIP7);
    // }
    // public Medicament getMedicamentWithCIP7(int CIP7){
    //     return medicamentRepository.findByCIP7(String.valueOf(CIP7));
    // }

    public List<Medicament> getMedicamentWithCIP13In(List<Long> cip13){
        return medicamentRepository.findByCIP13In(cip13);
    }
    public Medicament getMedicamentWithCIP13(String CIP13){
        return medicamentRepository.findByCIP13(Long.parseLong(CIP13));
    }
    public Medicament getMedicamentWithCIP13(Long CIP13){
        return medicamentRepository.findByCIP13(CIP13);
    }

    public List<Medicament> getMedicamentsByPartialName(String partialName) {
        return medicamentRepository.findByDenominationContaining(partialName);
    }
    public List<Medicament> getAllMedicaments(){
        return medicamentRepository.findAll();
    }

    public List<Medicament> getMedicamentByNameIn(List<String> noms){
        return medicamentRepository.findByDenominationIn(noms);
    }

    public Page<Medicament> getMedicamentsByPartialName(String partialName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("denomination"));
        // return medicamentRepository.findByDenominationContaining(partialName,
        // pageable);
        return medicamentRepository.findByDenominationStartingWith(partialName, pageable);
    }
}
