package com.sventas.sventas.service;

import com.sventas.sventas.converter.UserConverter;
import com.sventas.sventas.dto.UserDto;
import com.sventas.sventas.exception.ModelNotFoundException;
import com.sventas.sventas.model.User;
import com.sventas.sventas.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserConverter userConverter;
    private final UserRepository userRepository;

    private static final String ACTION_1 = "Error in server";
    private static final String ACTION_2 = "There is no user database";
    private static final String ACTION_3 = "Wrong value";

    @Override
    public UserDto create(UserDto userDto) throws ModelNotFoundException {
        try {

            User user = userConverter.convertToEntity(userDto);
            userRepository.save(user);
            return userConverter.convertToDto(user);

        }catch (ModelNotFoundException m){
            throw m;
        }catch (Exception e){
            throw new ModelNotFoundException(ACTION_1, HttpStatus.NOT_FOUND);
        }
        
    }

    @Override
    public UserDto update(UserDto userDto) throws ModelNotFoundException {
        try{
            User user = userRepository.findByIdAndDateOffIsNull(userDto.getId());
            if(user == null){
                throw new ModelNotFoundException("User not found", HttpStatus.NOT_FOUND);
            }
            user = userConverter.convertToEntity(userDto);
            userRepository.save(user);
            return userConverter.convertToDto(user);

        }catch (ModelNotFoundException m){
            throw m;
        }catch (Exception e){
            throw new ModelNotFoundException(ACTION_1);
        }
    }

    @Override
    public UserDto findById(String id) throws ModelNotFoundException {
        try{
            User user = userRepository.findByIdAndDateOffIsNull(id);
            if(user == null){
                throw new ModelNotFoundException(ACTION_2,HttpStatus.NOT_FOUND);
            }
            return userConverter.convertToDto(user);
        }catch (ModelNotFoundException m){
            throw m;
        }catch (Exception e){
            throw new ModelNotFoundException(ACTION_1);
        }
    }

    @Override
    public List<UserDto> findAll() throws ModelNotFoundException{
        try {
            List<UserDto> userDtos = new ArrayList<>();
            for (User user : userRepository.findAllByDateOffIsNullOrderByLastNameAsc()) {
                userDtos.add(userConverter.convertToDto(user));
            }
            return userDtos;
        }catch (ModelNotFoundException e){
            throw new ModelNotFoundException("Salary not found");
        }catch (Exception e){
            throw new ModelNotFoundException(ACTION_1);
        }

    }

    @Override
    public List<UserDto> findByNameAndLastName(String name, String lastName) throws ModelNotFoundException{
        try{
            List<UserDto> userDtos = new ArrayList<>();
            for(User user : userRepository.findByNameIgnoreCaseAndLastNameIgnoreCaseAndDateOffIsNullOrderByLastNameDesc(name,lastName)){
                userDtos.add(userConverter.convertToDto(user));
            }
            if(userDtos.isEmpty()){
                throw new ModelNotFoundException("User not found", HttpStatus.NOT_FOUND);
            }
            return userDtos;
        }catch (ModelNotFoundException e){
            throw new ModelNotFoundException(ACTION_1);
        }
    }

    @Override
    public List<UserDto> findBySalary(Double salary) throws ModelNotFoundException{
        try{
            List<UserDto> userDtos = new ArrayList<>();
            for(User user : userRepository.findBySalaryAndDateOffIsNullOrderByLastNameAsc(salary)){
                userDtos.add(userConverter.convertToDto(user));
            }
            if(userDtos.isEmpty()){
                throw new ModelNotFoundException("Salary not found", HttpStatus.NOT_FOUND);
            }

            return userDtos;
        }catch (ModelNotFoundException e){
            throw new ModelNotFoundException(ACTION_1);
        }
    }

    @Override
    public List<UserDto> findMaxSalary() {
        try{
            List<UserDto> userDtos = new ArrayList<>();
            for(User user : userRepository.findAllByDateOffIsNullOrderBySalaryDesc()){
                userDtos.add(userConverter.convertToDto(user));
            }
            return userDtos;
        }catch (Exception e){
            throw new ModelNotFoundException(ACTION_1);
        }
    }


    @Override
    public void delete(String id) throws ModelNotFoundException{
        try{
            User user = userRepository.findByIdAndDateOffIsNull(id);
            if(user == null){
                throw new ModelNotFoundException(ACTION_2,HttpStatus.NOT_FOUND);
            }
            user.setDateOff(new Date());
            userRepository.save(user);

        }catch (ModelNotFoundException m){
            throw m;
        }catch (Exception e){
            throw new ModelNotFoundException(ACTION_1);
        }

    }

    @Override
    public List<UserDto> findByOrder(String order) {
        try{
            List<User> users = new ArrayList<>();
            if(order.equalsIgnoreCase("asc")){
                users.addAll(userRepository.findAllByDateOffIsNullOrderByNameAsc());
            }else if(order.equalsIgnoreCase("desc")){
                users.addAll(userRepository.findAllByDateOffIsNullOrderByNameDesc());
            }else{
                throw new ModelNotFoundException(ACTION_3);
            }
            List<UserDto> userDtos = new ArrayList<>();
            for(User user : users){
                userDtos.add(userConverter.convertToDto(user));
            }
            return userDtos;
        }catch (ModelNotFoundException e){
            throw new ModelNotFoundException(e.getMessage());
        }
    }
}
