package com.cibertec.puestosservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GiroDTO(Long id, @NotBlank @Size(max=100) String nombre,
                      @Size(max=255) String descripcion) {}
