package br.com.demo.eventhub.consumer;

import java.util.function.Consumer;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventProcessorClientBuilder;
import com.azure.messaging.eventhubs.checkpointstore.blob.BlobCheckpointStore;
import com.azure.messaging.eventhubs.models.ErrorContext;
import com.azure.messaging.eventhubs.models.EventContext;
import com.azure.storage.blob.BlobContainerAsyncClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConsumidorEventos {

    private static final String EVENT_HUB_NAME = "demo-01";

    private static final String CONNECTION_STRING = "Endpoint=sb://demo-eventhub-patri.servicebus.windows.net/;SharedAccessKeyName=consumer;SharedAccessKey=;EntityPath="
            .concat(EVENT_HUB_NAME);

    private static final String STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=demostorageaccountpatri;AccountKey=;EndpointSuffix=core.windows.net";

    private static final String CONTAINER_NAME = "demo-eventhub-checkpoint";

    @EventListener(ApplicationReadyEvent.class)
    private void startConsumer() {
        BlobContainerAsyncClient blobContainerAsyncClient = new BlobContainerClientBuilder()
                .connectionString(STORAGE_CONNECTION_STRING)
                .containerName(CONTAINER_NAME)
                .buildAsyncClient();

        new EventProcessorClientBuilder()
                .connectionString(CONNECTION_STRING, EVENT_HUB_NAME)
                .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                .processEvent(PROCESSA_EVENTO)
                .processError(PROCESSA_ERRO)
                .checkpointStore(new BlobCheckpointStore(blobContainerAsyncClient))
                .buildEventProcessorClient()
                .start();
        
        log.info("Consumer inicializado");
    }

    private static final Consumer<EventContext> PROCESSA_EVENTO = new Consumer<EventContext>() {

        @Override
        public void accept(EventContext eventContext) {
            log.info("Mensagem recebida: {}", eventContext.getEventData().getBodyAsString());
        }

    };

    private static final Consumer<ErrorContext> PROCESSA_ERRO = new Consumer<ErrorContext>() {

        @Override
        public void accept(ErrorContext errorContext) {
            Throwable throwable = errorContext.getThrowable();
            log.error("Erro recebido: {}", throwable.getMessage(), errorContext.getThrowable());
        }

    };

}
