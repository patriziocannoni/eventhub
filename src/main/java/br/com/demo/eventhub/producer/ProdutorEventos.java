package br.com.demo.eventhub.producer;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.security.keyvault.secrets.SecretClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProdutorEventos {

    @Value("${spring.cloud.azure.eventhubs.producer-0.connection-string-secret-name}")
    private String secretName;

    @Autowired
    private SecretClient secretClient;

    private EventHubProducerClient producerClient;

    @EventListener(ApplicationReadyEvent.class)
    private void initProdutor() {
        String connectionString = secretClient.getSecret(secretName).getValue();
        producerClient = new EventHubClientBuilder()
                .connectionString(connectionString)
                .buildProducerClient();
    }

    public void produzirEvento(String message) {
        EventData eventData = new EventData(message);
        producerClient.send(Collections.singletonList(eventData));
        log.info("Mensagem enviada: {}", message);
    }

}
