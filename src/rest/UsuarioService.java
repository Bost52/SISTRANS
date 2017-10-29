package rest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.NoPermissionException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import oracle.jdbc.proxy.annotation.Post;
import tm.RotondAndesTM;
import vos.AgregarUsuarioCliente;
import vos.Cliente;
import vos.ConsultarClientes;
import vos.ConsultarConsumoCliente;
import vos.Preferencia;
import vos.Usuario;

@Path("usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioService {

	
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
	
	@GET
	public Response getUsuarios() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Usuario> usuarios;
		try {
			usuarios = tm.darUsuarios();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}
	
	
	@POST
	public Response addUsuario(Usuario usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addUsuario(usuario);
		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}
	
	@POST
	@Path("/signup")
	public Response addUsuarioCliente(AgregarUsuarioCliente usuario) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addUsuarioCliente(usuario);
		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}catch(NoSuchElementException e) {
			return Response.status(404).entity(doErrorMessage(e)).build();
		}catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}
	

	@POST
	@Path("/preferencia")
	public Response addPreferencia(Preferencia preferencia) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addPreferencia(preferencia);
		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}catch(NoSuchElementException e) {
			return Response.status(404).entity(doErrorMessage(e)).build();
		}catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}
	
	@GET
	@Path("cliente/{id: \\d+}")
	public Response getInfoCliente(@PathParam("id")int id)
	{
		Cliente cliente=null;
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			cliente=tm.getInfoCliente(id);
		}catch(NoSuchElementException e) {
			return Response.status(404).entity(doErrorMessage(e)).build();
		}catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cliente).build();		
	}
	
	@GET
	@Path("consumoCliente/{id: \\d+}")
	public Response getConsumoCliente(@PathParam("id")long id)
	{
		ArrayList<ConsultarConsumoCliente> cliente=null;
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			cliente=tm.getConsumoCliente(id);
		}catch(NoSuchElementException e) {
			return Response.status(404).entity(doErrorMessage(e)).build();
		}catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cliente).build();		
	}

//	@GET
//	public Response getUsuariosAdministrador(ConsultarClientes administrador) {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		List<Usuario> usuarios;
//		try {
//			usuarios = tm.darUsuariosAdministrador(administrador);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(usuarios).build();
//	}
}
