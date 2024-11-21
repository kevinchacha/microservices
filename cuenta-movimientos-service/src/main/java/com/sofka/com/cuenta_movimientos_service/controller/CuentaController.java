package com.sofka.com.cuenta_movimientos_service.controller;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import com.sofka.com.cuenta_movimientos_service.model.ManageResponse;
import com.sofka.com.cuenta_movimientos_service.service.CuentaService;
import com.sofka.com.cuenta_movimientos_service.utils.CommonUtils;
import com.sofka.com.cuenta_movimientos_service.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;
    private CommonUtils utils;

    public CuentaController(CuentaService cuentaService,CommonUtils utils) {
        this.cuentaService = cuentaService;
        this.utils = utils;
    }
    @GetMapping
    public List<Cuenta> findAllCuentas(Pageable pageable) {
        return cuentaService.findAllCuentas(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> findCuentaById(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.findCuentaById(id);
        return ResponseEntity.ok(cuenta);
    }

    @PostMapping
    public ResponseEntity<Object> createCuenta(@Valid @RequestBody Cuenta cuenta) {
        try {
            Cuenta cuentaCreated = cuentaService.createCuenta(cuenta);
            return new ResponseEntity<>(cuentaCreated, HttpStatus.CREATED);
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
    public ResponseEntity<ManageResponse> updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        Cuenta updatedCuenta = cuentaService.updateCuenta(id, cuenta);
        return ResponseEntity.ok(utils.createResponse(Constants.OK, Constants.CUENTA_ACTUALIZADO + updatedCuenta, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ManageResponse> deleteCuenta(@PathVariable Long id) {
        try {
            cuentaService.deleteCuenta(id);
            return ResponseEntity.ok(utils.createResponse(Constants.OK, Constants.CUENTA_ELIMINADO + id, null));
        } catch (Exception e) {
            return new ResponseEntity<>(utils.createResponse(Constants.INTERNAL_SERVER_ERROR, e.toString(),null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
