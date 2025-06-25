package com.challenge.knex.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.knex.model.Deputado;
import com.challenge.knex.model.Despesa;
import com.challenge.knex.repository.DeputadoRepository;
import com.challenge.knex.repository.DespesaRepository;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReader;

@Service
public class DeputadoService {

    private final DeputadoRepository deputadoRepository;
    private final DespesaRepository despesaRepository;

    public DeputadoService(DeputadoRepository deputadoRepository, DespesaRepository despesaRepository) {
        this.deputadoRepository = deputadoRepository;
        this.despesaRepository = despesaRepository;
    }

    public String fileCSV(MultipartFile file){
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build();

            String[] linha;
            csvReader.readNext();

            System.out.println("Iniciando importação do CSV...");

            int maxLinhas = 5000;
            int contador = 0;

            while((linha = csvReader.readNext()) != null) {
                if (contador >= maxLinhas) {
                    System.out.println("Limite de " + maxLinhas + " linhas atingido. Parando importação.");
                    break;
                }
                contador++;

                System.out.println("Linha lida: " + Arrays.toString(linha));
                if(linha.length < 27) {
                    System.out.println("Linha ignorada por ter menos de 27 colunas: " + Arrays.toString(linha));
                    continue;
                }
                String cpf = linha[1];
                if(cpf == null || cpf.isBlank()) {
                    System.out.println("Linha ignorada por CPF vazio: " + Arrays.toString(linha));
                    continue;
                }

                String nome = linha[0];
                String uf = linha[5];
                String partido = linha [6];
                
                String fornecedor = linha[12];
                String dataEmissaoStr = linha[17];
                String valorLiquidoStr = linha[20];
                String urlDocumento = linha[26];

                LocalDateTime dataEmissao;
                try{
                    dataEmissao = LocalDateTime.parse(dataEmissaoStr + "T00:00:00");
                } catch (Exception e) {
                    dataEmissao = null;
                }

                Double valorLiquido;
                try {
                    valorLiquido = Double.parseDouble(valorLiquidoStr.replace(",", "."));
                } catch (Exception e) {
                    valorLiquido = null;
                }
                
                Deputado deputado = deputadoRepository.findByCpf(cpf)
                    .orElseGet(() -> {
                        Deputado novo = new Deputado();
                        novo.setCpf(cpf);
                        novo.setNome(nome);
                        novo.setUf(uf);
                        novo.setPartido(partido);
                        return deputadoRepository.save(novo);
                    });

                Despesa despesa = new Despesa();
                despesa.setDeputado(deputado);
                despesa.setDataEmissao(dataEmissao);
                despesa.setFornecedor(fornecedor);
                despesa.setValorLiquido(valorLiquido);
                despesa.setUrlDocumento(urlDocumento);

                despesaRepository.save(despesa);
            }

            return "Importação concluída com sucesso.";

        } catch (Exception e) {
            return "Erro ao procesar CSV: " + e.getMessage();
        }
    }

}
