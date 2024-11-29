package com.sofka.com.cuenta_movimientos_service.interfaces;
import com.sofka.com.cuenta_movimientos_service.dto.request.CreateMovimientoDTO;
import com.sofka.com.cuenta_movimientos_service.dto.response.GetMovimientosDTO;
import com.sofka.com.cuenta_movimientos_service.dto.response.MovimientosDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MovimientosInterface {
    Page<MovimientosDTO> findAllMovimientos(Pageable pageable);
    List <GetMovimientosDTO> findMovimientosByNumCuenta(Long id);
    MovimientosDTO createMovimiento(CreateMovimientoDTO createMovimientoDTO);
    MovimientosDTO updateMovimiento(Long id,CreateMovimientoDTO updateMovimientoDTO);
    void deleteMovimiento(Long id);
}
