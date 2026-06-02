package com.taskboard.api.controller;


import com.taskboard.api.config.TokenProvider;
import com.taskboard.api.database.entity.user.UserEntity;
import com.taskboard.api.database.repository.IUserRepository;
import com.taskboard.api.dto.request.AuthRequestDto;
import com.taskboard.api.dto.request.TokenRequestDto;
import com.taskboard.api.dto.response.TokenResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final IUserRepository userRepository;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto authenticate(@Valid @RequestBody AuthRequestDto authDto){
        var authToken = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        var authentication = authenticationManager.authenticate(authToken);
        String token = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        return new TokenResponseDto(token, refreshToken);
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto refreshToken(@Valid @RequestBody TokenRequestDto tokenDto){
        var refreshToken = tokenDto.refreshToken();
        String userEmail = tokenProvider.verifyToken(refreshToken);

        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow();

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        String token = tokenProvider.generateToken(authentication);
        String newRefreshToken = tokenProvider.generateRefreshToken(authentication);

        return new TokenResponseDto(token, newRefreshToken);
    }
}
