package com.example.sae_mobile_api.sae_mobile_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sae_mobile_api.sae_mobile_api.models.Medicament;
import com.example.sae_mobile_api.sae_mobile_api.services.MedicamentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/medicaments")
public class MedicamentController {
    @Autowired
    private MedicamentService medicamentService;

    @GetMapping("test")
    public String getMethodName() {
        return "d,zipad";
    }
    
    @GetMapping("/searchWithCIP13")
    public Medicament getMedicamentCIP13(@RequestParam("CIP13") String CIP13) {
        return medicamentService.getMedicamentWithCIP13(CIP13);
    }
    // @GetMapping("/searchWithCIP7")
    // public Medicament getMedicamentCIP7(@RequestParam("CIP7") String CIP7) {
    //     return medicamentService.getMedicamentWithCIP7(CIP7);
    // }
    @GetMapping("/searchWithCIS")
    public Medicament getMedicamentCIS(@RequestParam("CIS") String CIS) {
        return medicamentService.getMedicamentWithCIP13(CIS);
    }
    @GetMapping("/searchWithPName")
    public Page<Medicament> getMedicamentWithPartialName(   @RequestParam("name") String name,
                                                            @RequestParam(defaultValue = "30") Integer size,
                                                            @RequestParam(defaultValue = "0") Integer page) {
        return medicamentService.getMedicamentsByPartialName(name, page, size);
    }

    @GetMapping("/searchWithName")
    public List<Medicament> getMedicamentWithPartialName(@RequestParam("name") String name) {
        return medicamentService.getMedicamentsByPartialName(name);
    }
    @GetMapping("/allMedicaments")
    public List<Medicament> getAllMedicaments() {
        return medicamentService.getAllMedicaments();
    }
    
    
}
