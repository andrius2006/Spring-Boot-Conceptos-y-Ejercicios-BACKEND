package dto;

import lombok.Data;
import models.Producto;
import jakarta.validation.constraints.*;

@Data
public class ProductoDto {
    @NotBlank(message = "Nombre requerido")
    private String nombre;

    @NotNull
    private Producto.CategoriaProducto categoria;

    @NotNull @Positive
    private Double precio;

    @NotBlank
    private String unidad;

    @NotNull @PositiveOrZero
    private Integer stock;
}

