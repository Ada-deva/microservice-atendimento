package com.br.microservice.atendimento.controller;

import com.br.microservice.atendimento.model.Cliente;
import com.br.microservice.atendimento.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.br.microservice.atendimento.utility.CpfValidador.isValidCPF;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping

    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente){
        log.info(cliente.getEndereco().getCep());
        if(!isValidCPF(cliente.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi informado um CPF válido");
        }

        Optional<Cliente> novoCliente = clienteService.cadastrar(cliente);

        novoCliente.orElseThrow(()-> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cliente não " +
                "cadastrado"));

        Cliente response = novoCliente.get();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
