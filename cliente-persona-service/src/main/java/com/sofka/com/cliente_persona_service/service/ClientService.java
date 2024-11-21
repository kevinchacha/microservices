package com.sofka.com.cliente_persona_service.service;
import com.sofka.com.cliente_persona_service.dto.response.GetUserDTO;
import com.sofka.com.cliente_persona_service.interfaces.ClientInterface;
import com.sofka.com.cliente_persona_service.mapper.UserMapper;
import com.sofka.com.cliente_persona_service.model.Cliente;
import com.sofka.com.cliente_persona_service.model.Persona;
import com.sofka.com.cliente_persona_service.repository.ClienteRepository;
import com.sofka.com.cliente_persona_service.repository.PersonaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class ClientService implements ClientInterface {
    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    public ClientService(ClienteRepository clienteRepository, PersonaRepository personaRepository) {
        this.clienteRepository = clienteRepository;
        this.personaRepository = personaRepository;
    }
    @Override
    public Page<GetUserDTO> findAllUsers(Pageable pageable) {
        Page<Cliente> clientes= clienteRepository.findAll(pageable);
        return clientes.map(UserMapper::toUserDto);
    }

    @Override
    public Cliente findUserById(Long id) {
            return clienteRepository.findById(id)
                    .orElseThrow(()-> new NoSuchElementException("Cliente no encontrado con ID: " + id));
    }

    @Transactional
    @Override
    public GetUserDTO createUser(Cliente user) {
        Persona persona = user.getPersona();
        Persona nuevaPersona = personaRepository.save(persona);
        user.setPersona(nuevaPersona);
        Cliente cliente= clienteRepository.save(user);
        return UserMapper.toUserDto(cliente);
    }

    @Transactional
    @Override
    public GetUserDTO updateUser(Long id, Cliente newUser) {
        Cliente cliente = findUserById(id);
        Persona persona = cliente.getPersona();
        updatePersonaIfNecessary(persona, newUser.getPersona());
        cliente.setContrasena(newUser.getContrasena());
        cliente.setEstado(newUser.isEstado());
        clienteRepository.save(cliente);
        return UserMapper.toUserDto(cliente);
    }

    @Override
    public void deleteUser(Long id) {
        findUserById(id);
        clienteRepository.deleteById(id);
    }

    private void updatePersonaIfNecessary(Persona existingPersona, Persona newPersona) {
        boolean isUpdated = false;
        if (!existingPersona.getNombre().equals(newPersona.getNombre())) {
            existingPersona.setNombre(newPersona.getNombre());
            isUpdated = true;
        }
        if (!existingPersona.getGenero().equals(newPersona.getGenero())) {
            existingPersona.setGenero(newPersona.getGenero());
            isUpdated = true;
        }
        if (!existingPersona.getEdad().equals(newPersona.getEdad())) {
            existingPersona.setEdad(newPersona.getEdad());
            isUpdated = true;
        }
        if (!existingPersona.getIdentificacion().equals(newPersona.getIdentificacion())) {
            existingPersona.setIdentificacion(newPersona.getIdentificacion());
            isUpdated = true;
        }
        if (!existingPersona.getDireccion().equals(newPersona.getDireccion())) {
            existingPersona.setDireccion(newPersona.getDireccion());
            isUpdated = true;
        }
        if (!existingPersona.getTelefono().equals(newPersona.getTelefono())) {
            existingPersona.setTelefono(newPersona.getTelefono());
            isUpdated = true;
        }
        if (isUpdated) {
            personaRepository.save(existingPersona);
        }
    }

}
