package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.Model.Role;
import com.example.RestApiPracticMsocial.Repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public List<Role> readAll(){
        return roleRepository.findAll();
    }
    public Role readById(Long id){
        return roleRepository.findById(id).orElseThrow(() ->
                new RuntimeException("В таблицы роли нет значения с таким id: " + id));
    }
}
