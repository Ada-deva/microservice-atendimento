package com.br.microservice.atendimento.repository;

import com.br.microservice.atendimento.model.Endereco;
import org.springframework.data.repository.CrudRepository;

public interface EnderecoRepository extends CrudRepository<Endereco, Long> {
}

