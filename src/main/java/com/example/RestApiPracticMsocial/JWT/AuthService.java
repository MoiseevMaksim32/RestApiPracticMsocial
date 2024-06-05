package com.example.RestApiPracticMsocial.JWT;

import com.example.RestApiPracticMsocial.DTO.LoginDTO;
import com.example.RestApiPracticMsocial.Model.JwtResponse;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.Repository.UsersRepository;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UsersRepository usersRepository;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider provider;

    @Autowired
    public AuthService(UsersRepository usersRepository, JwtProvider provider) {
        this.usersRepository = usersRepository;
        this.provider = provider;
    }

    public JwtResponse login(@NotNull LoginDTO loginDTO) {
        final Users users = usersRepository.findByGetUserUsername(loginDTO.getUserName());
        if (users.getPassword().equals(loginDTO.getPassword())) {
            final String accessToken = provider.generateAccessToken(users);
            final String refreshToken = provider.generateRefreshToken(users);
            refreshStorage.put(users.getUsersName(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new RuntimeException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NotNull String refreshToken) {
        if (provider.validateRefreshToken(refreshToken)) {
            final Claims claims = provider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Users user = usersRepository.findByGetUserUsername(login);
                final String accessToken = provider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NotNull String refreshToken) {
        if (provider.validateRefreshToken(refreshToken)) {
            final Claims claims = provider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Users user = usersRepository.findByGetUserUsername(login);
                final String accessToken = provider.generateAccessToken(user);
                final String newRefreshToken = provider.generateRefreshToken(user);
                refreshStorage.put(user.getUsersName(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new RuntimeException("Невалидный JWT токен");
    }

    public String getLogin(@NotNull String accessToken) {
        if (provider.validateAccessToken(accessToken)) {
            final Claims claims = provider.getAccessClaims(accessToken);
            final String login = claims.getSubject();
            if (refreshStorage.get(login) != null)
                return login;
            else
                throw new RuntimeException("Неавторизованны");
        }
        throw new RuntimeException("Невалидный JWT токен");
    }

    public String Exit(@NotNull String accessToken) {
        if (provider.validateAccessToken(accessToken)) {
            final Claims claims = provider.getAccessClaims(accessToken);
            final String login = claims.getSubject();
            return refreshStorage.remove(login);
        }
        throw new RuntimeException("Невалидный JWT токен");
    }
}
