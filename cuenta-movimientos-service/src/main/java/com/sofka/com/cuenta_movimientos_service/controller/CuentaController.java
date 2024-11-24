package com.sofka.com.cuenta_movimientos_service.controller;
import com.sofka.com.cuenta_movimientos_service.dto.request.CreateCuentaDTO;
import com.sofka.com.cuenta_movimientos_service.dto.response.GetCuentasDTO;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import com.sofka.com.cuenta_movimientos_service.model.ManageResponse;
import com.sofka.com.cuenta_movimientos_service.service.CuentaService;
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
    public Page<GetCuentasDTO> findAllCuentas(Pageable pageable) {
        return cuentaService.findAllCuentas(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> findCuentaById(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.findCuentaById(id);
        return ResponseEntity.ok(cuenta);
    }

    @PostMapping
    public ResponseEntity<Object> createCuenta(@Valid @RequestBody CreateCuentaDTO createCuentaDTO) {
        try {
            GetCuentasDTO cuentaCreated = cuentaService.createCuenta(createCuentaDTO);
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
    public ResponseEntity<Object> updateCuenta(@PathVariable Long id, @Valid @RequestBody CreateCuentaDTO updateCuentaDTO) {
        GetCuentasDTO updatedCuenta = cuentaService.updateCuenta(id, updateCuentaDTO);
        return ResponseEntity.ok(updatedCuenta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ManageResponse> deleteCuenta(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
        return ResponseEntity.ok(utils.createResponse(Constants.OK, Constants.CUENTA_ELIMINADO + id, null));
    }

}
