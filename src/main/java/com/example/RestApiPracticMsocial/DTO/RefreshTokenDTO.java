package com.example.RestApiPracticMsocial.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RefreshTokenDTO {
    @JsonProperty("refreshToken")
    private String token;
}
