package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import dto.CompraDto;
import lombok.RequiredArgsConstructor;
import models.Compra;
import models.Producto;
import repositories.CompraRepository;
import repositories.ProductoRepository;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepo;
    private final ProductoRepository productoRepo;

    public Compra crear(CompraDto dto) {
        List<Compra.ItemCompra> items = new ArrayList<>();
        double total = 0;

        for (CompraDto.ItemCompraDto item : dto.getItems()) {
            Producto p = productoRepo
                .findById(item.getProductoId())
                .orElseThrow(() -> new RuntimeException(
                    "Producto no encontrado: "
                    + item.getProductoId()));

            if (p.getStock() < item.getCantidad())
                throw new RuntimeException(
                    "Stock insuficiente: " + p.getNombre());

            double subtotal = p.getPrecio()
                * item.getCantidad();
            total += subtotal;

            p.setStock(p.getStock() - item.getCantidad());
            productoRepo.save(p);

            items.add(Compra.ItemCompra.builder()
                .productoId(p.getId())
                .nombreProducto(p.getNombre())
                .cantidad(item.getCantidad())
                .precioUnitario(p.getPrecio())
                .subtotal(subtotal)
                .build());
        }

        Compra compra = Compra.builder()
            .clienteNombre(dto.getClienteNombre())
            .clienteEmail(dto.getClienteEmail())
            .clienteTelefono(dto.getClienteTelefono())
            .items(items)
            .total(total)
            .build();

        return compraRepo.save(compra);
    }

    public List<Compra> listar() {
        return compraRepo.findAll();
    }

    public List<Compra> porCliente(String email) {
        return compraRepo.findByClienteEmail(email);
    }
}

