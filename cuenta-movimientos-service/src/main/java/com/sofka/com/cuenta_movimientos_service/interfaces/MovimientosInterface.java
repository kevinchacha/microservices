package com.sofka.com.cuenta_movimientos_service.interfaces;
import com.sofka.com.cuenta_movimientos_service.dto.request.CreateMovimientoDTO;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovimientosInterface {
    Page<Movimiento> findAllMovimientos(Pageable pageable);
    List <Movimiento> findMovimientosByNumCuenta(Long id);
    Movimiento createMovimiento(CreateMovimientoDTO createMovimientoDTO);
    Movimiento updateMovimiento(Long id,CreateMovimientoDTO updateMovimientoDTO);
    void deleteMovimiento(Long id);
}
