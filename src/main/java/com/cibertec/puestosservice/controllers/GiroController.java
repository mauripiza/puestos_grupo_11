package com.cibertec.puestosservice.controllers;

import com.cibertec.puestosservice.models.GiroDTO;
import com.cibertec.puestosservice.services.GiroService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/giros")
public class GiroController {
    private final GiroService service;
    public GiroController(GiroService service) { this.service=service; }
    @GetMapping public List<GiroDTO> listar() { return service.listar(); }
    @GetMapping("/{id}") public GiroDTO buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping public ResponseEntity<GiroDTO> crear(@Valid @RequestBody GiroDTO d) { return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(d)); }
    @PutMapping("/{id}") public GiroDTO editar(@PathVariable Long id,@Valid @RequestBody GiroDTO d) { return service.editar(id,d); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminar(id); return ResponseEntity.noContent().build(); }
}
