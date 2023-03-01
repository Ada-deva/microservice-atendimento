package com.br.microservice.atendimento.client;

import com.br.microservice.atendimento.configuration.EnvConfiguration;
import com.br.microservice.atendimento.model.ViaCEPApi;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Slf4j
@Component
@RequiredArgsConstructor
public class ViaCEPApiCLient {

    private final EnvConfiguration env;
    private final WebClient client = WebClient.create();

    public ViaCEPApi getViaCEPApi(String cep) {

        log.info("---Formatando  CEP do Cliente---");
        cep = cep.replace("-", "");
        String uri = env.getUri();
        uri = uri + cep + "/json/";

        log.info("---Requisitando endereço---");
        Mono<ViaCEPApi> viaCEPApiMono =
                client.get().uri(uri).retrieve().bodyToMono(ViaCEPApi.class);
        return viaCEPApiMono.block();

    }
}
