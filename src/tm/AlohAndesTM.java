package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.naming.NoPermissionException;

import dao.DAOTablaCliente;
import dao.DAOTablaHospedaje;
import dao.DAOTablaIngresosParAnios;
import dao.DAOTablaOferta;
import dao.DAOTablaReserva;
import vos.AgregarReserva;
import vos.Cliente;
import vos.Hospedaje;
import vos.IngresosParAnios;
import vos.Oferta;
import vos.Reserva;
import vos.ReservaMasiva;


public class AlohAndesTM {


	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;


	/**
	 * Metodo constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public AlohAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////////////////////////////////
	///////Transacciones De AlohAndes////////////////////
	////////////////////////////////////////


	public void addReserva(AgregarReserva reserva) throws Exception{
		DAOTablaReserva daoReserva= new DAOTablaReserva();
		DAOTablaCliente daoClientes = new DAOTablaCliente();
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoClientes.setConn(conn);
			Cliente cli = daoClientes.buscarClientePorCedula(reserva.getIdCliente());
			if(cli == null){
				throw new NoSuchElementException("No se encontró el cliente con la cedula: " + reserva.getIdCliente());
			}

			//////transaccion
			Reserva reser = new Reserva(reserva.getIdCliente(), reserva.getIdHospedaje(), reserva.getFechaInic(), reserva.getFechaFin());
			daoReserva.addReserva(reser, -1);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch(NoPermissionException e){
			System.err.println("NoPermissionException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch(NoSuchElementException e) {
			System.err.println("noSuchElementException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;


		} finally {
			try {
				daoReserva.cerrarRecursos();
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	public void cancelarReserva(Reserva reserva) throws Exception {
		DAOTablaReserva daoReserva= new DAOTablaReserva();
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			//////transaccion

			Reserva res= daoReserva.buscarReserva(reserva);
			if(res==null)
			{
				throw new NoSuchElementException("no se puede cancelar una reserva inexistente");
			}

			daoReserva.deleteReserva(res);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		catch(NoPermissionException e){
			System.err.println("privilegeException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch(NoSuchElementException e) {
			System.err.println("noSuchElementException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		
	}


	public void deshabilitarOferta(Integer oferta) throws Exception {
		DAOTablaOferta daoOferta= new DAOTablaOferta();
		try 
		{
			this.conn = darConexion();
			daoOferta.setConn(conn);
			//////transaccion

			Oferta ofer= daoOferta.buscarOferta(oferta);
			if(ofer==null)
			{
				throw new NoSuchElementException("no se puede deshabilitar una oferta inexistente");
			}


			daoOferta.deleteOferta(oferta);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		catch(NoPermissionException e){
			System.err.println("privilegeException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch(NoSuchElementException e) {
			System.err.println("noSuchElementException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		
	}


	public void addOferta(Integer oferta) throws Exception{
		DAOTablaOferta daoOferta= new DAOTablaOferta();
		try 
		{
			this.conn = darConexion();
			daoOferta.setConn(conn);
			Oferta ofer= daoOferta.buscarOferta(oferta);
			if(ofer != null){
				throw new Exception("Ya existe esta oferta");
			}

			//////transaccion
			daoOferta.addOferta(oferta);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch(NoPermissionException e){
			System.err.println("NoPermissionException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch(NoSuchElementException e) {
			System.err.println("noSuchElementException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;


		} finally {
			try {
				daoOferta.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void eliminarHospedaje(Integer id) throws SQLException{

		DAOTablaHospedaje daoHospedaje= new DAOTablaHospedaje();
		try 
		{
			this.conn = darConexion();
			daoHospedaje.setConn(conn);
			//////transaccion

			Hospedaje res= daoHospedaje.buscarHospedaje(id);
			if(res==null)
			{
				throw new NoSuchElementException("no se puede cancelar una reserva inexistente");
			}

			daoHospedaje.deleteHospedaje(id);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		//		catch(NoPermissionException e){
		//			System.err.println("privilegeException:" + e.getMessage());
		//			e.printStackTrace();
		//			throw e;
		//		}
		catch(NoSuchElementException e) {
			System.err.println("noSuchElementException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoHospedaje.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

	public Hospedaje[] getHospedajesPopulares() throws SQLException{
		DAOTablaReserva daoReserva = new DAOTablaReserva();
		DAOTablaHospedaje daoHospedaje = new DAOTablaHospedaje();
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoHospedaje.setConn(conn);
			Integer[] resp =null;


			//////transaccion
			resp=daoReserva.darVeinteHospedajesPopulares();
			Hospedaje[] hosp = new Hospedaje[20];
			int i = 0;
			while(i<20){
				hosp[i] = daoHospedaje.buscarHospedaje(resp[i]);
				i++;
			}
			conn.commit();

			return hosp;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
			//		} catch(NoPermissionException e){
			//			System.err.println("privilegeException:" + e.getMessage());
			//			e.printStackTrace();
			//			throw e;
		}catch(NoSuchElementException e) {
			System.err.println("noSuchElementException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public ArrayList<Hospedaje> getHospedajes() throws SQLException{
		DAOTablaHospedaje daoReserva = new DAOTablaHospedaje();
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			ArrayList<Hospedaje> resp =null;
			//////transaccion
			resp= daoReserva.getHospedajes();

			conn.commit();

			return resp;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
			//		} catch(NoPermissionException e){
			//			System.err.println("privilegeException:" + e.getMessage());
			//			e.printStackTrace();
			//			throw e;
		}catch(NoSuchElementException e) {
			System.err.println("noSuchElementException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public ArrayList<IngresosParAnios> ingresosPorOperadorUltimoParAnios() throws Exception {
		DAOTablaIngresosParAnios daoIngresos= new DAOTablaIngresosParAnios();
		try 
		{

			ArrayList<IngresosParAnios> resp = new ArrayList<IngresosParAnios>();
			this.conn = darConexion();
			daoIngresos.setConn(conn);
			//////transaccion
			resp = daoIngresos.dineroRecibidoPorProveedorParAnios();
			conn.commit();

			return resp;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		catch(NoPermissionException e){
			System.err.println("privilegeException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch(NoSuchElementException e) {
			System.err.println("noSuchElementException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngresos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		
	}


	//Agregar reserva masiva
	public void addReservaMasiva(ReservaMasiva res) throws Exception{
		DAOTablaReserva daoReserva= new DAOTablaReserva();
		DAOTablaCliente daoClientes = new DAOTablaCliente();
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoClientes.setConn(conn);

			//verfifica que el cliente exista
			Cliente cli = daoClientes.buscarClientePorCedula(res.getIdUsuario());
			if(cli == null){
				throw new NoSuchElementException("No se encontró el cliente con la cedula: " + res.getIdUsuario());
			}


			//////transaccion

			//autocommit en 0
			conn.setAutoCommit(false);

			int cant = res.getCantidad();

			//Consulta las ofertas disponibles que pueden suplir la reserva.
			ArrayList<Integer> hospedajes = verificarHospedajes(cant, res.getTipo(), res.getFechaInicio(), res.getFechaFin());
			if(hospedajes.size() >= cant && hospedajes.size() != 0){
				//Persiste la reserva masiva
				daoReserva.addReservaMasiva(res.getId());
				System.out.println("existen " + hospedajes.size() + " ofertas que pueden suplir la reserva");
				//persiste todas las reservas
				while (cant != 0){
					Reserva reser = new Reserva(res.getIdUsuario(), hospedajes.get(cant-1), res.getFechaInicio(), res.getFechaFin());
					daoReserva.addReserva(reser, res.getId());
					cant--;
				}
				// hago commit 
				conn.commit();
			}



			else{
				throw new Exception("No se encontraron suficientes ofertas de hospedaje (" + cant + ") para poder realizar la reserva.");
			}



			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch(NoPermissionException e){
			System.err.println("NoPermissionException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch(NoSuchElementException e) {
			System.err.println("noSuchElementException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;


		} finally {
			try {
				daoReserva.cerrarRecursos();
				daoClientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public ArrayList<Integer> verificarHospedajes(int cantidad, String tipo, String fechaInic, String fechaFin){
		ArrayList<Integer> hospedajes = new ArrayList<Integer>();

		DAOTablaReserva daoReserva = new DAOTablaReserva();
		DAOTablaOferta daoOferta = new DAOTablaOferta();

		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			daoOferta.setConn(conn);

			//Consulta todas las ofertas disponibles (aun no verifica la fecha)
			ArrayList<Integer> ofertasDeTipo = daoOferta.getOfertasTipo(tipo);
			System.out.println("hay " + ofertasDeTipo.size() + " hosp que son del tipo requerido");
			if(ofertasDeTipo.size() >= cantidad){
				//Selecciona unicamente las ofertas que no estan en reservas 
				hospedajes = daoReserva.verificarMasivas(ofertasDeTipo, cantidad, fechaInic, fechaFin);
			}
			else{
				return hospedajes;
			}
			//verifica primero si hay al menos la cantidad necesaria y luego si la fecha sirve
		}
		catch(Exception e){

		}

		return hospedajes;
	}

	//Cancelar reserva masiva
	public void cancelarReservaMasiva(Integer res){
		DAOTablaReserva daoReserva = new DAOTablaReserva();
		try{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			
			//Set autocommit 0
			conn.setAutoCommit(false);;
			
			//Busca todas las reservas singulares asociadas a la masiva y las elimina
			daoReserva.cancelarSingularesMasiva(res);
			
			//Una vez eliminadas las singulares, elimina la grande y hace commit.
			daoReserva.cancelarMasiva(res);
			conn.commit();
		}
		catch(Exception e){

		}

	}

}
