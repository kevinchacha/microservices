package com.sofka.com.cuenta_movimientos_service.interfaces;
import com.sofka.com.cuenta_movimientos_service.dto.response.GetCuentasDTO;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CuentaInterface {
    Page<GetCuentasDTO> findAllCuentas(Pageable pageable);
    Cuenta findCuentaById(Long id);
    Cuenta createCuenta(Cuenta cuenta);
    Cuenta updateCuenta(Long id, Cuenta cuenta);
    void deleteCuenta(Long id);
}
