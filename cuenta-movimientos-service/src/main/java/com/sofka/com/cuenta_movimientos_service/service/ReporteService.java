package com.sofka.com.cuenta_movimientos_service.service;
import com.sofka.com.cuenta_movimientos_service.dto.response.ReporteMovimientoDTO;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;
import com.sofka.com.cuenta_movimientos_service.mq.producer.ClienteProducer;
import com.sofka.com.cuenta_movimientos_service.repository.CuentaRepository;
import com.sofka.com.cuenta_movimientos_service.repository.MovimientosRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;


@Service
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientosRepository movimientoRepository;
    private final ClienteProducer clienteProducer;
    private final RabbitTemplate rabbitTemplate;
    public ReporteService(RabbitTemplate rabbitTemplate,CuentaRepository cuentaRepository,MovimientosRepository movimientoRepository,ClienteProducer clienteProducer) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository=movimientoRepository;
        this.clienteProducer=clienteProducer;
        this.rabbitTemplate=rabbitTemplate;
    }
    public List<ReporteMovimientoDTO> generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
     /*   clienteProducer.solicitarCliente(clienteId)
                .thenAccept(clienteName -> {
                    System.out.println("Nombre del cliente: " + clienteName);
                })
                .exceptionally(error -> {
                    throw new IllegalArgumentException("Error al obtener cliente: " + error.getMessage());
                });*/
        try {
            String clienteName = clienteProducer.solicitarCliente(clienteId).get();
            if(clienteName.startsWith("CLIENT_NOT_FOUND")){
                throw new NoSuchElementException("Cliente no encontrado con ID: " + clienteId);
            }
            List<Cuenta> cuentas = cuentaRepository.findAllByCliente(clienteId);
            List<ReporteMovimientoDTO> reporte = new ArrayList<>();
            for (Cuenta cuenta : cuentas) {
                List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(
                        cuenta.getId(), fechaInicio, fechaFin);
                double ultimoSaldo = cuenta.getSaldoInicial();
                for (Movimiento movimiento : movimientos) {
                    ReporteMovimientoDTO dto = new ReporteMovimientoDTO(
                            movimiento.getFecha().toString(),
                            clienteName,
                            cuenta.getNumeroCuenta(),
                            movimiento.getTipoMovimiento(),
                            cuenta.getPrimerMonto(),
                            ultimoSaldo,
                            cuenta.isEstado(),
                            movimiento.getValor(),
                            movimiento.getSaldo()
                    );

                    reporte.add(dto);

                    // Actualizar saldo inicial para el siguiente movimiento
                    ultimoSaldo = movimiento.getSaldo();
                }
            }
            if(reporte.isEmpty()){
                throw new NoSuchElementException("No existen movimientos para el cliente con ID: " + clienteId);
            }
            return reporte;
        }catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

    }

}
