package br.com.demo.eventhub.consumer;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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

    @Value("${spring.stream.bindings.consume-in-0.connection-string}")
    private String consumidorConnectionString;

    @Value("${spring.stream.bindings.consume-in-0.consumer-group}")
    private String consumerGroup;

    private static final String STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=demostorageaccountpatri;AccountKey=;EndpointSuffix=core.windows.net";

    private static final String CONTAINER_NAME = "demo-eventhub-checkpoint";

    @EventListener(ApplicationReadyEvent.class)
    private void initConsumidor() {
        BlobContainerAsyncClient blobContainerAsyncClient = new BlobContainerClientBuilder()
                .connectionString(STORAGE_CONNECTION_STRING)
                .containerName(CONTAINER_NAME)
                .buildAsyncClient();

        new EventProcessorClientBuilder()
                .connectionString(consumidorConnectionString)
                .consumerGroup(consumerGroup)
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
