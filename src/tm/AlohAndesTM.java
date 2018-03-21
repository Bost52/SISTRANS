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
import dao.DAOTablaReserva;
import vos.AgregarReserva;
import vos.Cliente;
import vos.Hospedaje;
import vos.Reserva;


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
			if(cli != null){
				throw new NoSuchElementException("No se encontró el cliente con la cedula: " + reserva.getIdCliente());
			}

			//////transaccion
			Reserva reser = new Reserva(reserva.getIdCliente(), reserva.getIdHospedaje(), reserva.getFechaInic(), reserva.getFechaFin());
			daoReserva.addReserva(reser);;
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


	public void eliminarHospedaje(Hospedaje hospedaje) throws SQLException{
		DAOTablaHospedaje daoHospedaje= new DAOTablaHospedaje();
		try 
		{
			this.conn = darConexion();
			daoHospedaje.setConn(conn);
			//////transaccion

			Hospedaje res= daoHospedaje.buscarHospedaje(hospedaje.getId());
			if(res==null)
			{
				throw new NoSuchElementException("no se puede cancelar una reserva inexistente");
			}

			daoHospedaje.deleteHospedaje(hospedaje);
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
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			Hospedaje[] resp =null;
			//////transaccion
			resp=daoReserva.darVeinteHospedajesPopulares();

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

	public void ingresosPorOperadorUltimoParAnios() throws Exception {
		DAOTablaIngresosParAnios daoIngresos= new DAOTablaIngresosParAnios();
		try 
		{
			this.conn = darConexion();
			daoIngresos.setConn(conn);
			//////transaccion

			daoIngresos.dineroRecibidoPorProveedorParAnios();
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
}

