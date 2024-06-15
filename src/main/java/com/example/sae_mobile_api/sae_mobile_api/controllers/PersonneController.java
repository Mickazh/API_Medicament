package com.example.sae_mobile_api.sae_mobile_api.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sae_mobile_api.sae_mobile_api.models.Personne;
import com.example.sae_mobile_api.sae_mobile_api.repositorys.PersonneRepository;
@RestController
@RequestMapping("/personne")
public abstract class PersonneController extends AuthController{
    @Autowired
    private PersonneRepository personneRepository;

    @GetMapping("/findById")
    public ResponseEntity<Personne> getByID(@RequestParam("idPersonne") int i ){
        // return null si il ne trouve rien 

        //localhost:8080/admin/findById?idPersonne=3
        //localhost:8080/user/findById?idPersonne=6
        Optional<Personne> op = personneRepository.findById(i);    
        if (op.isPresent()) {
            Personne p=op.get();

            if(p.getRole().equals(this.getRole())){    
                p.setPassword(null);
                p.setRole(null);
                return new ResponseEntity<>(p,HttpStatus.OK);
            }
            
        }
        return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
    }

    
}
