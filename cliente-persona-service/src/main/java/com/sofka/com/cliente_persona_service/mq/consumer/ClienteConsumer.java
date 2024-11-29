package com.sofka.com.cliente_persona_service.mq.consumer;
import com.sofka.com.cliente_persona_service.model.Cliente;
import com.sofka.com.cliente_persona_service.repository.ClienteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
@Component
public class ClienteConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ClienteRepository clienteRepository;

    @RabbitListener(queues = "cliente-queue")
    @SendTo("response-queue")
    public Map<String, String> obtenerCliente(Map<String, Object> request) {
        Object clienteIdObj = request.get("clienteId");
        Long clienteId = clienteIdObj instanceof Integer
                ? ((Integer) clienteIdObj).longValue()
                : (Long) clienteIdObj;
        String requestId = (String) request.get("requestId");
        try {
            Cliente cliente = obtenerClienteDesdeBD(clienteId);
            String clienteName = cliente.getPersona().getNombre();
            Map<String, String> response = new HashMap<>();
            response.put("requestId", requestId);
            response.put("clienteName", clienteName);
            return response;
        } catch (NoSuchElementException e) {
            Map<String, String> response = new HashMap<>();
            response.put("requestId", requestId);
            response.put("clienteName", "CLIENT_NOT_FOUND");
            return response;
        }
    }
    private Cliente obtenerClienteDesdeBD(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con ID: " + clienteId));
    }}
