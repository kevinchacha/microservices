package com.sofka.com.cuenta_movimientos_service.dto.response;

public record GetCuentasDTO(
        long numeroCuenta,
        String tipoCuenta,
        double saldoInicial,
        boolean estado

) {
}
