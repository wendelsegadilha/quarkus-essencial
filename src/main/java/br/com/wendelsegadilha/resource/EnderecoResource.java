package br.com.wendelsegadilha.resource;

import br.com.wendelsegadilha.entity.Endereco;
import br.com.wendelsegadilha.service.http.ViaCepHttpService;
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
    @Path("/{cep}/consultar")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Endereco> getEndereco(@PathParam("cep") String cep) {
        Endereco endereco = viaCepHttpService.buscarEnderecoPorCep(cep);
        return RestResponse.ok(endereco);
    }

}
