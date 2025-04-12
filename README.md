# Exemplo de Spring Boot com Azure Event Hub

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
            "projectName": "eventhub"
        },
        {
            "type": "java",
            "name": "EventhubApplication 2",
            "request": "launch",
            "mainClass": "br.com.demo.eventhub.EventhubApplication",
            "projectName": "eventhub",
            "vmArgs": [
                "-Dserver.port=8090",
                "-Dspring.stream.bindings.consumer-0.consumer-group=consumer-group-2"
            ]
        }
    ]
}
````
