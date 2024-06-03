package com.example.RestApiPracticMsocial.Repository;

import com.example.RestApiPracticMsocial.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
