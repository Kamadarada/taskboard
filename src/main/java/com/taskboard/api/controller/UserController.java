package com.taskboard.api.controller;


import com.taskboard.api.database.entity.user.UserEntity;
import com.taskboard.api.dto.request.UserRequestDto;
import com.taskboard.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntity createUserEntity(@Valid @RequestBody UserRequestDto userDto){
        return userService.create(userDto);
    }
}
