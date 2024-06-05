package com.example.RestApiPracticMsocial.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="roles_id", nullable = false)
    private Role roles;
    @Column(name = "users_name", nullable = false, unique = true, length = 100)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "поле username должно содержать только латинские символы")
    private String usersName;
    @Column(name = "password", nullable = false,length = 20)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,20}", message = "поле username должно содержать только латинские символы")
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "поле email написанно не правильно")
    private String email;
    @Column(name = "name", nullable = true)
    private String name;
}
