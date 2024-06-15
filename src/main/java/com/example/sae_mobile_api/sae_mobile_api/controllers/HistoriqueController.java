package com.example.sae_mobile_api.sae_mobile_api.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sae_mobile_api.sae_mobile_api.models.Signalement;
import com.example.sae_mobile_api.sae_mobile_api.repositorys.SignalementRepository;
import com.example.sae_mobile_api.sae_mobile_api.services.SignalementService;

@RestController
@RequestMapping("/historique")
public class HistoriqueController {
    @Autowired
    private SignalementService signalementService;

    @GetMapping("/test")
    public String getTest() {
        // return signalementController.getMethodName();
        return "";
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Signalement>> getSignalementsFromUser(@RequestParam("idUser") int idUser,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(signalementService.getSignalementsFromUser(idUser, page, size));
    }
}
