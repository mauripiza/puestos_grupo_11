package com.cibertec.puestosservice.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "puestos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Puesto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    private String numero;
    // Referencia externa; no se mapea como @ManyToOne a Socio
    @Column(name = "socio_id")
    private Long socioId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giro_id", nullable = false)
    private Giro giro;
    @Column(name = "inquilino_nombre", length = 150)
    private String inquilinoNombre;
    @Column(name = "inquilino_documento", length = 20)
    private String inquilinoDocumento;
    @Column(name = "fecha_inicio_vigencia")
    private LocalDate fechaInicioVigencia;
    @Column(name = "fecha_fin_vigencia")
    private LocalDate fechaFinVigencia;
}
