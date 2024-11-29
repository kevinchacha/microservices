package com.sofka.com.cuenta_movimientos_service.mq.consumer;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class ResponseListener {

    private final Map<String, CompletableFuture<String>> responseMap = new ConcurrentHashMap<>();

    public CompletableFuture<String> registerResponse(String requestId) {
        CompletableFuture<String> future = new CompletableFuture<>();
        responseMap.put(requestId, future);
        return future;
    }

    @RabbitListener(queues = "response-queue")
    public void handleResponse(Map<String, String> response) {
        String requestId = response.get("requestId");
        String clienteName = response.get("clienteName");
        CompletableFuture<String> future = responseMap.remove(requestId);
        if (future != null) {
            future.complete(clienteName);
        }
    }
}
