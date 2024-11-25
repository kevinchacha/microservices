package com.sofka.com.cuenta_movimientos_service.interfaces;
import com.sofka.com.cuenta_movimientos_service.dto.request.CreateCuentaDTO;
import com.sofka.com.cuenta_movimientos_service.dto.response.GetCuentasDTO;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CuentaInterface {
    Page<GetCuentasDTO> findAllCuentas(Pageable pageable);
    Cuenta findCuentaById(Long id);
    GetCuentasDTO createCuenta(CreateCuentaDTO createCuentaDTO);
    GetCuentasDTO updateCuenta(Long id, CreateCuentaDTO updateCuentaDTO);
    void deleteCuenta(Long id);
}
