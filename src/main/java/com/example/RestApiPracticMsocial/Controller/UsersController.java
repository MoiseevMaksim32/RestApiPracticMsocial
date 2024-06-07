package com.example.RestApiPracticMsocial.Controller;

import com.example.RestApiPracticMsocial.DTO.*;
import com.example.RestApiPracticMsocial.Model.JwtResponse;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.JWT.AuthService;
import com.example.RestApiPracticMsocial.Service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {
    private final UsersService service;
    ObjectMapper mapper = new ObjectMapper();
    private final AuthService authService;

    /*Я решил не идти путём создания собственной аннотаци  с вызывом ArgumentResolver
     * более понятным для меня способом была реализация представленная в пакете прокета JWT,
     * делал первый раз, если есть замечания обязательно пишите*/

    @PostMapping
    public ResponseEntity<JwtResponse> create(@RequestBody UsersDTO usersDTO) {
        Users user;
        try {

           // UsersDTO usersDTO = mapper.readValue(jsonDTO, UsersDTO.class);
            user = service.create(usersDTO);
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserName(user.getUsersName());
            loginDTO.setPassword(user.getPassword());
            final JwtResponse tokens = authService.login(loginDTO);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        } catch (DataIntegrityViolationException er) {
            throw new RuntimeException("Такой пользователься уже существует");
        } /*catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }*/
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDTO loginDTO) {
        final JwtResponse token = authService.login(loginDTO);
        return ResponseEntity.ok(token);
    }

    @PostMapping("exit")
    public ResponseEntity<String> exit(@RequestHeader("Authorization") String token) {
        authService.Exit(token);
        return ResponseEntity.ok("Вы вышли");
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshTokenDTO tokenDTO) {
        final JwtResponse newTokens = authService.getAccessToken(tokenDTO.getToken());
        return ResponseEntity.ok(newTokens);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Users>> readAll(@RequestHeader("Authorization") String token, @RequestParam(name = "page") Integer page, @RequestParam(name = "page_size", defaultValue = "15", required = false) Integer pageSize) {
        String login = authService.getLogin(token);
        return new ResponseEntity<>(service.readAll(login, page, pageSize), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Users> readById(@RequestHeader("Authorization") String token) {
        String login = authService.getLogin(token);
        return new ResponseEntity<>(service.readByLogin(login), HttpStatus.OK);
    }

    /*Пользователь может менять свои данные, для этого он должен
     * передать UpdateUserDTO содержащий логин и имя, из условия он может менять только их */
    @PutMapping()
    public ResponseEntity<Object> update(@RequestHeader("Authorization") String token, @RequestBody UpdateUserDTO updateUser) {
        String login = authService.getLogin(token);
        return new ResponseEntity<>(service.update(login, updateUser), HttpStatus.OK);
    }

    // Админ всё равно должен ввести id для удаления пользователя
    @DeleteMapping()
    public HttpStatus delete(@RequestParam("User-Id") Long id, @RequestHeader("Authorization") String token) {
        String login = authService.getLogin(token);
        service.delete(id, login);
        return HttpStatus.OK;
    }
}
