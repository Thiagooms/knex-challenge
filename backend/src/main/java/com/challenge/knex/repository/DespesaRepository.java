package com.challenge.knex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.knex.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long>{

}