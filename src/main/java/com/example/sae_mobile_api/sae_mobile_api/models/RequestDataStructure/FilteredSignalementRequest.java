package com.example.sae_mobile_api.sae_mobile_api.models.RequestDataStructure;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class FilteredSignalementRequest {
    private List<String> regions;
    private List<String> codesPostales;
    private List<String> departements;
    private List<Long> medicaments;
    private Date debut;
    private Date fin;
}
