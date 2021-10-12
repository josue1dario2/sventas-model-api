package com.sventas.sventas.controller;

import com.sventas.sventas.dto.UserDto;
import com.sventas.sventas.exception.ModelNotFoundException;
import com.sventas.sventas.service.UserService;
import com.sventas.sventas.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    private static final String ERROR = "error";
    private static final String ERRORS = "errors";

    @GetMapping
    public ResponseEntity<?>findAll(){
        try{


            List<UserDto> userDtos = userService.findAll();
            return new ResponseEntity<>(userDtos,OK);
        }catch (ModelNotFoundException m){
            Map<String,Object> response = new HashMap<>();
            response.put(ERROR,m.getMessage());
            return new ResponseEntity<>(response,INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(path = "/order")
    public ResponseEntity<?>findOrder(@RequestParam(value = "order")String order){
        try{
            return ResponseEntity.status(OK).body(userService.findByOrder(order));

        }catch (ModelNotFoundException m){
            Map<String,Object> response = new HashMap<>();
            response.put(ERROR,m.getMessage());
            return new ResponseEntity<>(response,INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserDto userDto, BindingResult result){
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }

            response.put(ERRORS, errors);
            return new ResponseEntity<>(response, BAD_REQUEST);
        }

        try{
            userService.create(userDto);
            return new ResponseEntity<>(userDto,CREATED);
        }catch (ModelNotFoundException m){
            response.put(ERROR,m.getMessage());
            return new ResponseEntity<>(response,m.getStatus());
        }

    }
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UserDto userDto,  BindingResult result){

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }

            response.put(ERRORS, errors);
            return new ResponseEntity(response, BAD_REQUEST);
        }
        try{
            userService.update(userDto);
            return new ResponseEntity<>(userDto,OK);
        }catch (ModelNotFoundException m){
            response.put(ERROR,m.getMessage());
            return new ResponseEntity<>(response,m.getStatus());
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        try{
            UserDto userDto = userService.findById(id);
            return new ResponseEntity<>(userDto,OK);
        }catch (ModelNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put(ERROR, e.getMessage());
            return new ResponseEntity<>(response,INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/salary/{salary}")
    public ResponseEntity<?> findBySalary(@PathVariable Double salary){
        try{
            List<UserDto> userDtos = userService.findBySalary(salary);
            return new ResponseEntity<>(userDtos,OK);
        }catch (ModelNotFoundException e){
            Map<String, Object> response = new HashMap<>();
            response.put(ERROR, e.getMessage());
            return new ResponseEntity<>(response,INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{name}/{lastName}")
    public ResponseEntity<?> findByNameAndLastName(@PathVariable String name,@PathVariable String lastName){
        try{
            List<UserDto> userDtos = userService.findByNameAndLastName(name,lastName);
            return new ResponseEntity<>(userDtos,OK);
        }catch (ModelNotFoundException e){
            Map<String, Object> response = new HashMap<>();
            response.put(ERROR, e.getMessage());
            return new ResponseEntity<>(response,INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/findAllSalary")
    public ResponseEntity<?> findAllSalary(){
        try{
            List<UserDto> userDtos = userService.findMaxSalary();
            return new ResponseEntity<>(userDtos,OK);
         }catch (ModelNotFoundException e){
            Map<String, Object> response = new HashMap<>();
            response.put(ERROR, e.getMessage());
            return new ResponseEntity<>(response,INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        try{
            userService.delete(id);
            return new ResponseEntity<>(OK);
        }catch (ModelNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put(ERROR, e.getMessage());
            return new ResponseEntity(response, e.getStatus());
        }

    }

}
