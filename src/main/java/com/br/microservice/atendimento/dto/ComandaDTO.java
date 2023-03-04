package com.br.microservice.atendimento.dto;

import com.br.microservice.atendimento.enums.TipoPagamento;
import com.br.microservice.atendimento.model.Cliente;
import com.br.microservice.atendimento.model.Comanda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComandaDTO {

    private long id;
    private LocalDateTime data;
    private double conta;
    private TipoPagamento tipoPagamento;
    private long cliente;

    public ComandaDTO of(Comanda comanda) {
        return ComandaDTO.builder()
                .id(comanda.getId())
                .data(comanda.getData())
                .conta(comanda.getConta())
                .tipoPagamento(comanda.getTipoPagamento())
                .cliente(comanda.getCliente().getId())
                .build();
    }

    public Comanda toEntity() {
        return Comanda.builder()
                .id(id)
                .data(data)
                .conta(conta)
                .tipoPagamento(tipoPagamento)
                .build();
    }
}
