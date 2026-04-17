package br.com.wendelsegadilha.service.http;

import br.com.wendelsegadilha.entity.Endereco;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/ws")
@RegisterRestClient(configKey = "via-cep-api")
public interface ViaCepHttpService {

    @GET
    @Path("/{cep}/json")
    Uni<Endereco> buscarEnderecoPorCep(@PathParam("cep") String cep);

}
