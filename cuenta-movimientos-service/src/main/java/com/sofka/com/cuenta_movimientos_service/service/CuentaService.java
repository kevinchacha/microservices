package com.sofka.com.cuenta_movimientos_service.service;
import com.sofka.com.cuenta_movimientos_service.dto.request.CreateCuentaDTO;
import com.sofka.com.cuenta_movimientos_service.dto.response.GetCuentasDTO;
import com.sofka.com.cuenta_movimientos_service.interfaces.CuentaInterface;
import com.sofka.com.cuenta_movimientos_service.mapper.CuentaMapper;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import com.sofka.com.cuenta_movimientos_service.repository.CuentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
@Service
public class CuentaService implements CuentaInterface {

    private final CuentaRepository cuentaRepository;
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }
    @Override
    public Page<GetCuentasDTO> findAllCuentas(Pageable pageable) {
        Page<Cuenta> cuentas= cuentaRepository.findAll(pageable);
        return cuentas.map(CuentaMapper::toCuentaDto);
    }

    @Override
    public Cuenta findCuentaById(Long id) {
        return cuentaRepository.findByNumeroCuenta(id)
                .orElseThrow(()-> new NoSuchElementException("Cuenta no encontrada con ID: " + id));
    }

    @Transactional
    @Override
    public GetCuentasDTO createCuenta(CreateCuentaDTO createCuentaDTO) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(createCuentaDTO.numeroCuenta());
        cuenta.setTipoCuenta(createCuentaDTO.tipoCuenta());
        cuenta.setSaldoInicial(createCuentaDTO.saldoInicial());
        cuenta.setEstado(createCuentaDTO.estado());
        cuenta.setCliente(createCuentaDTO.identificacion());
        cuentaRepository.save(cuenta);
        return CuentaMapper.toCuentaDto(cuenta);
    }

        @Transactional
        @Override
        public GetCuentasDTO updateCuenta(Long id,  CreateCuentaDTO updateCuentaDTO) {
            Cuenta cuenta = findCuentaById(id);
            cuenta.setNumeroCuenta(updateCuentaDTO.numeroCuenta());
            cuenta.setTipoCuenta(updateCuentaDTO.tipoCuenta());
            cuenta.setSaldoInicial(updateCuentaDTO.saldoInicial());
            cuenta.setEstado(updateCuentaDTO.estado());
            cuenta.setCliente(updateCuentaDTO.identificacion());
            cuentaRepository.save(cuenta);
            return CuentaMapper.toCuentaDto(cuenta);
        }

        @Transactional
        @Override
        public void deleteCuenta(Long id) {
            findCuentaById(id);
            cuentaRepository.deleteByNumeroCuenta(id);
        }
}
