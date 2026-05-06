package dto;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDto {
    @NotBlank
    private String clienteNombre;

    @Email @NotBlank
    private String clienteEmail;

    private String clienteTelefono;

    @NotEmpty
    private List<ItemCompraDto> items;

    @Data
    public static class ItemCompraDto {
        @NotBlank
        private String productoId;

        @NotNull @Positive
        private Integer cantidad;
    }
}

