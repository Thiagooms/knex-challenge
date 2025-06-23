package com.challenge.knex.dto;

public record DeputadoDTO(

    Long id, 
    String name, 
    String uf,
    String cpf,
    String partido

){}