package repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import models.Producto;

@Repository
public interface ProductoRepository
    extends MongoRepository<Producto, String> {

    List<Producto> findByCategoria(
        Producto.CategoriaProducto categoria);

    List<Producto> findByNombreContainingIgnoreCase(
        String nombre);

    List<Producto> findByStockLessThan(int umbral);

    List<Producto> findByStockEquals(int stock);
}


