package com.sofka.com.cliente_persona_service.dto.response;

import java.io.Serializable;

public record GetUserDTO(
        Long id,
        String nombre,
        String genero,
        String direccion,
        String telefono,
        boolean estado
) implements Serializable {
}
