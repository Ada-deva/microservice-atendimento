package com.br.microservice.atendimento.exception;

public class BadRequestException extends Exception{
    public BadRequestException(String mensagem) {
        super(mensagem);
    }

}
