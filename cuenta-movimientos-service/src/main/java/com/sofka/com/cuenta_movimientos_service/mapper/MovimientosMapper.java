package com.sofka.com.cuenta_movimientos_service.mapper;
import com.sofka.com.cuenta_movimientos_service.dto.response.GetMovimientosDTO;
import com.sofka.com.cuenta_movimientos_service.dto.response.MovimientosDTO;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;

public class MovimientosMapper {

    public static GetMovimientosDTO toMovimientoDto(Movimiento movimiento){
        if(movimiento == null) return  null;
        return new GetMovimientosDTO(
               movimiento.getId(),
                movimiento.getFecha(),
                movimiento.getTipoMovimiento(),
                movimiento.getValor(),
                movimiento.getSaldo()
        );
    }
    public static MovimientosDTO toMovimientosDto(Movimiento movimiento){
        if(movimiento == null) return  null;
        return new MovimientosDTO(
                movimiento.getId(),
                movimiento.getFecha(),
                movimiento.getTipoMovimiento(),
                movimiento.getValor(),
                movimiento.getSaldo(),
                movimiento.getCuenta().getNumeroCuenta()
        );
    }
}
