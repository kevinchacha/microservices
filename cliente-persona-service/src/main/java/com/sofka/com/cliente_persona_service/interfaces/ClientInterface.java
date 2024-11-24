package com.sofka.com.cliente_persona_service.interfaces;
import com.sofka.com.cliente_persona_service.dto.request.CreateUserDTO;
import com.sofka.com.cliente_persona_service.dto.response.GetUserDTO;
import com.sofka.com.cliente_persona_service.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientInterface {
    Page<GetUserDTO> findAllUsers(Pageable pageable);
    Cliente findUserById(Long id);
    GetUserDTO createUser( CreateUserDTO createUserDTO);
    GetUserDTO updateUser(Long id, CreateUserDTO newUser);
    void deleteUser(Long id);

}
