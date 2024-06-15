package com.example.sae_mobile_api.sae_mobile_api.models;

import lombok.Data;

@Data
public class CustomJsonString {
    private String message;
    public CustomJsonString(String msg){
        this.message=msg;
    }
}
