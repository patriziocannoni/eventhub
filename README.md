# Exemplo de Spring Boot com Azure Event Hub

Esta aplicação mostra como utilizar um Azure Event Hub para enviar e receber mensagens utilizando Spring Boot.

## Configuração na Azure

### Configuração do Azure App no Microsoft Entra

No Azure Microsoft Entra selecionar App registrations para registrar uma nova App.<br/>
Esse registro vai criar um Client ID.<br/>
Para criar uma Client Secret escolher a opção Certificates & secrets dentro do menu da aplicação criada.

### Configuração do Azure Event Hub

TODO

### Configuração do Azure Key Vault

TODO

### Configuração VSCode

````
{
    // Use IntelliSense to learn about possible attributes.
    // Hover to view descriptions of existing attributes.
    // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "EventhubApplication 1",
            "request": "launch",
            "mainClass": "br.com.demo.eventhub.EventhubApplication",
            "projectName": "eventhub",
            "vmArgs": [
                "-DAZURE_TENANT_ID=xxxxx",
                "-DAZURE_APP_CLIENT_ID=xxxxx",
                "-DAZURE_APP_CLIENT_SECRET=xxxxx"
            ]
        },
        {
            "type": "java",
            "name": "EventhubApplication 2",
            "request": "launch",
            "mainClass": "br.com.demo.eventhub.EventhubApplication",
            "projectName": "eventhub",
            "vmArgs": [
                "-Dserver.port=8090",
                "-Dspring.stream.bindings.consume-in-0.consumer-group=consumer-group-2",
                "-DAZURE_TENANT_ID=xxxxx",
                "-DAZURE_APP_CLIENT_ID=xxxxx",
                "-DAZURE_APP_CLIENT_SECRET=xxxxx"
            ]
        }
    ]
}
````
