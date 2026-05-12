package models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "productos")
@Data
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

    private Integer stock;

    public Producto save(Producto producto) {
        throw new UnsupportedOperationException("El producto no se ha guardado");
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(int i) {
        this.stock = i;
    }
}