package com.sofka.com.cliente_persona_service.mapper;
import com.sofka.com.cliente_persona_service.dto.response.GetUserDTO;
import com.sofka.com.cliente_persona_service.model.Cliente;

public class UserMapper {
    public static GetUserDTO toUserDto(Cliente cliente){
        if(cliente == null) return  null;
        return new GetUserDTO(
                cliente.getId(),
                cliente.getPersona().getNombre(),
                cliente.getPersona().getGenero(),
                cliente.getPersona().getDireccion(),
                cliente.getPersona().getTelefono(),
                cliente.isEstado()
        );
    }
}
