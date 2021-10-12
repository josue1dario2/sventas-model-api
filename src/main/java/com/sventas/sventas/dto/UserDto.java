package com.sventas.sventas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {

    @Id
    private String id;
    @NotEmpty(message = "Name may not be empty")
    private String name;
    @NotEmpty(message = "Last name may not be empty")
    private String lastName;
    @NotEmpty(message = "The direction cannot be empty")
    private String direction;
    @NotEmpty(message = "Phone number may not be empty")
    private String phone;
    @NotEmpty(message = "The email cannot be empty")
    @Email
    private String email;
    @NotNull
    @Min(0)
    private Double salary;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateOff;

}
