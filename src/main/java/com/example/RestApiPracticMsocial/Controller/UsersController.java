package com.example.RestApiPracticMsocial.Controller;

import com.example.RestApiPracticMsocial.DTO.UsersDTO;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.Service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {
    private final UsersService service;

    @PostMapping
    public ResponseEntity<Users> create(@RequestBody UsersDTO dto){
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Users>> readAll(){
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Users> readById(@PathVariable Long id){
        return new ResponseEntity<>(service.readById(id),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Users> update(@RequestBody Users user){
        return new ResponseEntity<>(service.update(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        service.delete(id);
        return HttpStatus.OK;
    }
}
