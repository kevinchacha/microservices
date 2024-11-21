package com.sofka.com.cuenta_movimientos_service.service;
import com.sofka.com.cuenta_movimientos_service.interfaces.CuentaInterface;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import com.sofka.com.cuenta_movimientos_service.repository.CuentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
@Service
public class CuentaService implements CuentaInterface {

    private final CuentaRepository cuentaRepository;
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }
    @Override
    public List<Cuenta> findAllCuentas(Pageable pageable) {
        return cuentaRepository.findAll();
    }

    @Override
    public Cuenta findCuentaById(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Cuenta no encontrada con ID: " + id));
    }

    @Transactional
    @Override
    public Cuenta createCuenta(Cuenta cuenta) {
        if (cuenta.getMovimientos() != null && !cuenta.getMovimientos().isEmpty()) {
            cuenta.getMovimientos().forEach(movimiento -> movimiento.setCuenta(cuenta));
        }
        return cuentaRepository.save(cuenta);
    }

    @Transactional
    @Override
    public Cuenta updateCuenta(Long id, Cuenta newCuenta) {
        Cuenta cuenta = findCuentaById(id);
        cuenta.setTipoCuenta(newCuenta.getTipoCuenta());
        cuenta.setSaldoInicial(newCuenta.getSaldoInicial());
        cuenta.setEstado(newCuenta.isEstado());
//        cuenta.setCliente(newCuenta.getCliente());
        return cuentaRepository.save(cuenta);
    }

    @Override
    public void deleteCuenta(Long id) {
        findCuentaById(id);
        cuentaRepository.deleteById(id);
    }
}
