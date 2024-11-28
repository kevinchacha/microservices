package com.sofka.com.cuenta_movimientos_service.mq.producer;
import com.sofka.com.cuenta_movimientos_service.mq.consumer.ResponseListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Component;

@Component
public class ClienteProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ResponseListener responseListener;

    public ClienteProducer(RabbitTemplate rabbitTemplate, ResponseListener responseListener) {
        this.rabbitTemplate = rabbitTemplate;
        this.responseListener = responseListener;
    }

    public CompletableFuture<String> solicitarCliente(Long clienteId) {
        String requestId = UUID.randomUUID().toString();
        Map<String, Object> message = new HashMap<>();
        message.put("clienteId",clienteId);
        message.put("requestId", requestId);
        CompletableFuture<String> future = responseListener.registerResponse(requestId);
        rabbitTemplate.convertAndSend("cliente-queue", message);
        return future;
    }
}
