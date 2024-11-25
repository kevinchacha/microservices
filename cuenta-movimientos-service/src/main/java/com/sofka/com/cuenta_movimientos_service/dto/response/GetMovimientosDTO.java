package com.sofka.com.cuenta_movimientos_service.dto.response;
import com.sofka.com.cuenta_movimientos_service.utils.TipoMovimiento;
import java.util.Date;

public record GetMovimientosDTO(
        Long id ,
        Date fecha,
        TipoMovimiento tipoMovimiento,
        double valor,
        double saldo
) {
}
