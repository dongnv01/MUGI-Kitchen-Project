package com.example.springlearn.services;

import com.example.springlearn.dto.UserDto;
import com.example.springlearn.entities.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findByEmail(String email);
    List<UserDto> findAllUser();
}
