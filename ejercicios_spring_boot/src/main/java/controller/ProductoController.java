package controller;

import java.util.List;
import java.util.Optional;

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

    // GET /api/productos
    // GET /api/productos?nombre=leche
    // GET /api/productos?categoria=LACTEOS
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
    

    // GET /api/productos/stock-bajo?umbral=10
    @GetMapping("/stock-bajo")
    public ResponseEntity<Optional<List<models.Producto>>> stockBajo(
            @RequestParam(defaultValue = "10") int umbral) {
        return ResponseEntity.ok(service.stockBajo(umbral));
    }

    // GET /api/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // POST /api/productos
    @PostMapping("/crear-producto")
    public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoDto dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        return ResponseEntity.status(201).body(service.crearProducto(producto));  // ✅ 201 Created
    }

 

    // DELETE /api/productos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();  // ✅ 204 No Content
    }
}
