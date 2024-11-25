package com.sofka.com.cliente_persona_service.service;
import com.sofka.com.cliente_persona_service.dto.request.CreateUserDTO;
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
            return clienteRepository.findByPersonaIdentificacion(id)
                    .orElseThrow(()-> new NoSuchElementException("Cliente no encontrado con ID: " + id));
    }

    @Transactional
    @Override
    public GetUserDTO createUser(CreateUserDTO createUserDTO) {
        Persona persona = new Persona(
                createUserDTO.nombre(),
                createUserDTO.genero(),
                createUserDTO.edad(),
                createUserDTO.identificacion(),
                createUserDTO.direccion(),
                createUserDTO.telefono()
        );
        Persona nuevaPersona = personaRepository.save(persona);
        Cliente cliente = new Cliente();
        cliente.setPersona(nuevaPersona);
        cliente.setContrasena(createUserDTO.contrasena());
        cliente.setEstado(true);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return UserMapper.toUserDto(clienteGuardado);
    }

    @Transactional
    @Override
    public GetUserDTO updateUser(Long id, CreateUserDTO newUser) {
        Cliente cliente = findUserById(id);
        Persona persona = cliente.getPersona();
        updatePersonaIfNecessary(persona, newUser);
        cliente.setContrasena(newUser.contrasena());
        cliente.setEstado(newUser.estado());
        clienteRepository.save(cliente);
        return UserMapper.toUserDto(cliente);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        Cliente cliente = findUserById(id);
        if(!cliente.isEstado()){
            throw new IllegalArgumentException("El cliente no se encuentra activo.");
        }
        cliente.setEstado(false);
        clienteRepository.save(cliente);
    }

    private void updatePersonaIfNecessary(Persona existingPersona, CreateUserDTO clienteUpdateDTO) {
        boolean isUpdated = false;

        if (!existingPersona.getNombre().equals(clienteUpdateDTO.nombre())) {
            existingPersona.setNombre(clienteUpdateDTO.nombre());
            isUpdated = true;
        }
        if (!existingPersona.getGenero().equals(clienteUpdateDTO.genero())) {
            existingPersona.setGenero(clienteUpdateDTO.genero());
            isUpdated = true;
        }
        if (existingPersona.getEdad() != clienteUpdateDTO.edad()) {
            existingPersona.setEdad(clienteUpdateDTO.edad());
            isUpdated = true;
        }
        if (!existingPersona.getIdentificacion().equals(clienteUpdateDTO.identificacion())) {
            existingPersona.setIdentificacion(clienteUpdateDTO.identificacion());
            isUpdated = true;
        }
        if (!existingPersona.getDireccion().equals(clienteUpdateDTO.direccion())) {
            existingPersona.setDireccion(clienteUpdateDTO.direccion());
            isUpdated = true;
        }
        if (!existingPersona.getTelefono().equals(clienteUpdateDTO.telefono())) {
            existingPersona.setTelefono(clienteUpdateDTO.telefono());
            isUpdated = true;
        }

        if (isUpdated) {
            personaRepository.save(existingPersona);
        }
    }
}
