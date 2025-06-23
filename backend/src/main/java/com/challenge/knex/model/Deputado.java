package com.challenge.knex.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "deputado")
public class Deputado {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String uf;

    @Column (unique = true)
    private String cpf;
    private String partido;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf(){
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCpf(){
        return cpf;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }
    
    @OneToMany(mappedBy = "deputado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Despesa> despesas;

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }
        
}
