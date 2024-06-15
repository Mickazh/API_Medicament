package com.example.sae_mobile_api.sae_mobile_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Pharmacie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPharmacie")
    private int id;

    @Column(name = "nomPharmacie")
    private String nomPharmacie;

    @Column(name = "adressePharmacieSimple")
    private String adressePharmacieSimple;
    
    @Column(name = "codePostale")
    private String codePostale;

    @Column(name = "telPharmacie")
    private String telPharmacie;
    
    @Column(name = "accepteCarteVitale")
    private String accepteCarteVitale;

    @Column(name = "adressePharmacieComplete")
    private String adressePharmacieComplete;

    @Column(name = "coordonnees")
    private String coordonneesXY;
    
    @Column(name = "departement")
    private String departement;

    @Column(name = "region")
    private String region;

    @Column(name = "codeCommune")
    private String codeCommune;
}
