package com.example.ejercicios_spring_boot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.ejercicios_spring_boot.dto.CompraDto;
import com.example.ejercicios_spring_boot.models.Compra;
import com.example.ejercicios_spring_boot.services.CompraService;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService service;

    @PostMapping
    public ResponseEntity<Compra> crear(
            @Valid @RequestBody CompraDto dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<Compra>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<Compra>> porCliente(
            @RequestParam String email) {
        return ResponseEntity.ok(
                service.porCliente(email));
    }
}
