# Knex Challenge - Backend

Este é o backend do desafio Knex, desenvolvido em Java com Spring Boot e PostgreSQL. O sistema permite importar dados de deputados e despesas via CSV, consultar deputados por estado, gerar relatórios de despesas e muito mais.

## Funcionalidades

- Importação de dados de deputados e despesas via arquivo CSV
- Consulta de deputados por UF (estado)
- Relatórios:
  - Somatório total de despesas
  - Somatório de despesas por deputado
  - Listagem de despesas por deputado, com filtros por fornecedor
- Integração com banco de dados PostgreSQL

## Endpoints principais

- `POST /api/deputado/upload-ceap`  
  Importa arquivo CSV de despesas/deputados

- `GET /api/deputado/estado/{uf}`  
  Lista deputados por estado

- `GET /relatorios/total-despesas`  
  Retorna o somatório de todas as despesas

- `GET /relatorios/deputados/{id}/total-despesas`  
  Retorna o somatório das despesas de um deputado específico

- `GET /relatorios/deputados/{id}/despesas?fornecedor=nome`  
  Lista despesas de um deputado, podendo filtrar por fornecedor

## Como rodar localmente

### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL

### Configuração do banco

Crie um banco chamado `knexchallenge` e configure o usuário e senha no `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/knexchallenge
spring.datasource.username=postgres
spring.datasource.password=postgres
```

A aplicação estará disponível em [http://localhost:3000](http://localhost:3000).

---

## Estrutura do Projeto

- `/src/main/java/com/challenge/knex/controller` — Controllers REST
- `/src/main/java/com/challenge/knex/service` — Regras de negócio
- `/src/main/java/com/challenge/knex/repository` — Repositórios JPA
- `/src/main/java/com/challenge/knex/model` — Entidades JPA
- `/src/main/java/com/challenge/knex/dto` — DTOs de resposta

---