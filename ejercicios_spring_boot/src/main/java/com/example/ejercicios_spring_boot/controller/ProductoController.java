package com.example.ejercicios_spring_boot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.ejercicios_spring_boot.dto.ProductoDto;
import com.example.ejercicios_spring_boot.models.Producto;
import com.example.ejercicios_spring_boot.services.ProductoService;

@RestController
@RequestMapping("/api/productos")
// @CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public ResponseEntity<Optional<List<Producto>>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria) {

        if (nombre != null)
            return ResponseEntity.ok(service.buscar(nombre));
        if (categoria != null)
            return ResponseEntity.ok(service.porCategoria(categoria));

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<Optional<List<Producto>>> stockBajo(
            @RequestParam(defaultValue = "10") int umbral) {
        return ResponseEntity.ok(service.stockBajo(umbral));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoDto dto) {
        System.out.println(dto);
        Producto producto = Producto.builder()
        .nombre(dto.getNombre())
        .categoria(dto.getCategoria())
        .precio(dto.getPrecio())
        .descripcion(dto.getDescripcion())
        .unidad(dto.getUnidad() != null ? dto.getUnidad().toString() : null)
        .build();

        if (dto.getUnidad() != null) {
            producto.setUnidad(dto.getUnidad().toString());
        }
        System.out.println(producto);

        return ResponseEntity.status(201).body(service.crearProducto(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
