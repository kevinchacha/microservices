package com.sofka.com.cliente_persona_service.interfaces;
import com.sofka.com.cliente_persona_service.dto.response.GetUserDTO;
import com.sofka.com.cliente_persona_service.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientInterface {
    Page<GetUserDTO> findAllUsers(Pageable pageable);
    Cliente findUserById(Long id);
    GetUserDTO createUser(Cliente user);
    GetUserDTO updateUser(Long id, Cliente newUser);
    void deleteUser(Long id);

}
