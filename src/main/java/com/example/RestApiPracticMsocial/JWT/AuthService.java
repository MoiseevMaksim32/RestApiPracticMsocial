package com.example.RestApiPracticMsocial.JWT;

import com.example.RestApiPracticMsocial.DTO.CreateRefreshTokenDTO;
import com.example.RestApiPracticMsocial.DTO.LoginDTO;
import com.example.RestApiPracticMsocial.Model.JwtResponse;
import com.example.RestApiPracticMsocial.Model.RefreshToken;
import com.example.RestApiPracticMsocial.Model.Users;
import com.example.RestApiPracticMsocial.Repository.UsersRepository;
import com.example.RestApiPracticMsocial.Service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {
    private final UsersRepository usersRepository;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider provider;
    private final RefreshTokenService refreshTokenService;

    /*Решил не менять логику с Map т.к боялся что-то сломать
     * но теперь refresh токены храняться в базе, да по сути происходит дублирование,
     * но обращение к БД происходит теперь только при использовании методов login и exit */
    @Autowired
    public AuthService(UsersRepository usersRepository, JwtProvider provider, RefreshTokenService refreshTokenService) {
        this.usersRepository = usersRepository;
        this.provider = provider;
        this.refreshTokenService = refreshTokenService;
        List<RefreshToken> list = refreshTokenService.readAll();
        for (var i : list) {
            refreshStorage.put(i.getUser().getUsersName(), i.getToken());
        }
    }

    public JwtResponse login(@NotNull LoginDTO loginDTO) {
        final Users users = usersRepository.findByGetUserUsername(loginDTO.getUserName());
        if (users.getPassword().equals(loginDTO.getPassword())) {
            final String accessToken = provider.generateAccessToken(users);
            final String refreshToken = provider.generateRefreshToken(users);
            if (refreshTokenService.readById(users.getId()) != null) {
                RefreshToken refreshTokenModel = refreshTokenService.readById(users.getId());
                refreshTokenModel.setToken(refreshToken);
                refreshTokenService.update(refreshTokenModel);
            } else {
                refreshTokenService.create(new CreateRefreshTokenDTO(users.getId(), refreshToken));
            }
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

    public String getLogin(@NotNull String accessToken) {
        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }
        System.out.println("\n" + accessToken + "\n");
        if (provider.validateAccessToken(accessToken)) { // Проверка на валидность токена, если ложь то срабатывает исключение на невалидность
            final Claims claims = provider.getAccessClaims(accessToken);
            final String login = claims.getSubject();
            if (refreshStorage.get(login) != null) // Проверка на наличие токена в Map-е, если его нет то считаеться что пользователь не авторизованн
                return login;
            else
                throw new RuntimeException("Неавторизованны");
        }
        throw new RuntimeException("Невалидный JWT токен");
    }

    public void Exit(@NotNull String accessToken) {
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }
        if (provider.validateAccessToken(accessToken)) {
            final Claims claims = provider.getAccessClaims(accessToken);
            final String login = claims.getSubject();
            final Users users = usersRepository.findByGetUserUsername(login);
            RefreshToken refreshToken = refreshTokenService.readById(users.getId());
            refreshTokenService.delete(refreshToken.getId());
            refreshStorage.remove(login);
            return;
        }
        throw new RuntimeException("Невалидный JWT токен");
    }
}
