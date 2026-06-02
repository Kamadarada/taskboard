package com.taskboard.api.service;

import com.taskboard.api.database.entity.user.UserEntity;
import com.taskboard.api.database.repository.IUserRepository;
import com.taskboard.api.dto.request.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }

    public UserEntity create(UserRequestDto userDto) {
        UserEntity userExists = userRepository.findByEmail(userDto.email()).orElse(null);

        if (userExists != null) {
            throw new RuntimeException("User already exists");
        }

        UserEntity userEntity = UserEntity.builder().email(userDto.email()).password(passwordEncoder.encode(userDto.password())).build();

        return userRepository.save(userEntity);
    }
}
