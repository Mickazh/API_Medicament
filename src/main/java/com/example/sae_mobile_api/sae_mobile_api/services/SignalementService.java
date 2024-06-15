package com.example.sae_mobile_api.sae_mobile_api.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.sae_mobile_api.sae_mobile_api.models.Medicament;
import com.example.sae_mobile_api.sae_mobile_api.models.Personne;
import com.example.sae_mobile_api.sae_mobile_api.models.Pharmacie;
import com.example.sae_mobile_api.sae_mobile_api.models.Signalement;
import com.example.sae_mobile_api.sae_mobile_api.models.RequestDataStructure.FilteredSignalementRequest;
import com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure.AggregateSignalementResponse;
import com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure.SignalementResponse;
import com.example.sae_mobile_api.sae_mobile_api.models.compositePK.SignalementPK;
import com.example.sae_mobile_api.sae_mobile_api.repositorys.PersonneRepository;
import com.example.sae_mobile_api.sae_mobile_api.repositorys.SignalementRepository;

@Service
public class SignalementService {
    @Autowired
    private MedicamentService medicamentService;
    @Autowired
    private PersonneRepository personneRepository;
    @Autowired
    private PharmacieService pharmacieService;
    @Autowired
    private SignalementRepository signalementRepository;
    
    public void signaler(int idPersonne,Long CIP13,int idPharmacie) throws MedicamentIntrouvableException{
        Optional<Personne> personne=personneRepository.findById(idPersonne);
        
        Medicament medicament=medicamentService.getMedicamentWithCIP13(CIP13);
        if (medicament == null) {
            System.out.println("introuvable");
            throw new MedicamentIntrouvableException("CIP13 incorrect");
        }
        Optional<Pharmacie> pharmacie=pharmacieService.getPharmacieWithID(idPharmacie);

        Signalement s= new Signalement();
        SignalementPK sPK=new SignalementPK();
        sPK.setCIP13(medicament);
        sPK.setDateSignalement(new Date());
        sPK.setIdPersonne(personne.get());
        sPK.setIdPharmacie(pharmacie.get());
        s.setSignalementPK_ID(sPK);// revoir ici avec des isPresent pour la vérif.

        signalementRepository.save(s);

        System.out.println("succes");
    }

    public List<SignalementResponse> getSignalements(int idPharmacie){
        List<SignalementResponse> listSignalementResponses=new ArrayList<>();
        
        Optional<Pharmacie> pharmacie=pharmacieService.getPharmacieWithID(idPharmacie);
        
        if (pharmacie.isPresent()) {
            List<Signalement> signalements=signalementRepository.findAllByIdPharmacie( (long) pharmacie.get().getId());
            for(Signalement s : signalements){
                SignalementPK sPK=s.getSignalementPK_ID();
                Medicament m=sPK.getCIP13();
                Pharmacie ph=sPK.getIdPharmacie();
                Date d=sPK.getDateSignalement();
                listSignalementResponses.add(new SignalementResponse(ph.getNomPharmacie(),m.getDenomination(),d));
            }
        }

        return listSignalementResponses;
    } 
    public Map<String,List<Signalement>> getSignalementsByCodePostales(List<String> codePostales){
        Map<String,List<Pharmacie>> mapCodePostaleToPharmacies=pharmacieService.getAllPharmaciesWithCodePostale(codePostales);
        Map<String,List<Signalement>> mapCodePostaleToSignalement=new HashMap<>();
        for (String codePostale : codePostales) {
            for (Pharmacie p :mapCodePostaleToPharmacies.get(codePostale)) {
                if(!mapCodePostaleToSignalement.containsKey(codePostale)){
                    mapCodePostaleToSignalement.put(codePostale, signalementRepository.findAllByIdPharmacie((long) p.getId()));
                }
                else{
                    List<Signalement> initialList=mapCodePostaleToSignalement.get(codePostale);
                    mapCodePostaleToSignalement.put(codePostale, mergeSignalement(initialList, signalementRepository.findAllByIdPharmacie((long) p.getId())));
                }
                
                
            }
        }

        // System.err.println(signalementRepository.findAllByIdPharmacie((long)1534));
        return mapCodePostaleToSignalement;
    }

    private List<Signalement> mergeSignalement(List<Signalement> l1,List<Signalement> l2){
        List<Signalement> result=new ArrayList<>(l1.size()+l2.size());
        result.addAll(l1);
        result.addAll(l2);
        return result;
    }
    public List<Signalement> getAllSignalement(){
        return null;
    }

    public Page<Signalement> getSignalementsFromUser(int idUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return signalementRepository.findAllByIdPersonneWithCustomOrder(idUser, pageable);
    }

    public List<AggregateSignalementResponse> getAggregateSignalementFromLastWeek(int idPharmacie){
        // pour suyi
        // http://localhost:8080/signalement/getSignalementLastWeek?idPharmacie=800

        //les dates de la bd vont jusqua fin 2024 c'est donc normal d'avoir des date au dela d'ajd, il faut que je regenerer un dataset signalement
        // retour : [
        // 	{
        // 		"nomMedicament": "SMECTA 3 g ORANGE-VANILLE, poudre pour suspension buvable en sachet",
        // 		"nbSignalement": 139,
        // 		"lastDateSignalement": "2024-11-14T23:43:39.142+00:00"
        // 	},
        // 	{
        // 		"nomMedicament": "OMEPRAZOLE TEVA CONSEIL 20 mg, gélule gastro-résistante",
        // 		"nbSignalement": 279,
        // 		"lastDateSignalement": "2024-09-06T23:57:50.384+00:00"
        // 	},
        // 	{
        // 		"nomMedicament": "DAFALGAN CODEINE, comprimé pelliculé",
        // 		"nbSignalement": 102,
        // 		"lastDateSignalement": "2024-11-02T23:44:56.339+00:00"
        // 	},
        // 	{
        // 		"nomMedicament": "DOLIPRANE 500 mg, comprimé",
        // 		"nbSignalement": 295,
        // 		"lastDateSignalement": "2024-08-03T23:58:20.783+00:00"
        // 	}
        // ]
        List<AggregateSignalementResponse> lst=new ArrayList<>();
        Date lastWeek=getLastWeekDate();
        List<Signalement>lstSignalement= signalementRepository.findSignalementFromLastWeek(idPharmacie,lastWeek);
        Map<Long,AggregateSignalementResponse> map=new HashMap<>();
        Set<Long> setCIP13= new HashSet<>();
        for (Signalement s : lstSignalement){
            long cip13=s.getSignalementPK_ID().getCIP13().getCIP13();
            map.putIfAbsent(cip13, new AggregateSignalementResponse());
            setCIP13.add(cip13);
            AggregateSignalementResponse aggregate=map.get(cip13);
            aggregate.setNbSignalement(aggregate.getNbSignalement()+1);

            Date oldAggregateDate=aggregate.getLastDateSignalement();
            Date newAggragateDate=s.getSignalementPK_ID().getDateSignalement();
            if(oldAggregateDate==null || oldAggregateDate.before(newAggragateDate))
                aggregate.setLastDateSignalement(newAggragateDate);
        }
        medicamentService.getMedicamentWithCIP13In(new ArrayList<>(setCIP13));

        for (Long cip13 : map.keySet()) {
            String nomMedicament=medicamentService.getMedicamentWithCIP13(cip13).getDenomination();
            AggregateSignalementResponse a= map.get(cip13);
            a.setNomMedicament(nomMedicament);
            lst.add(a);
        }
           //cip nbSignalement
        return lst;
    }

    private Date getLastWeekDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        return calendar.getTime();
    }

    //part Admin

    // public List<Signalement> getSignalementWithCISAndDateBetween(FilteredSignalementRequest fsr){
    //     List<Integer> cis=new LinkedList<>();
    //     for (Medicament m : medicamentService.getMedicamentWithCIP13In(fsr.getMedicaments())) {
    //         cis.add(m.getCIS());
    //     }
    //     return signalementRepository.findAllByMedicamentAndDateBetween(cis, fsr.getDebut(), fsr.getFin());
    // }

    public List<Signalement> getSignalementWithCISAndDateBetween(FilteredSignalementRequest fsr) {
    List<Long> cis = medicamentService.getMedicamentWithCIP13In(fsr.getMedicaments()).stream()
                                         .map(Medicament::getCIP13)
                                         .collect(Collectors.toList());
    
    return signalementRepository.findAllByMedicamentAndDateBetween(cis, fsr.getDebut(), fsr.getFin());
}
}
