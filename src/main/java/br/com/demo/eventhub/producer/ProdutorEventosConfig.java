package br.com.demo.eventhub.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;

@Configuration
public class ProdutorEventosConfig {

    private static final String EVENT_HUB_NAME = "demo-01";

    private static final String CONNECTION_STRING = "Endpoint=sb://demo-eventhub-patri.servicebus.windows.net/;SharedAccessKeyName=producer;SharedAccessKey=;EntityPath="
            .concat(EVENT_HUB_NAME);

    @Bean
    public EventHubProducerClient eventHubProducerClient() {
        return new EventHubClientBuilder()
                .connectionString(CONNECTION_STRING)
                .buildProducerClient();
    }

}
