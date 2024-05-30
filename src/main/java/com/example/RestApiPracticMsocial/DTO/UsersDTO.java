package com.example.RestApiPracticMsocial.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UsersDTO {
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;
}
