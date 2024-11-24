package com.sofka.com.cuenta_movimientos_service.mapper;

import com.sofka.com.cuenta_movimientos_service.dto.response.GetCuentasDTO;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;

public class CuentaMapper {
    public static GetCuentasDTO toCuentaDto(Cuenta cuenta){
        if(cuenta == null) return  null;
        return new GetCuentasDTO(
             cuenta.getNumeroCuenta(),
                cuenta.getTipoCuenta(),
                cuenta.getSaldoInicial(),
                cuenta.isEstado()
        );
    }
}
