package com.challenge.knex.dto;

import com.challenge.knex.model.Despesa;
import java.time.LocalDateTime;

public record DespesaDTO(
    LocalDateTime dataEmissao,
    String txtFornecedor,
    Double vlrLiquido,
    String urlDocumento
) {
    public static DespesaDTO fromEntity(Despesa d) {
        return new DespesaDTO(
            d.getDataEmissao(),
            d.getFornecedor(),
            d.getValorLiquido(),
            d.getUrlDocumento()
        );
    }
}
