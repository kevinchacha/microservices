package com.sofka.com.cliente_persona_service.repository;

import com.sofka.com.cliente_persona_service.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
