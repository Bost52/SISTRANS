package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.naming.NoPermissionException;

import dao.DAOTablaRestaurantes;
import dao.DAOTablaUsuarios;
import vos.AgregarRestaurante;
import vos.AgregarUsuarioCliente;
import vos.Restaurante;
import vos.Usuario;

public class RotondAndesTM {


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
	public RotondAndesTM(String contextPathP) {
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

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones Restaurantes////////////////////
	////////////////////////////////////////


		public List<Restaurante> darRestaurantes() throws Exception {
			List<Restaurante> restaurantes;
			DAOTablaRestaurantes daoVideos = new DAOTablaRestaurantes();
			try 
			{
				//////transaccion
				this.conn = darConexion();
				daoVideos.setConn(conn);
				restaurantes = daoVideos.darRestaurantes();
	
			} catch (SQLException e) {
				System.err.println("SQLException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				System.err.println("GeneralException:" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				try {
					daoVideos.cerrarRecursos();
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}
			return restaurantes;
		}
	//
	//	public List<Restaurante> buscarRestaurantesPorName(String name) throws Exception {
	//		List<Restaurante> restaurantes;
	//		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoRestaurantes.setConn(conn);
	//			restaurantes = daoRestaurantes.buscarRestaurantesPorName(name);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoRestaurantes.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return restaurantes;
	//	}
	//
	//	public Restaurante buscarRestaurantePorId(Long id) throws Exception {
	//		Restaurante restaurante;
	//		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoRestaurantes.setConn(conn);
	//			restaurante = daoRestaurantes.buscarRestaurantePorId(id);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoRestaurantes.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return restaurante;
	//	}
	//	
	//	public void addRestaurante(Restaurante restaurante) throws Exception {
	//		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoRestaurantes.setConn(conn);
	//			daoRestaurantes.addRestaurante(restaurante);
	//			conn.commit();
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoRestaurantes.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}
	//	
	//	public void addRestaurantes(List<Restaurante> restaurantes) throws Exception {
	//		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
	//		try 
	//		{
	//			//////transaccion - ACID Example
	//			this.conn = darConexion();
	//			conn.setAutoCommit(false);
	//			daoRestaurantes.setConn(conn);
	//			Iterator<Restaurante> it = restaurantes.iterator();
	//			while(it.hasNext())
	//			{
	//				daoRestaurantes.addRestaurante(it.next());
	//			}
	//			
	//			conn.commit();
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			conn.rollback();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			conn.rollback();
	//			throw e;
	//		} finally {
	//			try {
	//				daoRestaurantes.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}
	//	
	//
	//	public void updateRestaurante(Restaurante restaurante) throws Exception {
	//		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoRestaurantes.setConn(conn);
	//			daoRestaurantes.updateRestaurante(restaurante);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoRestaurantes.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}
	//
	//	public void deleteRestaurante(Restaurante restaurante) throws Exception {
	//		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
	//		try 
	//		{
	//			//////transaccion
	//			this.conn = darConexion();
	//			daoRestaurantes.setConn(conn);
	//			daoRestaurantes.deleteRestaurante(restaurante);
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoRestaurantes.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//	}


	////////////////////////////////////////
	///////Transacciones Usuarios////////////////////
	////////////////////////////////////////

	public List<Usuario> darUsuarios() throws Exception {
		List<Usuario> usuarios;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			usuarios = daoUsuarios.darUsuarios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}

	public void addUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuarios daoUsuario= new DAOTablaUsuarios();
		try 
		{
			if(usuario.getRol().equals("CLIENTE"))
			{
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}
			//////transaccion
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoUsuario.addUsuario(usuario);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch(NoPermissionException e){
			System.err.println("NoPermissionException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void addUsuarioCliente(AgregarUsuarioCliente usuarioCliente) throws Exception {
		DAOTablaUsuarios daoUsuario= new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			if(daoUsuario.buscarUsuarioPorCedula(usuarioCliente.getCedulaAdministrador())==null)
			{
				throw new NoSuchElementException("no se encontro el administrador con la cedula: "+usuarioCliente.getCedulaAdministrador());
			}
			if(!daoUsuario.buscarUsuarioPorCedula(usuarioCliente.getCedulaAdministrador()).getRol().equals("ADMINISTRADOR"))
			{
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}
			if(!usuarioCliente.getUsuario().getRol().equals("CLIENTE"))
			{
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}
			Usuario usuario = usuarioCliente.getUsuario();
			//////transaccion

			daoUsuario.addUsuario(usuario);
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
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addRestaurante(AgregarRestaurante usuarioCliente) throws Exception {
		DAOTablaUsuarios daoUsuario= new DAOTablaUsuarios();
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoRestaurantes.setConn(conn);
			if(daoUsuario.buscarUsuarioPorCedula(usuarioCliente.getCedulaAdministrador())==null)
			{
				throw new NoSuchElementException("no se encontro el administrador con la cedula: "+usuarioCliente.getCedulaAdministrador());
			}
			if(daoUsuario.buscarUsuarioPorCedula(usuarioCliente.getRestaurante().getRepresentante().getCedula())==null)
			{
				throw new NoSuchElementException("no se encontro el usuario con la cedula: "+usuarioCliente.getRestaurante().getRepresentante().getCedula());
			}
			if(!daoUsuario.buscarUsuarioPorCedula(usuarioCliente.getCedulaAdministrador()).getRol().equals("ADMINISTRADOR"))
			{
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}
			if(!daoUsuario.buscarUsuarioPorCedula(usuarioCliente.getRestaurante().getRepresentante().getCedula()).getRol().equals("RESTAURANTE")){
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}
			Restaurante restaurante = usuarioCliente.getRestaurante();
			//////transaccion

			daoRestaurantes.addRestaurante(restaurante);;
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
				daoUsuario.cerrarRecursos();
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
