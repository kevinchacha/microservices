package com.sofka.com.cliente_persona_service.repository;
import com.sofka.com.cliente_persona_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByPersonaIdentificacion(Long id);
    void deleteByPersonaIdentificacion(Long id);


}
