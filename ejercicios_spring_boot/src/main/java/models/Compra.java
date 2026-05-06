package models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "compras")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    
    @Id
    private String id;

    private String clienteNombre;
    private String clienteEmail;
    private String clienteTelefono;

    private List<ItemCompra> items;

    private Double total;

    @Builder.Default
    private EstadoCompra estado = EstadoCompra.PENDIENTE;

    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemCompra {
        private String productoId;
        private String nombreProducto;
        private Integer cantidad;
        private Double precioUnitario;
        private Double subtotal;
    }

    public enum EstadoCompra {
        PENDIENTE, COMPLETADA, CANCELADA
    }
}