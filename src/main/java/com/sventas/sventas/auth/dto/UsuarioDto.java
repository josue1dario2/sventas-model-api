package com.sventas.sventas.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    //@Email(message = "Username must be an email")
    private String username;

    //@Size(min = 8)
    private String password;
}
