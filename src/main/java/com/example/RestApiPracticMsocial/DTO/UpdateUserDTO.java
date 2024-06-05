package com.example.RestApiPracticMsocial.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @JsonProperty("userName")
    @NotNull
    private String username;
    @JsonProperty("name")
    private String name;
}
