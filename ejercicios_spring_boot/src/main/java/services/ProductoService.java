package services;

import java.util.List;

import org.springframework.stereotype.Service;

import dto.ProductoDto;
import lombok.RequiredArgsConstructor;
import models.Producto;
import repositories.ProductoRepository;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repo;

    public List<Producto> listar() {
        return repo.findAll();
    }

    public List<Producto> porCategoria(
        Producto.CategoriaProducto cat) {
        return repo.findByCategoria(cat);
    }

    public List<Producto> buscar(String nombre) {
        return repo.findByNombreContainingIgnoreCase(
            nombre);
    }

    public List<Producto> stockBajo(int umbral) {
        return repo.findByStockLessThan(umbral);
    }

    public Producto crear(ProductoDto dto) {
        Producto p = Producto.builder()
            .nombre(dto.getNombre())
            .categoria(dto.getCategoria())
            .precio(dto.getPrecio())
            .unidad(dto.getUnidad())
            .stock(dto.getStock())
            .build();
        return repo.save(p);
    }

    public Producto actualizar(String id,
        ProductoDto dto) {
        Producto p = repo.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Producto no encontrado"));
        p.setNombre(dto.getNombre());
        p.setCategoria(dto.getCategoria());
        p.setPrecio(dto.getPrecio());
        p.setUnidad(dto.getUnidad());
        p.setStock(dto.getStock());
        return repo.save(p);
    }

    public void eliminar(String id) {
        repo.deleteById(id);
    }
}
