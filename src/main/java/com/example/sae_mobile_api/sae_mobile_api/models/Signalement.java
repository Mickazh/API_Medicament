package com.example.sae_mobile_api.sae_mobile_api.models;

import java.io.Serializable;
import java.util.Date;

import com.example.sae_mobile_api.sae_mobile_api.models.compositePK.SignalementPK;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

import lombok.Data;
@Entity
@Data

// @Table(name = "signalement")
@Table(name = "signalement", indexes = {
    // @Index(name = "idx_signalement_date", columnList = "dateSignalement"),
    // @Index(name = "idx_signalement_idPharmacie", columnList = "idPharmacie")
})
public class Signalement{
    @EmbeddedId
    private SignalementPK signalementPK_ID;

    @Override
    public String toString(){
        return signalementPK_ID.toString();
    }
}



