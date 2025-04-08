package br.com.demo.eventhub.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/eventhub/producer")
public class ProdutorEventosController {
    
    @Autowired
    private ProdutorEventos producer;

    @PutMapping("/put")
    public ResponseEntity<Void> put(@RequestBody String message) {
        producer.produzirEvento(message);
        return ResponseEntity.ok().build();
    }

}
