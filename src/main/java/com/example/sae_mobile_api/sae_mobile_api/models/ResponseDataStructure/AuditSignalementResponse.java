package com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure;



import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.sae_mobile_api.sae_mobile_api.models.Medicament;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Data;

@Data
public class AuditSignalementResponse {
    public AuditSignalementResponse( Map<String,Map<Long,Integer>> m ){
        // this.idPharmacie=idPharmacie;
        this.dateTomedicamentToNbSignalement=m;
    }
    // private Integer idPharmacie;
    private Map<String,Map<Long,Integer>>  dateTomedicamentToNbSignalement;
            //   date       CIS    NB_REPORT
}
