package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.CommandUserPermissions;
import com.example.RestApiPracticMsocial.Model.Permission;
import com.example.RestApiPracticMsocial.Model.Users;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersPermissions {

    private final PermissionService permissionService;
    @Autowired
    public UsersPermissions(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public boolean isUsersPermission(Users users, CommandUserPermissions permissionName){
        if(users.getRoles().getName().equals("Администратор")){
            return true;
        }
        List<Permission> permissionList = permissionService.readAll();
        for(var i: permissionList){
            if(i.getRoles().getName().equals(users.getRoles().getName()) && i.getName().equals(permissionName.toString())){
                return true;
            }
        }
        return false;
    }
}
