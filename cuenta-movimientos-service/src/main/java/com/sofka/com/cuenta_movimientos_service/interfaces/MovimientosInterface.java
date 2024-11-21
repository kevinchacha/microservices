package com.sofka.com.cuenta_movimientos_service.interfaces;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;
import java.util.List;
import java.util.Optional;

public interface MovimientosInterface {
    List<Movimiento> findAllMovimientos();
    Optional<Movimiento> findMovimientoById(Long id);
    Movimiento createMovimiento(Movimiento movimiento);
    Movimiento updateMovimiento(Long id, Movimiento movimiento);
    void deleteMovimiento(Long id);
}
