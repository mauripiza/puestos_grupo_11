package com.cibertec.puestosservice.repositories;

import com.cibertec.puestosservice.entities.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PuestoRepository extends JpaRepository<Puesto,Long> {
    boolean existsByNumero(String numero);
    boolean existsByNumeroAndIdNot(String numero,Long id);
    List<Puesto> findBySocioId(Long socioId);
    List<Puesto> findByGiroId(Long giroId);
}
