package br.com.wendelsegadilha.service;

import br.com.wendelsegadilha.entity.Cliente;
import br.com.wendelsegadilha.repository.ClienteRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
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

    @WithTransaction
    public Uni<Void> salvar(Cliente cliente) {
        Log.info("Cliente salvo com sucesso: " + cliente.getNome());
        meterRegistry.counter("cliente_salvo_counter").increment();
        return clienteRepository.persist(cliente).replaceWithVoid();
    }

    @WithSession
    public Uni<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @WithSession
    public Uni<List<Cliente>> buscarTodos() {
        return clienteRepository.listAll();
    }

    @WithTransaction
    public Uni<Void> atualizar(Cliente cliente) {
        Log.info("Cliente atualizado com sucesso: " + cliente.getNome());
        return clienteRepository.update("nome = ?1, email = ?2 where id = ?3",
                cliente.getNome(), cliente.getEmail(), cliente.getId()).replaceWithVoid();
    }

    @WithTransaction
    public Uni<Void> excluir(Long id) {
        meterRegistry.counter("cliente_excluido_counter").increment();
        return clienteRepository.deleteById(id).replaceWithVoid();
    }

}
