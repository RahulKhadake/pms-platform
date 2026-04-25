package com.company.pms.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {

    @JsonProperty("message")
    private String message;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("token")
    private String token;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public AuthResponseDTO(String message, boolean success, String token) {
        this.message = message;
        this.success = success;
        this.token = token;


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}