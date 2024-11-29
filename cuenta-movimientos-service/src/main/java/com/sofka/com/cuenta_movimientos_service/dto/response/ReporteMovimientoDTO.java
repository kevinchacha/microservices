package com.sofka.com.cuenta_movimientos_service.dto.response;
import com.sofka.com.cuenta_movimientos_service.utils.TipoMovimiento;

public record ReporteMovimientoDTO(
        String fecha,
        String cliente,
        Long numeroCuenta,
        TipoMovimiento tipoMovimiento,

        double primerSaldo,
        double ultimoSaldo,
        boolean estado,
        double movimiento,
        double saldoDisponible
) {
}
