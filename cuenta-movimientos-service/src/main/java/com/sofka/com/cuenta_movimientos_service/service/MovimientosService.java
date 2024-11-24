package com.sofka.com.cuenta_movimientos_service.service;
import com.sofka.com.cuenta_movimientos_service.interfaces.MovimientosInterface;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;
import com.sofka.com.cuenta_movimientos_service.repository.MovimientosRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientosService implements MovimientosInterface {

    private final MovimientosRepository movimientosRepository;
    public MovimientosService(MovimientosRepository movimientosRepository) {
        this.movimientosRepository = movimientosRepository;
    }
    @Override
    public List<Movimiento> findAllMovimientos() {
        return movimientosRepository.findAll();
    }

    @Override
    public Optional<Movimiento> findMovimientoById(Long id) {
        return movimientosRepository.findById(id);
    }

    @Override
    public Movimiento createMovimiento(Movimiento movimiento) {
        return movimientosRepository.save(movimiento);
    }

    @Override
    public Movimiento updateMovimiento(Long id, Movimiento newMovimiento) {
        Optional<Movimiento> existeMovimiento = movimientosRepository.findById(id);
        if (existeMovimiento.isPresent()) {
            Movimiento movimiento = existeMovimiento.get();
//            movimiento.setFecha(newMovimiento.getFecha());
//            movimiento.setTipoMovimiento(newMovimiento.getTipoMovimiento());
//            movimiento.setValor(newMovimiento.getValor());
//            movimiento.setSaldo(newMovimiento.getSaldo());
//            movimiento.setCuenta(newMovimiento.getCuenta());
            return movimientosRepository.save(movimiento);
        } else {
            throw new RuntimeException("Movimiento con id: " +id+" no encontrado!!");
        }
    }

    @Override
    public void deleteMovimiento(Long id) {
        Optional<Movimiento> existeMovimiento = movimientosRepository.findById(id);
        if (existeMovimiento.isPresent()) {
            movimientosRepository.deleteById(id);
        }else {
            throw new RuntimeException("Movimiento con id: " +id+" no encontrada!!");
        }

    }
}
