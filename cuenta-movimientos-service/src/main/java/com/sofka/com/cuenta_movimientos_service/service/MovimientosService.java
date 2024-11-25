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
import com.sofka.com.cuenta_movimientos_service.utils.CommonUtils;
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

    private  final CommonUtils utils;

    public MovimientosService(MovimientosRepository movimientosRepository,CuentaRepository cuentaRepository,CuentaService cuentaService,CommonUtils utils) {
        this.movimientosRepository = movimientosRepository;
        this.cuentaRepository = cuentaRepository;
        this.cuentaService=cuentaService;
        this.utils=utils;
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
        Cuenta cuenta = CommonUtils.validarYObtenerCuenta(cuentaService.findCuentaById(createMovimientoDTO.cuentaId()));
        double nuevoSaldo = CommonUtils.calcularNuevoSaldo(
                cuenta.getSaldoInicial(),
                createMovimientoDTO.tipoMovimiento(),
                createMovimientoDTO.valor()
        );
        CommonUtils.actualizarSaldoCuenta(cuenta, nuevoSaldo, cuentaRepository);
        Movimiento movimiento = CommonUtils.crearOModificarMovimiento(
                new Movimiento(),
                createMovimientoDTO,
                nuevoSaldo,
                cuenta
        );
        movimientosRepository.save(movimiento);
        return MovimientosMapper.toMovimientosDto(movimiento);
    }

    @Transactional
    @Override
    public MovimientosDTO updateMovimiento(Long id, CreateMovimientoDTO updateMovimientoDTO) {
        Movimiento movimientoExistente = movimientosRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movimiento no encontrado con ID: " + id));

        Cuenta cuenta = CommonUtils.validarYObtenerCuenta(movimientoExistente.getCuenta());
        findMovimientosByNumCuenta(updateMovimientoDTO.cuentaId());
        double saldoRevertido = CommonUtils.revertirMovimientoAnterior(cuenta, movimientoExistente);

        double nuevoSaldo = CommonUtils.calcularNuevoSaldo(
                saldoRevertido,
                updateMovimientoDTO.tipoMovimiento(),
                updateMovimientoDTO.valor()
        );
        CommonUtils.actualizarSaldoCuenta(cuenta, nuevoSaldo, cuentaRepository);
        Movimiento movimientoModificado = CommonUtils.crearOModificarMovimiento(
                movimientoExistente,
                updateMovimientoDTO,
                nuevoSaldo,
                cuenta
        );
        movimientosRepository.save(movimientoModificado);
        return MovimientosMapper.toMovimientosDto(movimientoModificado);
    }
    @Transactional
    @Override
    public void deleteMovimiento(Long id) {
        Movimiento movimiento = movimientosRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movimiento no encontrado con ID: " + id));

        Cuenta cuenta = CommonUtils.validarYObtenerCuenta(movimiento.getCuenta());
        double nuevoSaldo = CommonUtils.revertirMovimientoAnterior(cuenta, movimiento);
        if (nuevoSaldo < 0) {
            throw new IllegalStateException("El saldo no puede ser negativo al eliminar el movimiento.");
        }
        CommonUtils.actualizarSaldoCuenta(cuenta, nuevoSaldo, cuentaRepository);
        movimientosRepository.deleteById(id);

    }
}
