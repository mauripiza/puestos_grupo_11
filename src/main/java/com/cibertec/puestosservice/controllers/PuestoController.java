package com.cibertec.puestosservice.controllers;

import com.cibertec.puestosservice.models.*;
import com.cibertec.puestosservice.services.PuestoService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/puestos")
public class PuestoController {
    private final PuestoService service;
    public PuestoController(PuestoService service) { this.service=service; }
    @GetMapping public List<PuestoDTO> listar() { return service.listar(); }
    @GetMapping("/socio/{socioId}") public List<PuestoDTO> listarPorSocio(@PathVariable Long socioId) { return service.listarPorSocio(socioId); }
    @GetMapping("/{id}") public PuestoDTO buscar(@PathVariable Long id) { return service.buscar(id); }
    @GetMapping("/{id}/resumen") public ResumenCuentasDTO resumen(@PathVariable Long id) { return service.resumen(id); }
    @PostMapping public ResponseEntity<PuestoDTO> crear(@Valid @RequestBody PuestoDTO d) { return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(d)); }
    @PutMapping("/{id}") public PuestoDTO editar(@PathVariable Long id,@Valid @RequestBody PuestoDTO d) { return service.editar(id,d); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminar(id); return ResponseEntity.noContent().build(); }
}
