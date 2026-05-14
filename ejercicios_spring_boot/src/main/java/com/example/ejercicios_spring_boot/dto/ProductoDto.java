package com.example.ejercicios_spring_boot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductoDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    private String descripcion;

    @NotNull(message = "La unidad es obligatoria")
    private String unidad;
}
