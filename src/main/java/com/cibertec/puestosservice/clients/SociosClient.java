package com.cibertec.puestosservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="socios-service")
public interface SociosClient {
    @GetMapping("/api/socios/{id}")
    Object buscarPorId(@PathVariable("id") Long id);
}
