package com.cibertec.puestosservice.repositories;

import com.cibertec.puestosservice.entities.Giro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiroRepository extends JpaRepository<Giro,Long> {
    boolean existsByNombre(String nombre);
    boolean existsByNombreAndIdNot(String nombre, Long id);
}
