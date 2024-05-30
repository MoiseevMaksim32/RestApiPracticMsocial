package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.DTO.UsersDTO;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.Repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UsersService {
    // На всякий случай была реализована полностью CRUD система
    private final UsersRepository usersRepository;

    public Users create(UsersDTO dto) {
        Users user = Users.builder().usersName(dto.getUserName()).email(dto.getEmail()).name(dto.getName()).build();
        log.info("Создан новый пользователь");
        return usersRepository.save(user);
    }

    public List<Users> readAll() {
        return usersRepository.findAll();
    }

    public Users update(Users users) {
        return usersRepository.save(users);
    }

    public Users readById(Long id) {
        return usersRepository.findById(id).orElseThrow(() ->
                new RuntimeException("В таблицы пользователей нет значения с таким id: " + id));
    }

    public void delete(Long id) {
        if (!usersRepository.existsById(id))
            throw new RuntimeException("Пользователся с таким id нет");
        usersRepository.deleteById(id);
    }
}
