package com.br.microservice.atendimento.client;

import com.br.microservice.atendimento.configuration.EnvConfiguration;
import com.br.microservice.atendimento.model.ViaCEPApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Slf4j
@Component
public class ViaCEPApiCLient {


    private final WebClient client = WebClient.create();

    public ViaCEPApi getViaCEPApi(String cep) {
        EnvConfiguration env = new EnvConfiguration();
        cep = cep.replaceAll("-", "");
        String URI = env.getURI();
        URI = URI + cep + "/json/";
        Mono<ViaCEPApi> viaCEPApiMono =
                client.get().uri(URI).retrieve().bodyToMono(ViaCEPApi.class);

        return viaCEPApiMono
                .share().block();

    }
}
