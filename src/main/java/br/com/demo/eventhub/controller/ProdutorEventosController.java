package br.com.demo.eventhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.demo.eventhub.producer.ProdutorEventos;

@RestController
@RequestMapping("/api/v1/eventhub/producer")
public class ProdutorEventosController {
    
    @Autowired
    private ProdutorEventos produtorEventos;

    @PutMapping("/put")
    public ResponseEntity<Void> put(@RequestBody String message) {
        produtorEventos.produzirEvento(message);
        return ResponseEntity.ok().build();
    }

}
