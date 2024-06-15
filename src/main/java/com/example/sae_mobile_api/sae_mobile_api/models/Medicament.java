package com.example.sae_mobile_api.sae_mobile_api.models;

import java.util.List;



import com.example.sae_mobile_api.sae_mobile_api.models.compositePK.SignalementPK;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;


@Entity
@Data
@Table(name="Medicaments")
public class Medicament {
    @Id
    @Column(name = "CIP13")
    private long CIP13;

    @Column(name = "CIS")
    private String CIS;

    @Column(name = "Denomination")
    private String denomination;

    @Column(name = "FormePharmaceutique")
    private String formePharmaceutique;

    @Column(name = "VoiesAdministration")
    private String voiesAdministration;

    @Column(name = "StatutAdministratif")
    private String statutAdministratif;

    @Column(name = "procedureAutorisationMarche")
    private String procedureAutorisationMarche;

    @Column(name = "EtatCommercialisation")
    private String etatCommercialisation;

    @Column(name = "DateAMM")
    private String dateAMM;

    @Column(name = "Titulaire")
    private String titulaire;

    @Column(name = "SurveillanceRenforcee")
    private String surveillanceRenforcee;

    @Column(name = "CIP7")
    private String CIP7;

    @Column(name = "LibellePresentation")
    private String libellePresentation;

    @Column(name = "DateCommercialisation")
    private String dateCommercialisation;



    @Column(name = "AggrementCollectivite")
    private String aggrementCollectivite;

    @Column(name = "TauxRemboursement")
    private String tauxRemboursement;

    @Column(name = "prixHT")
    private String prixHT;

    @Column(name = "prixTTC")
    private String prixTTC;

    @Column(name = "montantTaxe")
    private String montantTaxe;
    


    @Override
    public String toString() {
        return "Medicament{" +
                "CIS=" + CIS +
                ", denomination='" + denomination + '\'' +
                ", formePharmaceutique='" + formePharmaceutique + '\'' +
                ", voiesAdministration='" + voiesAdministration + '\'' +
                ", statutAdministratif='" + statutAdministratif + '\'' +
                ", procedureAutorisationMarche='" + procedureAutorisationMarche + '\'' +
                ", etatCommercialisation='" + etatCommercialisation + '\'' +
                ", dateAMM='" + dateAMM + '\'' +
                ", titulaire='" + titulaire + '\'' +
                ", surveillanceRenforcee='" + surveillanceRenforcee + '\'' +
                ", CIP7='" + CIP7 + '\'' +
                ", libellePresentation='" + libellePresentation + '\'' +
                ", dateCommercialisation='" + dateCommercialisation + '\'' +
                ", CIP13='" + CIP13 + '\'' +
                ", aggrementCollectivite='" + aggrementCollectivite + '\'' +
                ", tauxRemboursement='" + tauxRemboursement + '\'' +
                ", prixHT='" + prixHT + '\'' +
                ", prixTTC='" + prixTTC + '\'' +
                ", montantTaxe:'" + montantTaxe + '\'' +
                '}';
    }
}
