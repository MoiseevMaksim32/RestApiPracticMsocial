package com.example.RestApiPracticMsocial.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRefreshTokenDTO {
    private long id;
    private String token;
}
