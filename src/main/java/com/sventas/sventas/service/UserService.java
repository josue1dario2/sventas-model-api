package com.sventas.sventas.service;

import com.sventas.sventas.dto.UserDto;

import java.util.List;

public interface UserService {


    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    UserDto findById(String id);

    List<UserDto> findAll();

    List<UserDto> findByNameAndLastName(String name ,String lastName);

    List<UserDto> findBySalary(Double salary);

    List<UserDto> findMaxSalary();

    void delete(String id);

    List<UserDto> findByOrder(String order);
}
