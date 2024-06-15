package com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure;

import java.util.Date;

import lombok.Data;

@Data
public class AggregateSignalementResponse {
    public AggregateSignalementResponse(){
        
    }
    public AggregateSignalementResponse(String nomMedicament, long nbSignalement, Date lastDate) {
        this.nomMedicament = nomMedicament;
        this.nbSignalement = nbSignalement;
        this.lastDateSignalement = lastDate;
    }
    private String nomMedicament;
    private long nbSignalement;
    private Date lastDateSignalement;

}
