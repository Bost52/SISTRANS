package rest;

import java.util.NoSuchElementException;

import javax.naming.NoPermissionException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohAndesTM;
import vos.AgregarReserva;
import vos.Oferta;
import vos.Reserva;

@Path("oferta")
@Produces({MediaType.APPLICATION_JSON})
public class OfertaServices {

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
	@Path("/agregarOferta")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addOferta(Integer oferta) {
		AlohAndesTM tm = new AlohAndesTM(getPath());
		try {
			tm.addOferta(oferta);
			//		}catch(NoPermissionException e){
			//			return Response.status(403).entity(doErrorMessage(e)).build();
		}catch(NoSuchElementException e) {
			return Response.status(404).entity(doErrorMessage(e)).build();
		}catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(oferta).build();
	}

	@DELETE
	@Path("/deshabilitarOferta")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deshabilitarOferta(@PathParam("idHospedaje") Integer idHos){
		AlohAndesTM tm = new AlohAndesTM(getPath());
		try {
			tm.deshabilitarOferta(idHos);

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(idHos).build();
	}
}
