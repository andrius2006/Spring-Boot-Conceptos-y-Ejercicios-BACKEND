package repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import models.Producto;

public interface ProductoRepository extends MongoRepository<Producto, String>{

    Optional<Producto> findById(String id);
    Optional<List<Producto>> findByNombreContainingIgnoreCase(String nombre);
    Optional<List<Producto>> findByCategoria(String categoria);

    Optional<List<Producto>> findByStockLessThan(int umbral);

}
