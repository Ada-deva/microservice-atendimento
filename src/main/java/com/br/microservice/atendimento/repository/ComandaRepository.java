package com.br.microservice.atendimento.repository;

import com.br.microservice.atendimento.model.Comanda;
import org.springframework.data.repository.CrudRepository;

public interface ComandaRepository extends CrudRepository<Comanda, Long> {
}
