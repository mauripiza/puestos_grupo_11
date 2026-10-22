package com.cibertec.puestosservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@FeignClient(name="cobranzas-service")
public interface CobranzasClient {
    @GetMapping("/api/cuentas-por-cobrar/puesto/{puestoId}")
    List<Map<String,Object>> listarPorPuesto(@PathVariable("puestoId") Long puestoId);
}
