package com.br.microservice.atendimento.model;

import com.br.microservice.atendimento.exception.InformacaoInvalidaException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
@Slf4j
@Data
@Entity
@Table(name = "Cliente")
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String celular;

    @Column(unique = true)
    private String email;


    @Column(unique = true, nullable = false)
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @CreatedDate
    private LocalDateTime dataCadastro;

    public Cliente(long id, String nome, String celular, String email, String cpf, Endereco endereco, LocalDateTime dataCadastro) throws InformacaoInvalidaException {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        log.info("---Checando  CPF do Cliente---");
        if(cpf.replaceAll("[\\.-]", "").isBlank()) {
            log.warn("---CPF vazio---");
            throw new InformacaoInvalidaException("CPF inválido!");
        } else {
            log.info("---CPF não é nulo---");
            this.cpf = cpf;
        }
        this.endereco = endereco;
        this.dataCadastro = dataCadastro;
    }

    public void setCpf(String cpf) throws InformacaoInvalidaException {
        log.info("---Checando  CPF do Cliente---");
        if(cpf.replaceAll("[\\.-]", "").isBlank()) {
            log.warn("---CPF vazio---");
            throw new InformacaoInvalidaException("CPF inválido!");
        } else {
            log.info("---CPF não é nulo---");
            this.cpf = cpf;
        }
    }
}
