package com.sofka.com.cuenta_movimientos_service.service;
import com.sofka.com.cuenta_movimientos_service.dto.response.ReporteMovimientoDTO;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;
import com.sofka.com.cuenta_movimientos_service.repository.CuentaRepository;
import com.sofka.com.cuenta_movimientos_service.repository.MovimientosRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientosRepository movimientoRepository;
    public ReporteService(CuentaRepository cuentaRepository,MovimientosRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository=movimientoRepository;
    }
    public List<ReporteMovimientoDTO> generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener datos del cliente mediante RabbitMQ
//        Cliente cliente = clienteProducer.obtenerCliente(clienteId);
//        if (cliente == null) {
//            throw new IllegalArgumentException("Cliente no encontrado");
//        }

        List<Cuenta> cuentas = cuentaRepository.findAllByCliente(clienteId);

        List<ReporteMovimientoDTO> reporte = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(
                    cuenta.getId(), fechaInicio, fechaFin);

            double saldoInicial = cuenta.getSaldoInicial();

            for (Movimiento movimiento : movimientos) {
                ReporteMovimientoDTO dto = new ReporteMovimientoDTO(
                        movimiento.getFecha().toString(),
//                        cliente.getNombre(),
                        "Kevin Test",
                        cuenta.getNumeroCuenta(),
                        cuenta.getTipoCuenta(),
                        saldoInicial,
                        cuenta.isEstado(),
                        movimiento.getValor(),
                        movimiento.getSaldo()
                );

                reporte.add(dto);

                // Actualizar saldo inicial para el siguiente movimiento
                saldoInicial = movimiento.getSaldo();
            }
        }

        return reporte;
    }

}
