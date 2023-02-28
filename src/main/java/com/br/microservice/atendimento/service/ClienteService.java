package com.br.microservice.atendimento.service;

import com.br.microservice.atendimento.client.ViaCEPApiCLient;
import com.br.microservice.atendimento.exception.InformacaoInvalidaException;
import com.br.microservice.atendimento.model.Cliente;
import com.br.microservice.atendimento.model.Endereco;
import com.br.microservice.atendimento.model.ViaCEPApi;
import com.br.microservice.atendimento.repository.ClienteRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ViaCEPApiCLient viaCEPApiCLient;

    @Value("${application.email}")
    private String applicationEmail;

    @Value("${sendgrid.sendgrid-api-key}")
    private String sendgridKey;

    public Optional<Cliente> cadastrar(Cliente cliente) throws InformacaoInvalidaException, IOException {
        if(cliente.getEndereco().getLogradouro() == null
        || cliente.getEndereco().getLogradouro().isBlank()
        || cliente.getEndereco().getLogradouro().isEmpty()) {
            log.info("---Obtendo endereço do Cliente a partir do CEP "  + cliente.getEndereco().getCep() + "---");
            ViaCEPApi enderecoEncontrado =  viaCEPApiCLient.getViaCEPApi(cliente.getEndereco().getCep());

            if(enderecoEncontrado == null) {
                log.info("---CEP não encontrado!---");
                throw new InformacaoInvalidaException("CEP não encontrado!");
            } else {
                log.info("---CEP encontrado!---");
                log.info("---Setando rua do Cliente---");
                cliente.getEndereco().setLogradouro(enderecoEncontrado.getLogradouro());
                log.info("---Setando bairro do Cliente---");
                cliente.getEndereco().setBairro(enderecoEncontrado.getBairro());
                log.info("---Setando cidade do Cliente---");
                cliente.getEndereco().setCidade(enderecoEncontrado.getLocalidade());
                log.info("---Setando estado do Cliente---");
                cliente.getEndereco().setEstado(enderecoEncontrado.getUf());
            }
        } else{
            log.info("---Cliente informou o endereço---");
        }


        log.info("---Data do cadastro " + LocalDateTime.now() + "---");
        cliente.setDataCadastro(LocalDateTime.now());

        log.info("---Salvando Cliente no banco de dados---");
        clienteRepository.save(cliente);

        if(cliente.getEmail() != null) {
            sendGridEmailer(cliente.getEmail());
        }

        return Optional.of(cliente);
    }

    public List<Cliente> clienteList() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    public Optional<Cliente> encontrarPorId(Long id) {

        return clienteRepository.findById(id);

    }

    public Optional<Cliente> atualizarCliente(Cliente cliente, Long id) throws InformacaoInvalidaException {
        Optional<Cliente> clienteEncontrado = encontrarPorId(id);


        if (clienteEncontrado.isPresent()) {
            if(cliente.getNome() != null && !cliente.getNome().isEmpty()) {
                clienteEncontrado.get().setNome(cliente.getNome());
            }

            if(cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
                clienteEncontrado.get().setEmail(cliente.getEmail());
            }

            if(cliente.getCelular() !=null && !cliente.getCelular().isEmpty()) {
                clienteEncontrado.get().setCelular(cliente.getCelular());
            }

            if(cliente.getCpf() !=null && !cliente.getCpf().isEmpty()) {
                clienteEncontrado.get().setCpf(cliente.getCpf());
            }

            clienteRepository.save(clienteEncontrado.get());

        }

        return clienteEncontrado;
    }






    public void sendGridEmailer(String email) throws IOException {
        Email from = new Email(applicationEmail);

        Email to = new Email(email);

        String subject = "Bem vindo ao AdaBurger";
        Content content = new Content("text/html", "Obrigada por se cadastrar em nosso sistema" +
                "<strong>AdaBurger</strong>");

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendgridKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        log.info("---Enviando E-mail: response code " + response.getStatusCode() + "---");

    }

}
