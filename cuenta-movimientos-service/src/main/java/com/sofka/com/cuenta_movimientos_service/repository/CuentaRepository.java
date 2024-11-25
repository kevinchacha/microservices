package com.sofka.com.cuenta_movimientos_service.repository;
import com.sofka.com.cuenta_movimientos_service.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumeroCuenta(Long id);
    void deleteByNumeroCuenta(Long id);


}
