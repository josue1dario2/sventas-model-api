package com.sventas.sventas.converter;

import com.sventas.sventas.dto.UserDto;
import com.sventas.sventas.exception.ModelNotFoundException;
import com.sventas.sventas.model.User;
import com.sventas.sventas.repository.UserRepository;
import com.sventas.sventas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserConverter {
    @Autowired
    private UserRepository userRepository;

    public User convertToEntity(UserDto userDto)throws ModelNotFoundException {

        User user = new User();

        if(userDto.getId() == null) {
            String id = generateId();
            user.setId(id);
        }else{
            user.setId(userDto.getId());
        }

        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setDirection(userDto.getDirection());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setSalary(userDto.getSalary());

        return user;

    }

    public UserDto convertToDto(User user) throws ModelNotFoundException{

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setDirection(user.getDirection());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());
        userDto.setSalary(user.getSalary());

        return userDto;

    }
    public String generateId() {
        String id;
        do {
            id = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 15);
        } while (userRepository.existsByIdAndDateOffIsNull(id));
        return id;
    }
}
