package com.challenge.knex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.knex.model.Deputado;

@Repository
public interface DeputadoRepository extends JpaRepository<Deputado , Long>{

    Optional<Deputado> findByCpf(String cpf);

    List<Deputado> findByUfIgnoreCase(String uf);

}
