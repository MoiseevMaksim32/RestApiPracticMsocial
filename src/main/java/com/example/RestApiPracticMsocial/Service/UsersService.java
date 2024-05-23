package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.DTO.UsersDTO;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.Repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    public Users create(UsersDTO dto){
        Users user = Users.builder().usersName(dto.getUserName()).email(dto.getEmail()).name(dto.getName()).build();
        return usersRepository.save(user);
    }

    public List<Users> readAll(){
        return usersRepository.findAll();
    }

    public Users update(Users users){
        return usersRepository.save(users);
    }

    public Users readById(Long id){
        return usersRepository.findById(id).orElseThrow(() ->
                new RuntimeException("В таблицы Roles нет значения с таким: "+id));
    }

    public void delete(Long id){
        usersRepository.deleteById(id);
    }
}
