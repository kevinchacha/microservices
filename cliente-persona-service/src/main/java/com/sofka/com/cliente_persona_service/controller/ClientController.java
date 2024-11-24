package com.sofka.com.cliente_persona_service.controller;
import com.sofka.com.cliente_persona_service.dto.request.CreateUserDTO;
import com.sofka.com.cliente_persona_service.dto.response.GetUserDTO;
import com.sofka.com.cliente_persona_service.model.Cliente;
import com.sofka.com.cliente_persona_service.model.ManageResponse;
import com.sofka.com.cliente_persona_service.service.ClientService;
import com.sofka.com.cliente_persona_service.utils.CommonUtils;
import com.sofka.com.cliente_persona_service.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    private final ClientService clienteService;
    private CommonUtils utils;

    public ClientController(ClientService clienteService,CommonUtils utils) {
        this.clienteService = clienteService;
        this.utils = utils;
    }
    @GetMapping
    public Page<GetUserDTO> getAllUsers(Pageable pageable) {
        return clienteService.findAllUsers(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getUserById(@PathVariable Long id) {
        Cliente user = clienteService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            GetUserDTO userCreated = clienteService.createUser(createUserDTO);
            return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
        } catch (HttpMessageNotReadableException e) {
            throw new HttpMessageNotReadableException("Violación de datos: " + e.getMessage());
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Violación de restricción de datos: " + e.getMessage());
        }catch (RuntimeException e) {
            return new ResponseEntity<>(utils.createResponse(Constants.INTERNAL_SERVER_ERROR, e.toString(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @Valid @RequestBody CreateUserDTO user) {
        GetUserDTO updatedUser = clienteService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ManageResponse> deleteUser(@PathVariable Long id) {
        clienteService.deleteUser(id);
        return ResponseEntity.ok(utils.createResponse(Constants.OK, Constants.CLIENTE_ELIMINADO + id, null));
    }



}
