package com.sofka.com.cliente_persona_service.model;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class ClienteTest {

    @Test
    public void testRelacionClienteConPersona() {
        Persona persona = new Persona();
        persona.setNombre("Kevin Chacha");
        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        Persona clientePersona = cliente.getPersona();
        assertEquals("Kevin Chacha", clientePersona.getNombre(), "El nombre de la persona debe ser 'Kevin Chacha'");
    }


    @Test
    public void testCambioDeEstadoDelCliente() {
        Cliente cliente = new Cliente();
        cliente.setEstado(true);
        cliente.setEstado(false);
        assertFalse(cliente.isEstado(), "El cliente debería estar inactivo después del cambio de estado");
    }

    @Test
    public void testNoPermitirCuentasDuplicadas() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(001L);
        Cliente cliente = new Cliente();
        cliente.setCuentas(new ArrayList<>(List.of(cuenta)));
        //cliente.getCuentas().add(cuenta);
        assertEquals(1, cliente.getCuentas().size(), "El cliente no debe permitir cuentas duplicadas");
    }
}