package com.sofka.com.cuenta_movimientos_service.interfaces;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
public interface CuentaInterface {
    List<Cuenta> findAllCuentas(Pageable pageable);
    Cuenta findCuentaById(Long id);
    Cuenta createCuenta(Cuenta cuenta);
    Cuenta updateCuenta(Long id, Cuenta cuenta);
    void deleteCuenta(Long id);
}
