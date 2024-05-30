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
            System.out.println(usersDTO);
            if (usersDTO.getUserName() == null || usersDTO.getEmail() == null) {
                throw new RuntimeException("Пользователь ввёл неверные данные пользователся");
            }
            Pattern p = Pattern.compile("\\b[A-Za-z]+@[A-Za-z]+\\.[A-Za-z]{2,4}\\b");
            Matcher m = p.matcher(usersDTO.getEmail());
            if (!m.find()) {
                throw new RuntimeException("Пользователь неверно указал email");
            }
            Pattern pat = Pattern.compile("^[A-Za-z]");
            Matcher mat = pat.matcher(usersDTO.getUserName());
            if (!mat.find()) {
                throw new RuntimeException("Пользователь неверно указал username");
            }
            user = service.create(usersDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (DataIntegrityViolationException er) {
            throw new RuntimeException("Такой пользователься уже существует");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<Users>> readAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
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
    public HttpStatus delete(@RequestParam("User-Id") Long id) {
        service.delete(id);
        return HttpStatus.OK;
    }
}
