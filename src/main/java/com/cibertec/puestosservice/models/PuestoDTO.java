package com.cibertec.puestosservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record PuestoDTO(
    Long id,
    @NotBlank @Size(max=20) String numero,
    Long socioId,
    @NotNull Long giroId,
    @Size(max=150) String inquilinoNombre,
    @Size(max=20) String inquilinoDocumento,
    LocalDate fechaInicioVigencia,
    LocalDate fechaFinVigencia
) {}
