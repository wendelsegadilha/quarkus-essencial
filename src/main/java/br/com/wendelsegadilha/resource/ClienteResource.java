package br.com.wendelsegadilha.resource;

import br.com.wendelsegadilha.entity.Cliente;
import br.com.wendelsegadilha.service.ClienteService;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/clientes")
public class ClienteResource {

    private final ClienteService clienteService;

    public ClienteResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @POST
    @NonBlocking
    @Transactional
    public Uni<RestResponse<Void>> salvar(Cliente cliente, @Context UriInfo uriInfo) {
        return clienteService.salvar(cliente).replaceWith(
                RestResponse.created(uriInfo.getAbsolutePathBuilder().build())
        );
    }

    @PUT
    @NonBlocking
    @Transactional
    public Uni<RestResponse<Void>> atualizar(Cliente cliente) {
        return clienteService.atualizar(cliente).replaceWith(
                RestResponse.ok()
        );
    }

    @GET
    @Transactional
    @NonBlocking
    public Uni<RestResponse<List<Cliente>>> buscarTodos() {
        return clienteService.buscarTodos().onItem().transform(
                RestResponse::ok
        );
    }

    @DELETE
    @Transactional
    @NonBlocking
    @Path("/{id}")
    public Uni<RestResponse<Void>> deletar(@PathParam("id") Long id) {
        return clienteService.excluir(id).replaceWith(
                RestResponse.noContent()
        );
    }

    @GET
    @NonBlocking
    @Path("/{id}")
    public Uni<RestResponse<Cliente>> buscarPorId(@PathParam("id") Long id) {
        return clienteService.buscarPorId(id)
                .onItem().transform(c -> {
                    if (c == null) {
                        return RestResponse.notFound();
                    }
                    return RestResponse.ok(c);
                });
    }

}
