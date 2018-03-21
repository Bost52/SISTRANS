package rest;

import java.sql.Date;
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
import vos.Reserva;


@Path("productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaServices {

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
	public Response addReserva(AgregarReserva reserva) {
		AlohAndesTM tm = new AlohAndesTM(getPath());
		try {
			tm.addReserva(reserva);
			//		}catch(NoPermissionException e){
			//			return Response.status(403).entity(doErrorMessage(e)).build();
		}catch(NoSuchElementException e) {
			return Response.status(404).entity(doErrorMessage(e)).build();
		}catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}

	@DELETE
	@Path("/reserva/{idHospedaje: \\d+}/{idCliente: \\\\d+}/{fechaInicio: \\\\d+}/{fechaFin: \\\\d+}")
	public Response cancelarPedidoProducto(@PathParam("idHospedaje") Integer idHos, @PathParam("idCliente") Integer idCli, @PathParam("fechaInicio") Date fecIni, @PathParam("fechaFin") Date fecFin){
		AlohAndesTM tm = new AlohAndesTM(getPath());
		try {
			Reserva reserva = new Reserva(idHos, idCli, fecIni, fecFin);
			tm.cancelarReserva(reserva);

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(idHos).build();
	}
}
