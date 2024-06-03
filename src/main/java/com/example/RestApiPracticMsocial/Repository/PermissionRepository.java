package com.example.RestApiPracticMsocial.Repository;

import com.example.RestApiPracticMsocial.Model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
