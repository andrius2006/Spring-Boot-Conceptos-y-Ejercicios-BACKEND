package dto;

import lombok.Data;
import models.Producto;
import jakarta.validation.constraints.*;

@Data
public class ProductoDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;


    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    

    @NotBlank(message = "La categoría es obligatoria")

    private String categoria;
}
