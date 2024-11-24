package com.sofka.com.cuenta_movimientos_service.dto.response;

import com.sofka.com.cuenta_movimientos_service.utils.TipoCuenta;

public record GetCuentasDTO(
        long numeroCuenta,
        TipoCuenta tipoCuenta,
        double saldoInicial,
        boolean estado

) {
}
