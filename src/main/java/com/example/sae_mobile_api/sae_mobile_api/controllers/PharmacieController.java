package com.example.sae_mobile_api.sae_mobile_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sae_mobile_api.sae_mobile_api.models.Pharmacie;
import com.example.sae_mobile_api.sae_mobile_api.services.PharmacieService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/pharmacies")
public class PharmacieController {
    @Autowired
    private PharmacieService pharmacieService;

    @GetMapping("/searchPharmarciesWithName")
    public List<Pharmacie> getPharmacieWithName(@RequestParam("nomPharmacie") String nom) {
        //http://localhost:8080/pharmacies/searchPharmarciesWithName?nomPharmacie=Alesia
        return pharmacieService.getPharmaciesWithPartialName(nom);
    }

    @GetMapping("/searchPharmaciesWithCodePostale")
    public List<Pharmacie> getPharmacieWithCodePostale(@RequestParam("codePostale") String codePostale) {
        return pharmacieService.getPharmaciesWithCodePostale(codePostale);
    }
    
    @GetMapping("/searchPharmaciesWithCodeRegion")
    public List<Pharmacie> getPharmacieWithCodeRegion(@RequestParam("codeRegion") String codeRegion) {
        return pharmacieService.getPharmaciesWithCodeRegion(codeRegion);
    }
    @GetMapping("/allPharmacies")
    public List<Pharmacie> getAllPharmacies	() {
        System.out.println(pharmacieService.getAllPharmacies().size());
        return pharmacieService.getAllPharmacies();
    }

}
