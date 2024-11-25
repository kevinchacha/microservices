package com.sofka.com.cuenta_movimientos_service.dto.request;
import com.sofka.com.cuenta_movimientos_service.utils.TipoMovimiento;
import jakarta.validation.constraints.*;
import java.util.Date;

public record CreateMovimientoDTO(

        @NotNull(message = "La fecha no puede ser nula")
        Date fecha,

        @NotNull(message = "El tipo de movimiento es obligatorio")
        TipoMovimiento tipoMovimiento,

        @NotNull(message = "El valor no puede ser nulo")
        Double valor,

        @NotNull(message = "El ID de la cuenta no puede ser nulo")
        @Positive(message = "El ID de la cuenta debe ser un número positivo")
        Long cuentaId
) {
}
