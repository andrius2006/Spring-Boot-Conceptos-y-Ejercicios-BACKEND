package com.example.ejercicios_spring_boot.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ejercicios_spring_boot.models.Compra;

@Repository
public interface CompraRepository
        extends MongoRepository<Compra, String> {

    List<Compra> findByClienteEmail(String email);

    List<Compra> findByEstado(
            Compra.EstadoCompra estado);

    List<Compra> findByFechaBetween(
            LocalDateTime inicio, LocalDateTime fin);

    List<Compra> findByItemsProductoId(
            String productoId);
}
