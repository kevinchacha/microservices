package com.sofka.com.cuenta_movimientos_service.dto.response;

import com.sofka.com.cuenta_movimientos_service.utils.TipoCuenta;

public record ReporteMovimientoDTO(
        String fecha,
        String cliente,
        Long numeroCuenta,
        TipoCuenta tipo,
        double saldoInicial,
        boolean estado,
        double movimiento,
        double saldoDisponible
) {
}
