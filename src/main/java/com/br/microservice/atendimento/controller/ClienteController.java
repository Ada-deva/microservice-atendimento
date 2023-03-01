package com.br.microservice.atendimento.controller;

import com.br.microservice.atendimento.exception.InformacaoInvalidaException;
import com.br.microservice.atendimento.model.Cliente;
import com.br.microservice.atendimento.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.br.microservice.atendimento.utility.CpfValidador.isValidCPF;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class ClienteController {

    private final ClienteService clienteService;

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(InformacaoInvalidaException.class)
    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) throws InformacaoInvalidaException, IOException {
        log.info("---Recebendo informações do Cliente---");
        log.info("---Validando o CPF do Cliente---");

        if(!isValidCPF(cliente.getCpf())) {
            log.warn("---CPF inválido---");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi informado um CPF válido");
        }

        log.info("---Enviando cliente para cadastro---");
        Optional<Cliente> novoCliente = clienteService.cadastrar(cliente);

        log.info("---Checando se Cliente foi salvo com sucesso---");
        if(novoCliente.isPresent()) {
            log.info("---Cliente salvo com sucesso---");
            Cliente response = novoCliente.get();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            log.warn("---Registro do Cliente falhou---");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cliente não " +
                    "cadastrado");
        }

    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obterListaClientes() {
        return new ResponseEntity<>(clienteService.clienteList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> encontrarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.encontrarPorId(id);

        if(cliente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
        } else {
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
        public ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente cliente, @PathVariable Long id) throws InformacaoInvalidaException {
        if(cliente.getCpf() !=null) {
            if(!isValidCPF(cliente.getCpf())) {
                log.warn("---CPF inválido---");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi informado um CPF válido");
            }
        }

        Optional<Cliente> clienteAtualizado = clienteService.atualizarCliente(cliente, id);

        if(clienteAtualizado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
        } else {
            return new ResponseEntity<>(clienteAtualizado.get(), HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> deletarCliente(@PathVariable Long id) {

        Optional<Cliente> clienteDeletado = clienteService.deletarCliente(id);

        if(clienteDeletado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
        } else {
            return new ResponseEntity<>(clienteDeletado.get(), HttpStatus.OK);
        }

    }


}
