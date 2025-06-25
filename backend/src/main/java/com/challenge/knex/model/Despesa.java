package com.challenge.knex.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "despesa")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime dataEmissao;
    private String fornecedor;
    private Double valorLiquido;
    private String urlDocumento;

    @ManyToOne
    @JoinColumn(name = "deputado_id", nullable = false)
    private Deputado deputado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Double getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(Double valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public String getUrlDocumento() {
        return urlDocumento;
    }

    public void setUrlDocumento(String urlDocumento) {
        this.urlDocumento = urlDocumento;
    }

    public Deputado getDeputado() {
        return deputado;
    }

    public void setDeputado(Deputado deputado) {
        this.deputado = deputado;
    }
}
