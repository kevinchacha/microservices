package com.sofka.com.cuenta_movimientos_service.mq.producer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void solicitarCliente(Long clienteId) {
        rabbitTemplate.convertAndSend("cliente-queue", clienteId);
    }
}
