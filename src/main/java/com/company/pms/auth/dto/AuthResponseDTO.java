package com.company.pms.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {

    @JsonProperty("message")
    private String message;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("token")
    private String token;

    public AuthResponseDTO(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public AuthResponseDTO(String message, boolean success, String token) {
        this.message = message;
        this.success = success;
        this.token = token;
    }
}