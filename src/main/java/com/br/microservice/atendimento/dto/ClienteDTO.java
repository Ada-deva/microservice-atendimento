package com.br.microservice.atendimento.dto;

import com.br.microservice.atendimento.exception.InformacaoInvalidaException;
import com.br.microservice.atendimento.model.Cliente;
import com.br.microservice.atendimento.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private long id;
    private String nome;
    private String celular;
    private String email;
    private String cpf;
    private Endereco endereco;
    private LocalDateTime dataCadastro;

    public ClienteDTO of(Cliente cliente) {
        return ClienteDTO
                .builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .celular(cliente.getCelular())
                .email(cliente.getEmail())
                .cpf(cliente.getCpf())
                .endereco(cliente.getEndereco())
                .dataCadastro(LocalDateTime.now())
                .build();
    }

    public Cliente toEntity() throws InformacaoInvalidaException {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setCelular(celular);
        cliente.setEmail(email);
        cliente.setCpf(cpf);
        cliente.setEndereco(endereco);
        cliente.setDataCadastro(dataCadastro);
        return cliente;
    }

}
