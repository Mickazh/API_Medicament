package com.example.sae_mobile_api.sae_mobile_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.sae_mobile_api.sae_mobile_api.models.Medicament;
import com.example.sae_mobile_api.sae_mobile_api.models.Signalement;
import com.example.sae_mobile_api.sae_mobile_api.models.RequestDataStructure.SignalementRequest;
import com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure.AggregateSignalementResponse;
import com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure.SignalementResponse;
import com.example.sae_mobile_api.sae_mobile_api.models.compositePK.SignalementPK;
import com.example.sae_mobile_api.sae_mobile_api.services.MedicamentIntrouvableException;
import com.example.sae_mobile_api.sae_mobile_api.services.MedicamentService;
import com.example.sae_mobile_api.sae_mobile_api.services.SignalementService;
import com.example.sae_mobile_api.sae_mobile_api.services.UtilService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("/signalement")
public class SignalementController {
    @Autowired
    private SignalementService signalementService;

    @PostMapping("/signaler")
    public ResponseEntity<Resp> postMethodName(@RequestBody SignalementRequest signalement) {
        //System.out.println(signalement);
        // Medicament m =medicamentService.getMedicamentWithCIP13(signalement.getCIP13());
        Long cip13=signalement.getCIP13();
        
        try {
            signalementService.signaler(signalement.getIdUser(), cip13, signalement.getIdPharmacie());
        } catch (MedicamentIntrouvableException e) {
            return ResponseEntity.status(HttpStatus.OK).body(new Resp(e.getMessage()));
        }
        return ResponseEntity.ok(new Resp("Signalement effectué !"));
        
        // return signalement;
    }
    @GetMapping("/")
    public void getMethodName(@RequestBody SignalementPK signalement) {
        signalement.setDateSignalement(new Date());
    }
    @GetMapping("/test")
    public String getMethodName() {
        return "abced";
    }
    
    @GetMapping("/getSignalements")
    public List<SignalementResponse> getSignalements(@RequestParam("idPharmacie") int idPharmacie) {
        return signalementService.getSignalements(idPharmacie);
        
    }

    @GetMapping("getSignalementLastWeek")
    public List<AggregateSignalementResponse> getSignalementLastWeek(@RequestParam("idPharmacie") int idPharmacie) {

        return signalementService.getAggregateSignalementFromLastWeek(idPharmacie);
    }
    
    
    
    
}
