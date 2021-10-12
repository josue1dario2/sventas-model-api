package com.sventas.sventas.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class User {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String direction;
    private String phone;
    private String email;
    private Double salary;
    private Date dateOff;

}