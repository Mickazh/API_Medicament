package com.example.sae_mobile_api.sae_mobile_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

public class UtilService {
    public static int parseCIP(String cip13){
        return Integer.parseInt(cip13.substring(5));
    }
    public static List<Integer> parseMutlipleCIP(List<String> cip13){
            return cip13.stream()
                .map(c -> parseCIP(c))
                .collect(Collectors.toList());
    }
}
