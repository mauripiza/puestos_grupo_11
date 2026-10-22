package com.cibertec.puestosservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "giros")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Giro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;
    @Column(length = 255)
    private String descripcion;
}
