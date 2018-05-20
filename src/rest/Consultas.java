package rest;

import java.util.ArrayList;
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

import tm.AlohAndesTM;
import vos.AgregarReserva;
import vos.Agrupamiento10;
import vos.ConsultaHospServicio;
import vos.DatosTopPorTipoAlojamiento;
import vos.HospedajeIndicador;
import vos.IngresosParAnios;
import vos.Ordenamiento10;
import vos.Reserva;
import vos.Rpta10Agrupamiento;
import vos.Rpta10Ordenamiento;
import vos.UsoPorTipoUsuario;
import vos.UsoPorUsuario;

@Path("consulta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Consultas {

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
	@Path("/darUsoDeUsuarioDado/{id: \\d+}")
	public Response darUsoDeUsuarioDado(@PathParam("id") Integer id) {
		AlohAndesTM tm = new AlohAndesTM(getPath());
		UsoPorUsuario resp = null;
		try {
			resp = tm.darUsoDeUsuarioDado(id);
			//		}catch(NoPermissionException e){
			//			return Response.status(403).entity(doErrorMessage(e)).build();
		}catch(NoSuchElementException e) {
			return Response.status(404).entity(doErrorMessage(e)).build();
		}catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}

	@GET
	@Path("/darUsoPorTipoUsuario")
	public Response darUsoPorTipoUsuario(){
		AlohAndesTM tm = new AlohAndesTM(getPath());
		ArrayList<UsoPorTipoUsuario> resp = new ArrayList<UsoPorTipoUsuario>();
		try {
			resp = tm.darUsoPorTipoUsuario();

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	
	@POST
	@Path("/darHospedajesDisponiblesConServicio")
	public Response darHospedajesDisponiblesConServicio(ConsultaHospServicio consulta){
		AlohAndesTM tm = new AlohAndesTM(getPath());
		ArrayList<Integer> resp = new ArrayList<Integer>();
		try {
			resp = tm.darHospedajesDisponiblesConServicio(consulta);

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	
	@GET
	@Path("/darIndiceDeOcupacionPorHospedaje")
	public Response darIndiceDeOcupacionPorHospedaje(){
		AlohAndesTM tm = new AlohAndesTM(getPath());
		ArrayList<HospedajeIndicador> resp = new ArrayList<HospedajeIndicador>();
		try {
			resp = tm.darIndiceDeOcupacionPorHospedaje();

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	
	@GET
	@Path("/darClientesFrecuentes/{id: \\d+}")
	public Response darClientesFrecuentes(@PathParam("id") Integer id){
		AlohAndesTM tm = new AlohAndesTM(getPath());
		ArrayList<Integer> resp = new ArrayList<Integer>();
		try {
			resp = tm.darClientesFrecuentes(id);

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	
	@POST
	@Path("/darDatosTopPorTipoAlojamiento")
	public Response darDatosTopPorTipoAlojamiento(DatosTopPorTipoAlojamiento datos){
		AlohAndesTM tm = new AlohAndesTM(getPath());
		ArrayList<DatosTopPorTipoAlojamiento> resp = new ArrayList<DatosTopPorTipoAlojamiento>();
		try {
			resp = tm.darDatosTopPorTipoAlojamiento(datos);

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	
	@POST
	@Path("/rfc10Ordenamiento")
	public Response rfc10(Ordenamiento10 datos){
		AlohAndesTM tm = new AlohAndesTM(getPath());
		ArrayList<Rpta10Ordenamiento> resp = new ArrayList<Rpta10Ordenamiento>();
		try {
			resp = tm.rfc10Ordenamiento(datos);

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
	
	@POST
	@Path("/rfc10Agrupamiento")
	public Response rfc10(Agrupamiento10 datos){
		AlohAndesTM tm = new AlohAndesTM(getPath());
		ArrayList<Rpta10Agrupamiento> resp = new ArrayList<Rpta10Agrupamiento>();
		try {
			resp = tm.rfc10Agrupamiento(datos);

		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resp).build();
	}
}
