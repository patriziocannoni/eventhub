package br.com.demo.eventhub.producer;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubProducerClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProdutorEventos {
    
    @Autowired
    private EventHubProducerClient producerClient;

    public void produzirEvento(String message) {
        EventData eventData = new EventData(message);
        producerClient.send(Collections.singletonList(eventData));
        log.info("Mensagem enviada: {}", message);
    }

}
