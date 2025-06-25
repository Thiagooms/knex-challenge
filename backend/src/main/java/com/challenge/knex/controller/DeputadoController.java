package com.challenge.knex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.knex.service.DeputadoService;

@RestController
@RequestMapping("/api/deputado")
public class DeputadoController {

    private final DeputadoService deputadoService;

    public DeputadoController(DeputadoService deputadoService) {
        this.deputadoService = deputadoService;
    }

    @PostMapping("/upload-ceap")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        String resultado = deputadoService.fileCSV(file);

        if(resultado.toLowerCase().contains("erro")) {
            return ResponseEntity.internalServerError().body(resultado);
        }

        return ResponseEntity.ok(resultado);
    }
    
}
