package com.example.RestApiPracticMsocial.Service;

import com.example.RestApiPracticMsocial.DTO.CreateRefreshTokenDTO;
import com.example.RestApiPracticMsocial.Model.RefreshToken;
import com.example.RestApiPracticMsocial.Repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UsersService usersService;

    public RefreshToken create(CreateRefreshTokenDTO dto) {
        RefreshToken refreshToken = RefreshToken.builder().user(usersService.readById(dto.getId()))
                .token(dto.getToken()).build();
        return refreshTokenRepository.save(refreshToken);
    }

    public List<RefreshToken> readAll() {
        return refreshTokenRepository.findAll();
    }

    public RefreshToken update(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken readById(long id) {
        return refreshTokenRepository.findByGetId(id);
    }

    public void delete(Long id) {
        refreshTokenRepository.deleteById(id);
    }
}
