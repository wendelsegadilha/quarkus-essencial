package br.com.wendelsegadilha.service;

import br.com.wendelsegadilha.entity.Endereco;
import br.com.wendelsegadilha.service.http.ViaCepHttpService;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ViaCepService {

    @RestClient
    private ViaCepHttpService viaCepHttpService;

    @CircuitBreaker(
            requestVolumeThreshold = 2,
            failureRatio = 0.5,
            delay = 2000,
            successThreshold = 2
    )
    @Fallback(fallbackMethod = "metodoFallback")
    public Uni<Endereco> buscarEnderecoPorCep(String cep) {
        return viaCepHttpService.buscarEnderecoPorCep(cep);
    }

    public Uni<Endereco> metodoFallback(String cep) {
        Log.info("Fallback acionado: serviço de consulta de CEP indisponível.");
        return Uni.createFrom().nullItem();
    }

}
