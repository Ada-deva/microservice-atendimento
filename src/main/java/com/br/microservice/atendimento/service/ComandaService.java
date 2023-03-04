package com.br.microservice.atendimento.service;


import com.br.microservice.atendimento.dto.ComandaDTO;
import com.br.microservice.atendimento.exception.InformacaoNaoEncontradaException;
import com.br.microservice.atendimento.model.Cliente;
import com.br.microservice.atendimento.model.Comanda;
import com.br.microservice.atendimento.repository.ClienteRepository;
import com.br.microservice.atendimento.repository.ComandaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class ComandaService {

    @Autowired
    private ComandaRepository comandaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<Comanda> cadastrar(ComandaDTO comanda) throws InformacaoNaoEncontradaException {
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(comanda.getCliente());
        Comanda novaComanda = comanda.toEntity();

        if(clienteEncontrado.isEmpty()) {
            throw new InformacaoNaoEncontradaException("Cliente não encontrado!");
        } else {
            clienteEncontrado.ifPresent(novaComanda::setCliente);
            novaComanda.setData(LocalDateTime.now());
            comandaRepository.save(novaComanda);
        }



        return Optional.of(novaComanda);
    }
}
