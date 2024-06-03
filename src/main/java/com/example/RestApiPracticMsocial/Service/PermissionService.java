package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.DTO.PermissionDTO;
import com.example.RestApiPracticMsocial.Model.Permission;
import com.example.RestApiPracticMsocial.Repository.PermissionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final RoleService roleService;

    public Permission create(PermissionDTO dto) {
        Permission permission = Permission.builder().roles(roleService.readById(dto.getRolesId())).name(dto.getName()).build();
        log.info("Созданно новое разрешение для " + permission.getRoles().getName());
        return permissionRepository.save(permission);
    }

    public List<Permission> readAll(){
        return permissionRepository.findAll();
    }


    public Permission update(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Permission readById(Long id) {
        return permissionRepository.findById(id).orElseThrow(() ->
                new RuntimeException("В таблицы разрешений нет значения с таким id: " + id));
    }

    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }

}
