package com.sofka.com.cliente_persona_service.dto.response;

public record GetUserDTO(
        Long id,
        Long identificacion,
        String nombre,
        String genero,
        String direccion,
        String telefono,
        boolean estado
)  {
}
