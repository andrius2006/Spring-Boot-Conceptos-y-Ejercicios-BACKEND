package models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "productos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    private String id;

    @NotBlank
    private String nombre;

    private CategoriaProducto categoria;

    @NotNull
    private Double precio;

    private String unidad;

    @Builder.Default
    private Integer stock = 0;

    @Builder.Default
    private LocalDateTime creadoEn = LocalDateTime.now();

    public enum CategoriaProducto {
        CERAMICA, PEGASUPER, PORCELANA,
        ENCHAPE, ACCESORIO
    }
}