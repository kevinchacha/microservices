package com.sofka.com.cuenta_movimientos_service.repository;
import com.sofka.com.cuenta_movimientos_service.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaNumeroCuenta(Long id);

}
