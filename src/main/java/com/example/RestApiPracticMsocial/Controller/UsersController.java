package com.example.RestApiPracticMsocial.Controller;

import com.example.RestApiPracticMsocial.DTO.UpdateUserDTO;
import com.example.RestApiPracticMsocial.DTO.UsersDTO;
import com.example.RestApiPracticMsocial.Model.FavoritesMovies;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.Service.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UsersController {
    private final UsersService service;

    @PostMapping
    public ResponseEntity<Users> create(@RequestBody UsersDTO dto){
        Pattern p = Pattern.compile("\\b[A-Za-z]+@[A-Za-z]+\\.[A-Za-z]{2,4}\\b");
        Matcher m = p.matcher(dto.getEmail());
        if (m.find())
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
        log.error("Пользователь неверно указал email");
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<List<Users>> readAll(){
        log.error("Чтение всех пользователей");
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Users> update(@RequestParam("User-Id") Long id,@RequestBody UpdateUserDTO updateUser){
        Users user = service.readById(id);
        user.setUsersName(updateUser.getUsername());
        user.setName(updateUser.getName());
        return new ResponseEntity<>(service.update(user), HttpStatus.OK);
    }

    @DeleteMapping()
    public HttpStatus delete(@RequestParam("User-Id") Long id){
        service.delete(id);
        return HttpStatus.OK;
    }
}
