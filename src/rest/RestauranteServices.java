package rest;


import java.util.ArrayList;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.AgregarRestaurante;
import vos.ConsumoCliente;
import vos.ConsumoRotonda;
import vos.Restaurante;
import vos.SurtirRestaurante;

@Path("restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestauranteServices {

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
	public Response getRestaurantes() {
		AlohAndesTM tm = new AlohAndesTM(getPath());
		List<Restaurante> videos;
		try {
			videos = tm.darRestaurantes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(videos).build();
	}
	
	@PUT
	@Path( "/consumo" )
	public Response getConsumoRestaurante(ConsumoRotonda consumo )
	{
		AlohAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			ArrayList<ConsumoCliente> v = tm.getConsumoRotanda(consumo);
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	@PUT
	@Path( "/noConsumo" )
	public Response getNoConsumoRestaurante(ConsumoRotonda consumo )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			ArrayList<ConsumoCliente> v = tm.getNoConsumoRotanda(consumo);
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
//
//
//	@GET
//	@Path( "{nombre}" )
//	public Response getRestauranteName( @QueryParam("nombre") String name) {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		List<Restaurante> videos;
//		try {
//			if (name == null || name.length() == 0)
//				throw new Exception("Nombre del video no valido");
//			videos = tm.buscarRestaurantesPorName(name);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(videos).build();
//	}
//
	@POST
	public Response addRestaurante(AgregarRestaurante restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addRestaurante(restaurante);
		}catch(NoPermissionException e){
			return Response.status(403).entity(doErrorMessage(e)).build();
		}catch(NoSuchElementException e) {
			return Response.status(404).entity(doErrorMessage(e)).build();
		}catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}
	
	@PUT
	@Path( "{id: \\d+}" )
	public Response surtirRestaurante( @PathParam( "id" ) int id, SurtirRestaurante restaurante)
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			restaurante.setLocal(id);
			tm.surtirRestaurante(restaurante);
			return Response.status( 200 ).entity( restaurante ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
//	
//	@POST
//	@Path("/varios")
//	public Response addRestaurantes(List<Restaurante> videos) {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		try {
//			tm.addRestaurantes(videos);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(videos).build();
//	}
//	
//
//	@PUT
//	public Response updateRestaurante(Restaurante video) {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		try {
//			tm.updateRestaurante(video);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(video).build();
//	}
//	
//
//	@DELETE
//	public Response deleteRestaurantes(Restaurante video) {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		try {
//			tm.deleteRestaurante(video);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(video).build();
//	}


}
