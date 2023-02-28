package com.br.microservice.atendimento.service;

import com.br.microservice.atendimento.client.ViaCEPApiCLient;
import com.br.microservice.atendimento.model.Cliente;
import com.br.microservice.atendimento.model.ViaCEPApi;
import com.br.microservice.atendimento.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    public Optional<Cliente> cadastrar(Cliente cliente) {
        ViaCEPApiCLient viaCEPApiCLient = new ViaCEPApiCLient();
        log.info(cliente.getEndereco().getCep());
        ViaCEPApi enderecoEncontrado =  viaCEPApiCLient.getViaCEPApi(cliente.getEndereco().getCep());

        cliente.getEndereco().setLogradouro(enderecoEncontrado.getLogradouro());
        cliente.getEndereco().setBairro(enderecoEncontrado.getBairro());
        cliente.getEndereco().setCidade(enderecoEncontrado.getLocalidade());
        cliente.getEndereco().setEstado(enderecoEncontrado.getUf());
        cliente.setDataCadastro(LocalDateTime.now());


        clienteRepository.save(cliente);

        return Optional.of(cliente);
    }
}
