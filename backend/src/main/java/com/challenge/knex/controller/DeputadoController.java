package com.challenge.knex.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.knex.dto.DeputadoDTO;
import com.challenge.knex.model.Deputado;

import com.challenge.knex.service.DeputadoService;

@RestController
@RequestMapping("/api/deputado")
public class DeputadoController {

    private final DeputadoService deputadoService;

    private DeputadoDTO toDTO(Deputado deputado) {
    return new DeputadoDTO(
        deputado.getId(),
        deputado.getNome(),
        deputado.getUf(),
        deputado.getCpf(),
        deputado.getPartido()
        // Adicione outros campos do DTO se necessário
    );
}

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

    @GetMapping("/estado/{uf}")
    public ResponseEntity<List<DeputadoDTO>> listarPorUf(@PathVariable String uf) {
        List<Deputado> deputados = deputadoService.listarDeputadosUf(uf);
        
        if (deputados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<DeputadoDTO> dtos = deputados.stream()
            .map(this::toDTO)
            .toList();

        return ResponseEntity.ok(dtos);
    }
    
}
