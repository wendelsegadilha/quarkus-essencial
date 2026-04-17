package br.com.wendelsegadilha.service;

import br.com.wendelsegadilha.entity.Cliente;
import br.com.wendelsegadilha.repository.ClienteRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final MeterRegistry meterRegistry;

    public ClienteService(ClienteRepository clienteRepository, MeterRegistry meterRegistry) {
        this.clienteRepository = clienteRepository;
        this.meterRegistry = meterRegistry;
    }

    public void salvar(Cliente cliente) {
        clienteRepository.persist(cliente);
        Log.info("Cliente salvo com sucesso: " + cliente.getNome());
        meterRegistry.counter("cliente_salvo_counter").increment();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.listAll();
    }

    public void atualizar(Cliente cliente) {
        clienteRepository.update("nome = ?1, email = ?2 where id = ?3",
                cliente.getNome(), cliente.getEmail(), cliente.getId());
    }

    public void excluir(Long id) {
        clienteRepository.deleteById(id);
        meterRegistry.counter("cliente_excluido_counter").increment();
    }

}
