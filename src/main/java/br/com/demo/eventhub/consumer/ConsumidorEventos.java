package br.com.demo.eventhub.consumer;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.azure.messaging.eventhubs.EventProcessorClientBuilder;
import com.azure.messaging.eventhubs.checkpointstore.blob.BlobCheckpointStore;
import com.azure.messaging.eventhubs.models.ErrorContext;
import com.azure.messaging.eventhubs.models.EventContext;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.storage.blob.BlobContainerAsyncClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConsumidorEventos {

    @Value("${spring.cloud.azure.eventhubs.consumer-0.connection-string-secret-name}")
    private String consumerSecretName;

    @Value("${spring.cloud.azure.eventhubs.consumer-0.consumer-group}")
    private String consumerGroup;

    @Value("${spring.cloud.azure.eventhubs.processor.checkpoint-store.connection-string-secret-name}")
    private String storageSecretName;

    @Value("${spring.cloud.azure.eventhubs.processor.checkpoint-store.container-name}")
    private String storageContainerName;

    @Autowired
    private SecretClient secretClient;

    @EventListener(ApplicationReadyEvent.class)
    private void initConsumidor() {
        String storageConnectionString = secretClient.getSecret(storageSecretName).getValue();
        BlobContainerAsyncClient blobContainerAsyncClient = new BlobContainerClientBuilder()
                .connectionString(storageConnectionString)
                .containerName(storageContainerName)
                .buildAsyncClient();

        String connectionString = secretClient.getSecret(consumerSecretName).getValue();
        new EventProcessorClientBuilder()
                .connectionString(connectionString)
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
