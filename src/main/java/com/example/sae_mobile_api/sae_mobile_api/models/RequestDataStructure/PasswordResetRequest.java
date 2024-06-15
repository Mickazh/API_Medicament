package com.example.sae_mobile_api.sae_mobile_api.models.RequestDataStructure;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;
    private String newPassword;
    private String tokenOriginale;
    private String code6;
}
