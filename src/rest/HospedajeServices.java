package rest;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.NoPermissionException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohAndesTM;
import vos.AgregarReserva;
import vos.Hospedaje;
import vos.Reserva;


@Path("hospedaje/")
@Produces({MediaType.APPLICATION_JSON})
public class HospedajeServices{


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


	@DELETE
	@Path("{id: \\d+}")
	public Response eliminarHospedaje(@PathParam("id") Integer idHos){
		AlohAndesTM tm = new AlohAndesTM(getPath());
		try {
			tm.eliminarHospedaje(idHos);
		}
		//		}catch(NoPermissionException e){
		//			return Response.status(403).entity(doErrorMessage(e)).build();
		//		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(idHos).build();
	}
	
	
	@GET
	@Path("20")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getHospedajesPopulares() {
		AlohAndesTM tm = new AlohAndesTM(getPath());
		Hospedaje[] resp = new Hospedaje[20];
//		List<Usuario> usuarios;
		try {
			resp = tm.getHospedajesPopulares();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	
	
}
