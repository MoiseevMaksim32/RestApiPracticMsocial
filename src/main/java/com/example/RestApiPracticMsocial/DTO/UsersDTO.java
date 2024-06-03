package com.example.RestApiPracticMsocial.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsersDTO {
    @JsonProperty("userName")
    @NotNull
    private String userName;
    @JsonProperty("email")
    @NotNull
    private String email;
    @JsonProperty("name")
    private String name;
}
