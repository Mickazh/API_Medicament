package com.example.sae_mobile_api.sae_mobile_api.models.compositePK;

import java.io.Serializable;
import java.util.Date;

import com.example.sae_mobile_api.sae_mobile_api.models.Medicament;
import com.example.sae_mobile_api.sae_mobile_api.models.Personne;
import com.example.sae_mobile_api.sae_mobile_api.models.Pharmacie;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Embeddable
@Data
public class SignalementPK implements Serializable {
    @ManyToOne
    @JoinColumn(name ="CIP13")
    private Medicament CIP13;

    @ManyToOne
    @JoinColumn(name="idPersonne")
    private Personne idPersonne;
    
    private Date dateSignalement;

    @ManyToOne
    @JoinColumn(name ="idPharmacie")
    private Pharmacie idPharmacie;

    @Override
    public String toString() {
        return "SignalementPK [idPersonne=" + idPersonne + ", CIP13=" + CIP13 + ", dateSignalement=" + dateSignalement
                + ", idPharmacie=" + idPharmacie + "]";
    }
    
}
