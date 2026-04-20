package br.com.wendelsegadilha.service;

import br.com.wendelsegadilha.entity.Cliente;
import br.com.wendelsegadilha.repository.ClienteRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.Vertx;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class ClienteServiceTest {

    @InjectMock
    private ClienteRepository  clienteRepository;

    @Inject
    ClienteService clienteService;

    @Test
    public void deveCadastrarCliente() {
        Cliente cliente = criarCliente();
        Vertx.vertx().runOnContext(r -> {
            clienteService.salvar(cliente).await().indefinitely();
            Mockito.verify(clienteRepository).persist(cliente);
        });

    }

    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Wendel Segadilha");
        cliente.setEmail("wendelsegadilha99@gmail.com");
        return cliente;
    }

}
