package rest;

import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.NoPermissionException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.AgregarUsuarioCliente;
import vos.FuncionamientoRotonda;
import vos.Pedido;
import vos.PedidoMesa;
import vos.PedidoProducto;
import vos.Preferencia;
import vos.ServirPedidoProducto;
import vos.Usuario;

@Path("pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoServices {

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}


	@POST
	public Response addPedido(Pedido pedido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addPedido(pedido);
		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
	}
	
	@POST
	@Path("/mesa")
	public Response addPedidoMesa(PedidoMesa pedido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addPedidoMesa(pedido);
		}catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
	}


	@POST
	@Path("/producto")
	public Response addPedidoProducto (PedidoProducto pedido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addPedidoProducto(pedido);
		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
	}

	@PUT
	@Path("/{id: \\d+}")
	public Response servirPedidoProducto(@PathParam("id") int id, ServirPedidoProducto pedido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			pedido.setIdPedido(id);
			if(pedido.getIdProducto()>0 && pedido.getIdMenu()>0)
			{
				throw new NoPermissionException("sdad");
			}
			if(pedido.getIdProducto()>0)
			{
				tm.servirPedidoProducto(pedido);
			}
			else if(pedido.getIdMenu()>0)
			{
				tm.servirPedidoProducto(pedido);
			}
			else
			{
				tm.servirPedido(id);
			}
		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(id).build();
	}

	@DELETE
	@Path("/{id: \\d+}")
	public Response cancelarPedido(@PathParam("id") int id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {

			tm.cancelarPedido(id);

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(id).build();
	}
	
	@DELETE
	@Path("/{id: \\d+}/producto/{id2: \\\\d+}")
	public Response cancelarPedidoProducto(@PathParam("id") int id,@PathParam("id2") int id2) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {

			tm.cancelarPedidoProducto(id,id2);

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(id).build();
	}
	
	@GET
	@Path("/funcionamiento/{id : \\d+}")
	public Response getFuncionamiento(@PathParam("id") long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		FuncionamientoRotonda[] v =null;
		try {

			v=tm.getFuncionamiento(id);

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(v).build();
	}
}
