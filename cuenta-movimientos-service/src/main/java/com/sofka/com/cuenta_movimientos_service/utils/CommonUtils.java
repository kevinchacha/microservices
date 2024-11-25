package com.sofka.com.cuenta_movimientos_service.utils;
import com.sofka.com.cuenta_movimientos_service.dto.request.CreateMovimientoDTO;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import com.sofka.com.cuenta_movimientos_service.model.ManageResponse;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;
import com.sofka.com.cuenta_movimientos_service.repository.CuentaRepository;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
    public ManageResponse createResponse(String errorCode, String errorDescription, Object detalle) {
        ManageResponse responseDTO = new ManageResponse();
        responseDTO.setTipo(errorCode);
        responseDTO.setMensaje(errorDescription);
        responseDTO.setDetalle(detalle);
        return responseDTO;
    }

    public static Cuenta validarYObtenerCuenta(Cuenta cuenta) {
        if (!cuenta.isEstado()) {
            throw new IllegalArgumentException("La Cuenta no está activa. No se puede realizar la operación.");
        }
        return cuenta;
    }

    public static double revertirMovimientoAnterior(Cuenta cuenta, Movimiento movimiento) {
        double nuevoSaldo = cuenta.getSaldoInicial();
        if (movimiento.getTipoMovimiento() == TipoMovimiento.RETIRO) {
            nuevoSaldo += movimiento.getValor();
        } else if (movimiento.getTipoMovimiento() == TipoMovimiento.DEPOSITO) {
            nuevoSaldo -= movimiento.getValor();
        }
        return nuevoSaldo;
    }

    public static double calcularNuevoSaldo(double saldoActual, TipoMovimiento tipoMovimiento, double valor) {
        if (tipoMovimiento == TipoMovimiento.RETIRO) {
            double nuevoSaldo = saldoActual - valor;
            if (nuevoSaldo < 0) {
                throw new IllegalArgumentException("Saldo no disponible para realizar el retiro.");
            }
            return nuevoSaldo;
        }
        return saldoActual + valor;
    }

    public static void actualizarSaldoCuenta(Cuenta cuenta, double nuevoSaldo, CuentaRepository cuentaRepository) {
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
    }

    public static Movimiento crearOModificarMovimiento(
            Movimiento movimiento,
            CreateMovimientoDTO movimientoDTO,
            double saldo,
            Cuenta cuenta) {
        movimiento.setFecha(movimientoDTO.fecha());
        movimiento.setTipoMovimiento(movimientoDTO.tipoMovimiento());
        movimiento.setValor(movimientoDTO.valor());
        movimiento.setSaldo(saldo);
        movimiento.setCuenta(cuenta);
        return movimiento;
    }

}
