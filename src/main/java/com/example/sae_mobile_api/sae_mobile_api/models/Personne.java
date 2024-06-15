package com.example.sae_mobile_api.sae_mobile_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(name="Personne",uniqueConstraints = @UniqueConstraint(columnNames = "emailPersonne"))
public class Personne {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPersonne")
    private int id;
    @Column(name = "nomPersonne")
    private String nom;
    @Column(name = "prenomPersonne")
    private String prenom;
    @Column(name = "passwordPersonne")
    private String password;
    @Column(name = "rolePersonne")
    private String role;
    @Column(name = "emailPersonne")
    private String email;

    @Override
    public String toString() {
        return "Personne [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", role=" + role
                + "]";
    }
}
