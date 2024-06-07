package com.example.RestApiPracticMsocial.Repository;

import com.example.RestApiPracticMsocial.Model.RefreshToken;
import com.example.RestApiPracticMsocial.Model.Users;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Ref;

@Registered
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("Select s from RefreshToken s where user.id = ?1")
    public RefreshToken findByGetId(long id);
}
