package br.com.wendelsegadilha.resource;

import br.com.wendelsegadilha.entity.Cliente;
import br.com.wendelsegadilha.service.ClienteService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/clientes")
public class ClienteResource {

    private final ClienteService clienteService;

    public ClienteResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @POST
    @Transactional
    public RestResponse<Void> salvar(Cliente cliente) {
        clienteService.salvar(cliente);
        return RestResponse.ok();
    }

    @PUT
    @Transactional
    public RestResponse<Void> atualizar(Cliente cliente) {
        clienteService.atualizar(cliente);
        return RestResponse.ok();
    }

    @GET
    @Transactional
    public RestResponse<List<Cliente>> buscarTodos() {
        var clientes = clienteService.buscarTodos();
        return RestResponse.ok(clientes);
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public RestResponse<Void> deletar(@PathParam("id") Long id) {
        clienteService.excluir(id);
        return RestResponse.noContent();
    }

    @GET
    @Path("/{id}")
    public RestResponse<Cliente> buscarPorId(@PathParam("id") Long id) {
        var cliente = clienteService.buscarPorId(id);
        if (cliente == null) {
            return RestResponse.notFound();
        }
        return RestResponse.ok(cliente);
    }

}
