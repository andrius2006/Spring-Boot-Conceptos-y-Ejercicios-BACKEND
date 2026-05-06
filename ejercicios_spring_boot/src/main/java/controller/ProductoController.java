package controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dto.ProductoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import models.Producto;
import services.ProductoService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public ResponseEntity<List<Producto>> listar(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false)
            Producto.CategoriaProducto categoria) {
        if (nombre != null)
            return ResponseEntity.ok(
                service.buscar(nombre));
        if (categoria != null)
            return ResponseEntity.ok(
                service.porCategoria(categoria));
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> stockBajo(
        @RequestParam(defaultValue = "10") int umbral) {
        return ResponseEntity.ok(
            service.stockBajo(umbral));
    }

    @PostMapping
    public ResponseEntity<Producto> crear(
        @Valid @RequestBody ProductoDto dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
        @PathVariable String id,
        @Valid @RequestBody ProductoDto dto) {
        return ResponseEntity.ok(
            service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
        @PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
