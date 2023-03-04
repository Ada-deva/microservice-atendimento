package com.br.microservice.atendimento.controller;

import com.br.microservice.atendimento.dto.EnderecoDTO;
import com.br.microservice.atendimento.exception.InformacaoInvalidaException;
import com.br.microservice.atendimento.exception.InformacaoNaoEncontradaException;
import com.br.microservice.atendimento.model.Comanda;
import com.br.microservice.atendimento.model.Endereco;
import com.br.microservice.atendimento.response.message.ResponseMessage;
import com.br.microservice.atendimento.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor
@RestControllerAdvice
public class EnderecoController {

    private final EnderecoService enderecoService;

    private ResponseMessage responseMessage;
    @Autowired
    private void responseMessage() {
        responseMessage = new ResponseMessage(Comanda.class.getSimpleName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Endereco> cadastrarComanda(@RequestBody EnderecoDTO endereco) throws InformacaoNaoEncontradaException, InformacaoInvalidaException {

        Optional<Endereco> novoEndereco = enderecoService.cadastrar(endereco.toEntity());
        if(novoEndereco.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, responseMessage.getNaoCadastrado());
        } else {
            Endereco response = novoEndereco.get();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> obterListaEnderecos() {
        return new ResponseEntity<>(enderecoService.enderecoList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> encontrarPorId(@PathVariable Long id) {
        Optional<Endereco> endereco = enderecoService.encontrarPorId(id);

        if(endereco.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(endereco.get(), HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Endereco> atualizarEndereco(@RequestBody EnderecoDTO endereco, @PathVariable Long id) throws InformacaoInvalidaException, InformacaoNaoEncontradaException {

        Optional<Endereco> enderecoAtualizado = enderecoService.atualizarEndereco(endereco, id);

        if(enderecoAtualizado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(enderecoAtualizado.get(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Endereco> deletarEndereco(@PathVariable Long id) {

        Optional<Endereco> enderecoDeletado = enderecoService.deletarEndereco(id);

        if(enderecoDeletado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, responseMessage.getNaoEncontrado());
        } else {
            return new ResponseEntity<>(enderecoDeletado.get(), HttpStatus.OK);
        }

    }
}
