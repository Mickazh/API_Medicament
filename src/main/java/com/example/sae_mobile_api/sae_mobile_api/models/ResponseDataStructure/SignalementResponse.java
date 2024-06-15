package com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure;

import java.util.Date;

import lombok.Data;

@Data
public class SignalementResponse {

    private String nomPharmacie;
    private String nomMedicament;
    private Date dateSignalement;
    public SignalementResponse(String nomPharmacie,String nomMedicament,Date d){
        this.nomPharmacie=nomPharmacie;
        this.nomMedicament=nomMedicament;
        this.dateSignalement=d;   
    }
}
