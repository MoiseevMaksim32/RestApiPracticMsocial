package com.example.RestApiPracticMsocial.Repository;

import com.example.RestApiPracticMsocial.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    @Query("Select s from Users s where usersName = ?1")
    public Users findByGetUserUsername(String login);
}
