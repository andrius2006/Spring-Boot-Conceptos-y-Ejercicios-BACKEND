package com.example.ejercicios_spring_boot.dto;

import lombok.Data;

@Data
public class RegisterDto {

    private String nombre;
    private String email;
    private String password;
}
