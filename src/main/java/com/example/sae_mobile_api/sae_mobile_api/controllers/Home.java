package com.example.sae_mobile_api.sae_mobile_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.sae_mobile_api.sae_mobile_api.models.Personne;
import com.example.sae_mobile_api.sae_mobile_api.models.Pharmacie;
import com.example.sae_mobile_api.sae_mobile_api.models.Signalement;
import com.example.sae_mobile_api.sae_mobile_api.models.RequestDataStructure.FilteredSignalementRequest;
import com.example.sae_mobile_api.sae_mobile_api.repositorys.PharmacieRepository;
import com.example.sae_mobile_api.sae_mobile_api.services.PasswordResetService;
import com.example.sae_mobile_api.sae_mobile_api.services.PharmacieService;
import com.example.sae_mobile_api.sae_mobile_api.services.SignalementService;

import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class Home {
    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PharmacieService pharmacieService;

    @Autowired 
    private SignalementService signalementService;
    @GetMapping("/test")
    public Optional<Pharmacie> test(){
        return pharmacieService.getPharmacieWithID(2);
        
    }
    @GetMapping("/abdzv")
    public List<Pharmacie> getMethodName(@RequestParam("nomPharmacie") String nomPharmacie) {
        return pharmacieService.getPharmaciesWithPartialName(nomPharmacie);
        
    }
    @GetMapping("testString")
    public String getMethodName2() {
        return "gaki";
    }
    
    @GetMapping("/sendResetMail")
    public String sendReset() {
        passwordResetService.sendPasswordResetRequest("abeuhkclefeuh@gmail.com");
        return "zaeddzdz";
    }
    
    @GetMapping("signalementTest")
    public List<Signalement> abcd(@RequestBody FilteredSignalementRequest fsr){
        // http://localhost:8080/signalementTest
        // {
        //     "regions": ["Île-de-France", "Provence-Alpes-Côte d'Azur"],
        //     "codesPostales": ["75001", "13001"],
        //     "departements": ["75", "13"],
        //     "medicaments": ["ANASTROZOLE ACCORD 1 mg. comprimé pelliculé"],
        //     "debut": "2020-05-27T15:30:00.123456Z",
        //     "fin": "2024-05-27T15:30:00.123456Z"
        //   }
        return signalementService.getSignalementWithCISAndDateBetween(fsr);
    }

    @GetMapping("signalementTest2")
    public Set<Integer> ax(@RequestBody FilteredSignalementRequest fsr) {
        return pharmacieService.getIdPharmacieWithFilter(fsr);
        
    }
    




}
