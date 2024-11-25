package com.sofka.com.cuenta_movimientos_service.controller;
import com.sofka.com.cuenta_movimientos_service.dto.request.CreateMovimientoDTO;
import com.sofka.com.cuenta_movimientos_service.dto.response.GetMovimientosDTO;
import com.sofka.com.cuenta_movimientos_service.dto.response.MovimientosDTO;
import com.sofka.com.cuenta_movimientos_service.model.ManageResponse;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;
import com.sofka.com.cuenta_movimientos_service.service.MovimientosService;
import com.sofka.com.cuenta_movimientos_service.utils.CommonUtils;
import com.sofka.com.cuenta_movimientos_service.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {
    private final MovimientosService movimientoService;
    private CommonUtils utils;

    public MovimientosController(MovimientosService movimientoService,CommonUtils utils) {
        this.movimientoService = movimientoService;
        this.utils = utils;
    }

    @GetMapping
    public Page<MovimientosDTO> findAllMovimientos(Pageable pageable) {
        return movimientoService.findAllMovimientos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<GetMovimientosDTO>> findMovimientosByNumCuenta(@PathVariable Long id) {
          List<GetMovimientosDTO> movimiento = movimientoService.findMovimientosByNumCuenta(id);
          return ResponseEntity.ok(movimiento);
    }


    @PostMapping
    public ResponseEntity<Object> createMovimiento(@Valid @RequestBody CreateMovimientoDTO createMovimientoDTO) {
        try {
            MovimientosDTO movimientoCreated = movimientoService.createMovimiento(createMovimientoDTO);
            return new ResponseEntity<>(movimientoCreated, HttpStatus.CREATED);
        } catch (HttpMessageNotReadableException e) {
            throw new HttpMessageNotReadableException("Violación de datos: " + e.getMessage());
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Violación de restricción de datos: " + e.getMessage());
        }catch (RuntimeException e) {
            return new ResponseEntity<>(utils.createResponse(Constants.INTERNAL_SERVER_ERROR, e.toString(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientosDTO> updateMovimiento(@PathVariable Long id,
                                                       @Valid @RequestBody CreateMovimientoDTO createMovimientoDTO) {
        MovimientosDTO movimientoActualizado = movimientoService.updateMovimiento(id, createMovimientoDTO);
        return ResponseEntity.ok(movimientoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ManageResponse> deleteMovimiento(@PathVariable Long id) {
        movimientoService.deleteMovimiento(id);
        return ResponseEntity.ok(utils.createResponse(Constants.OK, Constants.MOVIMIENTO_ELIMINADO + id,null));
    }
}
