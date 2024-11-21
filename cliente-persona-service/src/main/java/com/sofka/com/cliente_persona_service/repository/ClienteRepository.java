package com.sofka.com.cliente_persona_service.repository;

import com.sofka.com.cliente_persona_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
