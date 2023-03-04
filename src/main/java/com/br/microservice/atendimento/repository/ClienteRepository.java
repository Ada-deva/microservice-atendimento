package com.br.microservice.atendimento.repository;

import com.br.microservice.atendimento.model.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
}
