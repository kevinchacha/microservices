package com.sofka.com.cliente_persona_service;
import com.sofka.com.cliente_persona_service.dto.request.CreateUserDTO;
import com.sofka.com.cliente_persona_service.dto.response.GetUserDTO;
import com.sofka.com.cliente_persona_service.model.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ClientePersonaServiceApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	@Test
	public void testCrearYObtenerCliente() {
		CreateUserDTO createUserDTO = new CreateUserDTO(
					"Kevin Chacha",
				"Masculino",
				30,
				1400878542L,
				"Calle 123",
				"0987654321",
				"12345",
				true
		);

		ResponseEntity<GetUserDTO> postResponse = restTemplate.postForEntity("http://localhost:8080/api/v1/clientes", createUserDTO, GetUserDTO.class);

		assertEquals(HttpStatus.CREATED, postResponse.getStatusCode(), "El cliente debería haberse creado exitosamente");
		assertNotNull(postResponse.getBody(), "El cuerpo de la respuesta no debe ser nulo");
		assertEquals("Kevin Chacha", postResponse.getBody().nombre(), "El nombre del cliente creado debe coincidir");

		Long clienteId = createUserDTO.identificacion();
		ResponseEntity<Cliente> getResponse = restTemplate.getForEntity("http://localhost:8080/api/v1/clientes/" + clienteId, Cliente.class);

		assertEquals(HttpStatus.OK, getResponse.getStatusCode(), "El cliente debería haberse recuperado exitosamente");
		assertNotNull(getResponse.getBody(), "El cuerpo de la respuesta no debe ser nulo");
		assertEquals("Kevin Chacha", getResponse.getBody().getPersona().getNombre(), "El nombre del cliente recuperado debe coincidir");
		assertEquals("0987654321", getResponse.getBody().getPersona().getTelefono(), "El telefono del cliente recuperado debe coincidir");
		assertEquals("Masculino", getResponse.getBody().getPersona().getGenero(), "El género del cliente recuperado debe coincidir");
	}

}
