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

import dao.DAOTablaIngrediente;
import dao.DAOTablaMenu;
import dao.DAOTablaPedido;
import dao.DAOTablaProductoSingular;
import dao.DAOTablaRestaurantes;
import dao.DAOTablaUsuarios;
import dao.DAOTablaZona;
import vos.AgregarIngredienteRestaurante;
import vos.AgregarMenu;
import vos.AgregarProducto;
import vos.AgregarRestaurante;
import vos.AgregarUsuarioCliente;
import vos.AgregarZona;
import vos.Preferencia;
import vos.Ingrediente;
import vos.Menu;
import vos.Pedido;
import vos.ProductoSingular;
import vos.Restaurante;
import vos.Usuario;
import vos.Zona;

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
	
	public List<ProductoSingular> darProductos() throws Exception {
		List<ProductoSingular> productos;
		DAOTablaProductoSingular daoProductos = new DAOTablaProductoSingular();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.darProductos();

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
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}

	
	public void addProducto(AgregarProducto producto) throws Exception {
		DAOTablaUsuarios daoUsuario= new DAOTablaUsuarios();
		DAOTablaProductoSingular daoProducto= new DAOTablaProductoSingular();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoProducto.setConn(conn);
			if(daoUsuario.buscarUsuarioPorCedula(producto.getCedulaRestaurante())==null)
			{
				throw new NoSuchElementException("no se encontro el representante con la cedula: "+producto.getCedulaRestaurante());
			}
			if(!daoUsuario.buscarUsuarioPorCedula(producto.getCedulaRestaurante()).getRol().equals("RESTAURANTE"))
			{
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}
			//////transaccion

			daoProducto.addProductoSingular(producto.getProducto(), producto.getCantidad(), producto.getLocal(), producto.getPrecio(), producto.getCoste());
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
	
	
	public void addPreferencia(Preferencia preferencia) throws Exception {
		DAOTablaUsuarios daoUsuario= new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			if(daoUsuario.buscarUsuarioPorCedula(preferencia.getCedula())==null)
			{
				throw new NoSuchElementException("no se encontro el usuario con la cedula: "+preferencia.getCedula());
			}
			//////transaccion

			daoUsuario.addPreferencia(preferencia.getIdPreferencia(), preferencia.getCedula(), preferencia.getIdCategoria(), preferencia.getMaximo(), preferencia.getMinimo(), preferencia.getIdZona());
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
					this.conn.rollback();
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void addMenuRestaurante(AgregarMenu userResta) throws Exception{
		DAOTablaMenu daoMenu = new DAOTablaMenu();
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		
		try 
		{
			this.conn = darConexion();
			daoMenu.setConn(conn);
			daoUsuario.setConn(conn);
			if(daoUsuario.buscarUsuarioPorCedula(userResta.getCedulaRestaurante()) == null || userResta.getMenu() == null)
			{
				throw new NoSuchElementException("no se encontro el usuario restaurante con la cedula: "+userResta.getCedulaRestaurante());
			}
			if(!daoUsuario.buscarUsuarioPorCedula(userResta.getCedulaRestaurante()).getRol().equals("RESTAURANTE"))
			{
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}
			
			
			Menu menu = userResta.getMenu();
			//////transaccion
			
			daoMenu.addMenu(menu);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch(NoPermissionException e){
			System.err.println("privilegeException:" + e.getMessage());
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
	
	public void addPedido(Pedido pedido) throws Exception{
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		DAOTablaPedido daoPedido= new DAOTablaPedido();
		
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoPedido.setConn(conn);
			//////transaccion
			
			daoPedido.addPedido(pedido);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch(NoPermissionException e){
			System.err.println("privilegeException:" + e.getMessage());
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
	
	public void servirPedido(int pedido) throws Exception{
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		DAOTablaPedido daoPedido= new DAOTablaPedido();
		
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoPedido.setConn(conn);
			//////transaccion
			
			daoPedido.pedidoServido(daoPedido.buscarPedidoById(pedido));
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch(NoPermissionException e){
			System.err.println("privilegeException:" + e.getMessage());
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
	
	public void addIngredienteRestaurante(AgregarIngredienteRestaurante userResta) throws Exception{
		DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoIngrediente.setConn(conn);
			daoUsuario.setConn(conn);
			if(daoUsuario.buscarUsuarioPorCedula(userResta.getCedulaUsuarioRestaurante()) == null || userResta.getIngrediente() == null)
			{
				throw new NoSuchElementException("no se encontro el usuario restaurante con la cedula: "+userResta.getCedulaUsuarioRestaurante());
			}
			if(!daoUsuario.buscarUsuarioPorCedula(userResta.getCedulaUsuarioRestaurante()).getRol().equals("RESTAURANTE"))
			{
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}
			
			
			Ingrediente ingrediente = userResta.getIngrediente();
			//////transaccion
			
			daoIngrediente.addIngrediente(ingrediente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch(NoPermissionException e){
			System.err.println("privilegeException:" + e.getMessage());
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

	
	public void addZona(AgregarZona userResta) throws Exception{
		DAOTablaZona daoZona = new DAOTablaZona();
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		
		try 
		{
			this.conn = darConexion();
			daoZona.setConn(conn);
			daoUsuario.setConn(conn);
			if(daoUsuario.buscarUsuarioPorCedula(userResta.getCedulaAdministrador()) == null || userResta.getZona() == null)
			{
				throw new NoSuchElementException("no se encontro el administrador con la cedula: "+userResta.getCedulaAdministrador());
			}
			if(!daoUsuario.buscarUsuarioPorCedula(userResta.getCedulaAdministrador()).getRol().equals("ADMINISTRADOR"))
			{
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}

			Zona zona = userResta.getZona();
			//////transaccion
			
			daoZona.addZona(zona);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch(NoPermissionException e){
			System.err.println("privilegeException:" + e.getMessage());
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

