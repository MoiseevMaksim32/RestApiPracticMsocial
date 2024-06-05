package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.CommandUserPermissions;
import com.example.RestApiPracticMsocial.DTO.UpdateUserDTO;
import com.example.RestApiPracticMsocial.DTO.UsersDTO;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.Repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UsersService {
    // На всякий случай была реализована полностью CRUD система
    private final UsersRepository usersRepository;
    private final RoleService roleService;
    private final UsersPermissions usersPermissions;

    @CacheEvict("usersReadAll")
    public Users create(UsersDTO dto) {
        Users user = Users.builder().roles(roleService.readById(1L)).usersName(dto.getUserName()).password(dto.getPassword()).email(dto.getEmail()).name(dto.getName()).build();
        log.info("Создан новый пользователь с ролью пользователя");
        return usersRepository.save(user);
    }

    @Cacheable("usersReadAll")
    public Page<Users> readAll(String login, Integer page, Integer sizePage) {
        Users users = usersRepository.findByGetUserUsername(login);
        usersPermissions.isUsersPermission(users, CommandUserPermissions.readUsers);
        return usersRepository.findAll(PageRequest.of(page, sizePage));
    }

    public Users update(String login, UpdateUserDTO updateUserDTO) {
        Users user = usersRepository.findByGetUserUsername(login);
        user.setUsersName(updateUserDTO.getUsername());
        user.setName(updateUserDTO.getName());
        usersPermissions.isUsersPermission(user, CommandUserPermissions.updateMySelfUser);
        return usersRepository.save(user);
    }

    public Users readById(Long id) {
        Users users = usersRepository.findById(id).orElseThrow(() ->
                new RuntimeException("В таблицы пользователей нет значения с таким id: " + id));
        usersPermissions.isUsersPermission(users, CommandUserPermissions.readMySelfUser);
        return users;
    }

    public Users readByLogin(String login) {
        Users users = usersRepository.findByGetUserUsername(login);
        usersPermissions.isUsersPermission(users, CommandUserPermissions.readMySelfUser);
        return users;
    }

    @CacheEvict("usersReadAll")
    public void delete(Long id, String login) {
        Users users = usersRepository.findByGetUserUsername(login);
        usersPermissions.isUsersPermission(users, CommandUserPermissions.deleteUser);
        if (!usersRepository.existsById(id))
            throw new RuntimeException("Пользователся с таким id нет");
        usersRepository.deleteById(id);
    }
}
