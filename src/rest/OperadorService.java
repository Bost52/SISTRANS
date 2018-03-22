package rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohAndesTM;
import vos.Hospedaje;
import vos.IngresosParAnios;

@Path("operador")
@Produces({MediaType.APPLICATION_JSON})
public class OperadorService {

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
	@Path("/ingreso")
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<IngresosParAnios> getIngresosParAnios(){
		AlohAndesTM tm = new AlohAndesTM(getPath());
		Hospedaje[] resp = new Hospedaje[20];
		try {
			resp = tm.ingresosPorOperadorUltimoParAnios();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
}
