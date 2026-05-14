package com.example.ejercicios_spring_boot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.example.ejercicios_spring_boot.models.Producto;
import com.example.ejercicios_spring_boot.repositories.ProductoRepository;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public Optional<List<Producto>> listar() {
        return Optional.ofNullable(productoRepository.findAll());
    }

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<List<Producto>> buscar(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Optional<List<Producto>> porCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public Optional<List<Producto>> stockBajo(int umbral) {
        return productoRepository.findByStockLessThan(umbral);
    }

    public Producto obtenerPorId(String id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    public void eliminar(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }
}
