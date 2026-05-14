package com.example.ejercicios_spring_boot.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {

    @Id
    private String id;

    @NotBlank
    private String nombre;

    private String categoria;

    @NotNull
    private Double precio;

    private String descripcion;

    private String unidad;


    public Producto save(Producto producto) {
        throw new UnsupportedOperationException("El producto no se ha guardado");
    }

 // Métodos de conveniencia para CompraService
    public int getStock() {
        return unidad != null ? Integer.parseInt(unidad) : 0;
    }

    public void setStock(int nuevoStock) {
        this.unidad = String.valueOf(nuevoStock);
    }
}