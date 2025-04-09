package br.com.demo.eventhub.producer;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProdutorEventos {

    @Value("${spring.stream.bindings.supply-out-0.connection-string}")
    private String produtorConnectionString;

    private EventHubProducerClient producerClient;

    @EventListener(ApplicationReadyEvent.class)
    private void initProdutor() {
        producerClient = new EventHubClientBuilder()
                .connectionString(produtorConnectionString)
                .buildProducerClient();
    }

    public void produzirEvento(String message) {
        EventData eventData = new EventData(message);
        producerClient.send(Collections.singletonList(eventData));
        log.info("Mensagem enviada: {}", message);
    }

}
