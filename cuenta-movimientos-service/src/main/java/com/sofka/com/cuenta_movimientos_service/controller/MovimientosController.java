package com.sofka.com.cuenta_movimientos_service.controller;
import com.sofka.com.cuenta_movimientos_service.model.ManageResponse;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;
import com.sofka.com.cuenta_movimientos_service.service.MovimientosService;
import com.sofka.com.cuenta_movimientos_service.utils.CommonUtils;
import com.sofka.com.cuenta_movimientos_service.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
    public List<Movimiento> findAllMovimientos() {
        return movimientoService.findAllMovimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> findMovimientoById(@PathVariable Long id) {
        Optional<Movimiento> movimiento = movimientoService.findMovimientoById(id);
        if (movimiento.isPresent()) {
            return ResponseEntity.ok(movimiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping
//    public ResponseEntity<ManageResponse> createUser(@RequestBody Movimiento movimiento) {
//        try {
//            ResponseEntity<ErrorResponse> validationResponse = utils.validateRetiro(movimiento);
//            if (validationResponse != null) {
//                return validationResponse;
//            }
//            movimiento = utils.updateSaldo(movimiento);
//            Movimiento postMoviento = movimientoService.saveUser(movimiento);
//            return ResponseEntity.ok(utils.createResponse(Constants.OK, Constants.MOVIMIENTO_CREADO + postMoviento,null));
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(utils.createResponse(Constants.INTERNAL_SERVER_ERROR, e.toString(),null), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ManageResponse> updateUser(@PathVariable Long id, @RequestBody Movimiento movimiento) {
//        try {
//            ResponseEntity<ErrorResponse> validationResponse = utils.validateRetiro(movimiento);
//            if (validationResponse != null) {
//                return validationResponse;
//            }
//            movimiento = utils.updateSaldo(movimiento);
//            Movimiento updatedUser = movimientoService.updateUser(id, movimiento);
//            return ResponseEntity.ok(utils.createResponse(Constants.OK, Constants.MOVIMIENTO_ACTUALIZADO + updatedUser,null));
//
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(utils.createResponse(Constants.INTERNAL_SERVER_ERROR, e.toString(),null), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ManageResponse> deleteMovimiento(@PathVariable Long id) {
        try {
            movimientoService.deleteMovimiento(id);

            return ResponseEntity.ok(utils.createResponse(Constants.OK, Constants.MOVIMIENTO_ELIMINADO + id,null));

        } catch (Exception e) {
            return new ResponseEntity<>(utils.createResponse(Constants.INTERNAL_SERVER_ERROR, e.toString(),null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
