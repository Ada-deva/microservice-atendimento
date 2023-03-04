package com.br.microservice.atendimento.exception;

public class InformacaoInvalidaException extends Exception{

    public InformacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
