package com.br.microservice.atendimento.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class EnvConfiguration {

    @Value("${client.viacepapi.uri}")
    private String URI;

//    public EnvConfiguration() {
//        this.URI = "https://viacep.com.br/ws/";
//    }
}
