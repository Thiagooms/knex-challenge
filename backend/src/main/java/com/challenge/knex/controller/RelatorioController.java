package com.challenge.knex.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.knex.dto.DespesaDTO;
import com.challenge.knex.model.Despesa;
import com.challenge.knex.repository.DespesaRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final DespesaRepository despesaRepository;

    public RelatorioController(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    @GetMapping("/total-despesas")
    public ResponseEntity<Double> getTotalDespesas() {
        Double total = despesaRepository.somarTodasDespesas();
        return ResponseEntity.ok(total != null ? total : 0.0);
    }

    @GetMapping("/deputados/{id}/total-despesas")
    public ResponseEntity<Double> despesasDeputadosTotal(@PathVariable Long id) {
        Double total = despesaRepository.somarDespesasPorId(id);
        return ResponseEntity.ok(total != null ? total : 0.0);
    }

    @GetMapping("/deputados/{id}/despesas")
    public ResponseEntity<List<DespesaDTO>> listarDespesasDeputado(
        @PathVariable Long id,
        @RequestParam(required = false) String fornecedor) {

    List<Despesa> despesas;

    if (fornecedor != null && !fornecedor.isEmpty()) {
        despesas = despesaRepository.findByDeputadoIdAndFornecedorContainingIgnoreCase(id, fornecedor);
    } else {
        despesas = despesaRepository.findByDeputadoId(id);
    }

    List<DespesaDTO> dtos = new ArrayList<>();
    for (Despesa d : despesas) {
        dtos.add(DespesaDTO.fromEntity(d));
    }

    return ResponseEntity.ok(dtos);
}

}
