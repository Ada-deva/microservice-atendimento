package com.br.microservice.atendimento.model;

import com.br.microservice.atendimento.exception.InformacaoInvalidaException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
     void cliente() throws InformacaoInvalidaException {
        Cliente cliente = new Cliente();
        Endereco endereco = new Endereco();

        endereco.setId(1l);
        endereco.setLogradouro("Rua Avelino");
        endereco.setNumero("97");
        endereco.setBairro("Jardim");
        endereco.setCidade("Jacareí");
        endereco.setEstado("São Paulo");
        endereco.setCep("12319-060");

        cliente.setId(1);
        cliente.setNome("Bruna");
        cliente.setCelular("1298892-4146");
        cliente.setEmail("bruna@gmail.com");
        cliente.setEndereco(endereco);
        cliente.setDataCadastro(LocalDateTime.now());
        cliente.setCpf("355.934.510-36");

        assertNotNull(cliente);

    }

    @Test
    void testToString() throws InformacaoInvalidaException {

        Cliente cliente = new Cliente();
        Endereco endereco = new Endereco();
        LocalDateTime agora = LocalDateTime.now();
        endereco.setId(1l);
        endereco.setLogradouro("Rua Avelino");
        endereco.setNumero("97");
        endereco.setBairro("Jardim");
        endereco.setCidade("Jacareí");
        endereco.setEstado("São Paulo");
        endereco.setCep("12319-060");

        cliente.setId(1);
        cliente.setNome("Bruna");
        cliente.setCelular("1298892-4146");
        cliente.setEmail("bruna@gmail.com");
        cliente.setEndereco(endereco);
        cliente.setDataCadastro(agora);
        cliente.setCpf("355.934.510-36");

        String rawData = "Cliente(id=1, nome=Bruna, celular=1298892-4146, email=bruna@gmail.com, cpf=355.934.510-36, " +
                "endereco=Endereco(id=1, logradouro=Rua Avelino, numero=97, bairro=Jardim, cep=12319-060, " +
                "cidade=Jacareí, estado=São Paulo), dataCadastro=" + agora + ")";

        assertEquals(rawData, cliente.toString());
    }
}


