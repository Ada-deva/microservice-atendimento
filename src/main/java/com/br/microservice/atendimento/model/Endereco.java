package com.br.microservice.atendimento.model;
import com.br.microservice.atendimento.exception.InformacaoInvalidaException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
@Slf4j
@Data
@Entity
@Table(name = "Endereco")
@NoArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String logradouro;

    @Column(nullable = false)
    private String numero;
    private String bairro;

    @Column(nullable = false)
    private String cep;
    private String cidade;
    private String estado;

    public Endereco(Long id, String logradouro, String numero, String bairro, String cep, String cidade, String estado) throws InformacaoInvalidaException {
        Id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;

        if(cep.replaceAll("[\\.-]", "").isBlank()) {
            log.warn("---CEP vazio---");
            throw new InformacaoInvalidaException("CEP inválido!");
        } else {
            log.info("---CEP não é nulo---");
            this.cep = cep;
        }
        this.cidade = cidade;
        this.estado = estado;
    }
}
