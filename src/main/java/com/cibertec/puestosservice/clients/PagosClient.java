package com.cibertec.puestosservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@FeignClient(name="pagos-service")
public interface PagosClient {
    @GetMapping("/api/recibos/puesto/{puestoId}")
    List<Map<String,Object>> listarPorPuesto(@PathVariable("puestoId") Long puestoId);
}
