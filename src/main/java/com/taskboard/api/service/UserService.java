package com.taskboard.api.service;

import com.taskboard.api.database.entity.user.UserEntity;
import com.taskboard.api.database.repository.IUserRepository;
import com.taskboard.api.dto.request.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailAndIsVerifiedTrue(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }

    public UserEntity create(UserRequestDto userDto) {
        UserEntity userExists = userRepository.findByEmailAndIsVerifiedTrue(userDto.email()).orElse(null);

        if (userExists != null) {
            throw new RuntimeException("User already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .isVerified(false)
                .token(UUID.randomUUID().toString())
                .tokenExpiration(LocalDateTime.now().plusHours(24))
                .build();

        UserEntity newUser = userRepository.save(userEntity);
        applicationEventPublisher.publishEvent(newUser);

        return newUser;
    }

    public void verify(String token) {
        UserEntity user = userRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (user.getTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        user.setVerified(true);
        user.setToken(null);
        user.setTokenExpiration(null);
        userRepository.save(user);
    }
}
