package com.sofka.com.cliente_persona_service.mq.consumer;
import com.sofka.com.cliente_persona_service.model.Cliente;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "cliente-queue")
    public Object obtenerCliente(Long clienteId) {
        Cliente cliente= obtenerClienteDesdeBD(clienteId);
        rabbitTemplate.convertAndSend("response-queue", cliente);
        return  cliente;
    }
    private Cliente obtenerClienteDesdeBD(Long clienteId) {
        Cliente cliente = new Cliente();
        cliente.setEstado(false);
        cliente.setContrasena("Kevin Test");
        return cliente;
    }
}
