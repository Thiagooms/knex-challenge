package com.challenge.knex.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

            int maxLinhas = 5000;
            int contador = 0;

            while((linha = csvReader.readNext()) != null) {
                if (contador >= maxLinhas) {
                    break;
                }
                contador++;
                if(linha.length < 27) {
                    continue;
                }
                String cpf = linha[1];
                if(cpf == null || cpf.isBlank()) {
                    continue;
                }

                String nome = linha[0];
                String uf = linha[5];
                String partido = linha [6];
                
                String fornecedor = linha[12];
                String dataEmissaoStr = linha[16];
                String valorLiquidoStr = linha[19];
                String urlDocumento = linha[31];

                LocalDateTime dataEmissao;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    dataEmissao = LocalDateTime.parse(dataEmissaoStr, formatter);
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

    public List<Deputado> listarDeputadosUf(String uf) {
        return deputadoRepository.findByUfIgnoreCase(uf);
    }
}
