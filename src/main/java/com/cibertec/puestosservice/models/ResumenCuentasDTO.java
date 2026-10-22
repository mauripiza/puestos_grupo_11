package com.cibertec.puestosservice.models;

import java.util.List;
import java.util.Map;

public record ResumenCuentasDTO(
    List<Map<String,Object>> cuentas,
    List<Map<String,Object>> movimientos
) {}
