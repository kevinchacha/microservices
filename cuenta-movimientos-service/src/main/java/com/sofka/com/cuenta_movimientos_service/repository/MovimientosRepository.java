package com.sofka.com.cuenta_movimientos_service.repository;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimiento, Long> {
}
