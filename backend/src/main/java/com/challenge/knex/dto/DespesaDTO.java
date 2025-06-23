package com.challenge.knex.dto;

import java.time.LocalDate;

public record DespesaDTO(

    Long id,
    LocalDate dataEmissao,
    String fornecedor,
    Double valorLiquido,
    String urlDocumento

){}
