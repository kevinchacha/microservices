package com.sofka.com.cuenta_movimientos_service.dto.request;
import com.sofka.com.cuenta_movimientos_service.utils.TipoCuenta;
import jakarta.validation.constraints.*;

public record CreateCuentaDTO(
        @NotNull(message = "El número de cuenta es obligatorio")
        @Positive(message = "El número de cuenta debe ser un número positivo")
        Long numeroCuenta,

        @NotNull(message = "El tipo de cuenta es obligatorio")
        TipoCuenta tipoCuenta,

        @NotNull(message = "El saldo inicial es obligatorio")
        Double saldoInicial,

        Boolean estado,

        @NotNull(message = "La identificación es obligatoria")
        @Positive(message = "La identificación debe ser un número positivo")
        Long identificacion
) {
}
