package com.br.microservice.atendimento.controller;


import com.br.microservice.atendimento.dto.ComandaDTO;
import com.br.microservice.atendimento.exception.InformacaoNaoEncontradaException;
import com.br.microservice.atendimento.model.Cliente;
import com.br.microservice.atendimento.model.Comanda;
import com.br.microservice.atendimento.response.message.ResponseMessage;
import com.br.microservice.atendimento.service.ComandaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/comanda")
@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class ComandaController {

    private final ComandaService comandaService;

    private ResponseMessage responseMessage;
    @Autowired
    private void responseMessage() {
        responseMessage = new ResponseMessage(Comanda.class.getSimpleName());
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Comanda> cadastrarComanda(@RequestBody ComandaDTO comanda) throws InformacaoNaoEncontradaException {

        Optional<Comanda> novaComanda = comandaService.cadastrar(comanda);
        if(novaComanda.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, responseMessage.getNaoCadastrado());
        } else {
            Comanda response = novaComanda.get();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }
}
