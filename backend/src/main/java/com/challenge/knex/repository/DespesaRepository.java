package com.challenge.knex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.knex.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long>{

    @Query("SELECT SUM(d.valorLiquido) FROM Despesa d")
    Double somarTodasDespesas();

    @Query("SELECT SUM(d.valorLiquido) FROM Despesa d WHERE d.deputado.id = :deputadoId")
    Double somarDespesasPorId(@Param("deputadoId") Long deputadoId);

    List<Despesa> findByDeputadoId(Long deputadoId);

    List<Despesa> findByDeputadoIdAndFornecedorContainingIgnoreCase(Long deputadoId, String fornecedor);

}