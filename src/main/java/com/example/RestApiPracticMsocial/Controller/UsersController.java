package com.example.RestApiPracticMsocial.Controller;

import com.example.RestApiPracticMsocial.DTO.MoviesDTO;
import com.example.RestApiPracticMsocial.DTO.UpdateUserDTO;
import com.example.RestApiPracticMsocial.DTO.UsersDTO;
import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.Service.UsersService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {
    private final UsersService service;
    ObjectMapper mapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<Users> create(@RequestBody String jsonDTO) {
        Users user;
        try {
            UsersDTO usersDTO = mapper.readValue(jsonDTO, UsersDTO.class);
            user = service.create(usersDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (DataIntegrityViolationException er) {
            throw new RuntimeException("Такой пользователься уже существует");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // метод должен быть недоступен пользователю, т.к используеться для проверки правильности работы,
    // поэтому пагенацию не делал
    @GetMapping
    public ResponseEntity<Page<Users>> readAll(@RequestParam("Login") String login,@RequestParam(name = "page") Integer page, @RequestParam(name = "page_size", defaultValue = "15", required = false) Integer pageSize){
        return new ResponseEntity<>(service.readAll(login,page,pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> readById(@PathVariable Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Object> update(@RequestParam("User-Id") Long id, @RequestBody UpdateUserDTO updateUser) {
        Users user = service.readById(id);
        user.setUsersName(updateUser.getUsername());
        user.setName(updateUser.getName());
        return new ResponseEntity<>(service.update(user), HttpStatus.OK);
    }

    @DeleteMapping()
    public HttpStatus delete(@RequestParam("User-Id") Long id,@RequestParam("Login") String login) {
        service.delete(id,login);
        return HttpStatus.OK;
    }
}
