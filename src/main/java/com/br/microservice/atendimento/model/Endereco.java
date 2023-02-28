package com.br.microservice.atendimento.model;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String logradouro;
    private String numero;
    private String bairro;

    @Column(nullable = false)
    private String cep;
    private String cidade;
    private String estado;
}
