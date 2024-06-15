package com.example.sae_mobile_api.sae_mobile_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.sae_mobile_api.sae_mobile_api.models.Pharmacie;
import com.example.sae_mobile_api.sae_mobile_api.models.Role;
import com.example.sae_mobile_api.sae_mobile_api.models.Signalement;
import com.example.sae_mobile_api.sae_mobile_api.models.RequestDataStructure.FilteredSignalementRequest;
import com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure.AuditSignalementResponse;
import com.example.sae_mobile_api.sae_mobile_api.services.MedicamentService;
import com.example.sae_mobile_api.sae_mobile_api.services.PharmacieService;
import com.example.sae_mobile_api.sae_mobile_api.services.SignalementService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;





@RestController
@RequestMapping("/admin")
public class AdminController extends PersonneController{
    @Autowired
    private PharmacieService pharmacieService;
    @Autowired
    private SignalementService signalementService;

    private static SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");




    @PostMapping("/admin_test")
    public Map<Integer,Map<Long,Integer>> getSignalementsFromFilter(@RequestBody FilteredSignalementRequest fsr) {
        
        Set<Integer> idPharmacies=pharmacieService.getIdPharmacieWithFilter(fsr);
        List<Signalement> signalements=signalementService.getSignalementWithCISAndDateBetween(fsr);
        
        Map<Integer,Map<Long,Integer>> idPharmacieToMap=new HashMap<>();
        //  idPhar     // CIS   nbSignalement
        for (Signalement s : signalements) {
            int idPharmacieFromSignalement=s.getSignalementPK_ID().getIdPharmacie().getId();
            if(idPharmacies.contains(idPharmacieFromSignalement)){
                idPharmacieToMap.putIfAbsent(idPharmacieFromSignalement,new HashMap<>());
                Map<Long,Integer> medicamentToNbSignalement=idPharmacieToMap.get(idPharmacieFromSignalement);
                long cip=s.getSignalementPK_ID().getCIP13().getCIP13();
                medicamentToNbSignalement.putIfAbsent(cip,0);
                
                medicamentToNbSignalement.put(cip,medicamentToNbSignalement.get(cip)+1);

          }  
        }
        return idPharmacieToMap;
    }
    @PostMapping("/getSignalementsFromFilter")
    public Map<Integer,AuditSignalementResponse> test(@RequestBody FilteredSignalementRequest fsr) {
        long startTime = System.currentTimeMillis();

        Set<Integer> idPharmacies=pharmacieService.getIdPharmacieWithFilter(fsr);
        

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("fetch pharmacie " + duration + "ms");

        startTime = System.currentTimeMillis();

        List<Signalement> signalements=signalementService.getSignalementWithCISAndDateBetween(fsr);
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("fetch signalement : " + duration + " ms");

        
        startTime = System.currentTimeMillis();

        Map<Integer,AuditSignalementResponse> res=new HashMap<>();

        for (Signalement s : signalements){
            int idPharmacieFromSignalement=s.getSignalementPK_ID().getIdPharmacie().getId();
            if(idPharmacies.contains(idPharmacieFromSignalement)){
                res.putIfAbsent(idPharmacieFromSignalement, new AuditSignalementResponse(new HashMap<>(200)));
                Map<String,Map<Long,Integer>> auditMap=res.get(idPharmacieFromSignalement).getDateTomedicamentToNbSignalement();
                Date dateSignalement=s.getSignalementPK_ID().getDateSignalement();
                String dateSignalementStr=sdf.format(dateSignalement);
                auditMap.putIfAbsent(dateSignalementStr, new HashMap<>());
                Map<Long,Integer> mapCipToNbReport=auditMap.get(dateSignalementStr);
                Long cip=s.getSignalementPK_ID().getCIP13().getCIP13();

                mapCipToNbReport.putIfAbsent(cip,0);
                mapCipToNbReport.put(cip,mapCipToNbReport.get(cip)+1);


            }  
        }
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("proccess time : " + duration + " ms");

        return res;
    }



    // {
    //     "departements" : ["paris"],
        
    //     "medicaments": ["3400955473350","3400931923138","3400926752804","3400958121975"],
    //             "debut": "2024-01-01T15:30:00.123456Z",
    //             "fin": "2024-12-31T15:30:00.123456Z"
    //           }
    @Override
    protected String getRole() {
        // TODO Auto-generated method stub
        return Role.ADMIN.name();
    }

    
}
