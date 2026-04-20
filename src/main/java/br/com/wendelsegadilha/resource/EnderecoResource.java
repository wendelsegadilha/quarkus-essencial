package br.com.wendelsegadilha.resource;

import br.com.wendelsegadilha.entity.Endereco;
import br.com.wendelsegadilha.service.http.ViaCepHttpService;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/enderecos")
public class EnderecoResource {

    @RestClient
    ViaCepHttpService viaCepHttpService;

    @GET
    @NonBlocking
    @Path("/{cep}/consultar")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<RestResponse<Endereco>> getEndereco(@PathParam("cep") String cep) {

        Uni<Endereco> endereco = viaCepHttpService.buscarEnderecoPorCep(cep);

        return endereco.onItem().ifNull().failWith(new RuntimeException("Endereço não encontrado para o CEP: " + cep))
                .onItem().transformToUni(e -> Uni.createFrom().item(RestResponse.ok(e)));

    }

}
