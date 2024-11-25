package com.sofka.com.cuenta_movimientos_service.service;
import com.sofka.com.cuenta_movimientos_service.dto.request.CreateMovimientoDTO;
import com.sofka.com.cuenta_movimientos_service.dto.response.GetMovimientosDTO;
import com.sofka.com.cuenta_movimientos_service.dto.response.MovimientosDTO;
import com.sofka.com.cuenta_movimientos_service.interfaces.MovimientosInterface;
import com.sofka.com.cuenta_movimientos_service.mapper.MovimientosMapper;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;
import com.sofka.com.cuenta_movimientos_service.repository.CuentaRepository;
import com.sofka.com.cuenta_movimientos_service.repository.MovimientosRepository;
import com.sofka.com.cuenta_movimientos_service.utils.TipoMovimiento;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MovimientosService implements MovimientosInterface {

    private final MovimientosRepository movimientosRepository;

    private  final CuentaRepository cuentaRepository;

    private  final CuentaService cuentaService;

    public MovimientosService(MovimientosRepository movimientosRepository,CuentaRepository cuentaRepository,CuentaService cuentaService) {
        this.movimientosRepository = movimientosRepository;
        this.cuentaRepository = cuentaRepository;
        this.cuentaService=cuentaService;
    }
    @Override
    public Page<MovimientosDTO> findAllMovimientos(Pageable pageable) {
        Page<Movimiento> movimientos =movimientosRepository.findAll(pageable);
        return movimientos.map(MovimientosMapper::toMovimientosDto);
    }

    @Override
    public List<GetMovimientosDTO> findMovimientosByNumCuenta(Long id) {
        List<Movimiento> movimientos = movimientosRepository.findByCuentaNumeroCuenta(id);
        if (movimientos.isEmpty()) {
            throw new NoSuchElementException("Movimientos no encontrados para el número de cuenta: " + id);
        }
        return  movimientos.stream().map(MovimientosMapper::toMovimientoDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MovimientosDTO createMovimiento(CreateMovimientoDTO createMovimientoDTO) {
        Cuenta cuenta = cuentaService.findCuentaById(createMovimientoDTO.cuentaId());
        Double nuevoSaldo = cuenta.getSaldoInicial();

        if (createMovimientoDTO.tipoMovimiento() == TipoMovimiento.RETIRO) {
            nuevoSaldo -= createMovimientoDTO.valor();
            if (nuevoSaldo < 0) {
                throw new IllegalArgumentException("Saldo no disponible para realizar el retiro");
            }
        } else if (createMovimientoDTO.tipoMovimiento() == TipoMovimiento.DEPOSITO) {
            nuevoSaldo += createMovimientoDTO.valor();
        }
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(createMovimientoDTO.fecha());
        movimiento.setTipoMovimiento(createMovimientoDTO.tipoMovimiento());
        movimiento.setValor(createMovimientoDTO.valor());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);
        movimientosRepository.save(movimiento);
        return MovimientosMapper.toMovimientosDto(movimiento);
    }

    @Transactional
    @Override
    public MovimientosDTO updateMovimiento(Long id, CreateMovimientoDTO updateMovimientoDTO) {
        Movimiento movimientoExistente = movimientosRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movimiento no encontrado con ID: " + id));
        Cuenta cuenta = movimientoExistente.getCuenta();
        if (movimientoExistente.getTipoMovimiento() == TipoMovimiento.RETIRO) {
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() + movimientoExistente.getValor());
        } else if (movimientoExistente.getTipoMovimiento() == TipoMovimiento.DEPOSITO) {
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() - movimientoExistente.getValor());
        }
        Double nuevoSaldo = cuenta.getSaldoInicial();
        if (updateMovimientoDTO.tipoMovimiento() == TipoMovimiento.RETIRO) {
            nuevoSaldo -= updateMovimientoDTO.valor();
            if (nuevoSaldo < 0) {
                throw new IllegalArgumentException("Saldo no disponible para realizar el retiro");
            }
        } else if (updateMovimientoDTO.tipoMovimiento() == TipoMovimiento.DEPOSITO) {
            nuevoSaldo += updateMovimientoDTO.valor();
        } else {
            throw new IllegalArgumentException("Tipo de movimiento no válido");
        }
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
        movimientoExistente.setFecha(updateMovimientoDTO.fecha());
        movimientoExistente.setTipoMovimiento(updateMovimientoDTO.tipoMovimiento());
        movimientoExistente.setValor(updateMovimientoDTO.valor());
        movimientoExistente.setSaldo(nuevoSaldo);
        movimientosRepository.save(movimientoExistente);
        return MovimientosMapper.toMovimientosDto(movimientoExistente);
    }
    @Transactional
    @Override
    public void deleteMovimiento(Long id) {
        Movimiento movimiento = movimientosRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movimiento no encontrado con ID: " + id));

        Cuenta cuenta = movimiento.getCuenta();

        Double nuevoSaldo = cuenta.getSaldoInicial();
        if (movimiento.getTipoMovimiento() == TipoMovimiento.RETIRO) {
            nuevoSaldo += movimiento.getValor();
        } else if (movimiento.getTipoMovimiento() == TipoMovimiento.DEPOSITO) {
            nuevoSaldo -= movimiento.getValor();
        }

        if (nuevoSaldo < 0) {
            throw new IllegalStateException("El saldo no puede ser negativo al eliminar el movimiento");
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimientosRepository.deleteById(id);

    }
}
