package com.example.sae_mobile_api.sae_mobile_api.models.RequestDataStructure;

import lombok.Data;

@Data
public class SignalementRequest {
    private int idUser;
    private int idPharmacie;
    private Long CIP13;
    
    // {
    //     "idUser":2,
    //     "idPharmacie":2,
    //     "cip13":"3400949497706"
    // }
}
