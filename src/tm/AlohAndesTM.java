package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import javax.naming.NoPermissionException;

import dao.DAOTablaIngrediente;
import dao.DAOTablaMenu;
import dao.DAOTablaPedido;
import dao.DAOTablaProductoSingular;
import dao.DAOTablaReserva;
import dao.DAOTablaRestaurantes;
import dao.DAOTablaUsuarios;
import dao.DAOTablaZona;
import vos.AgregarEquivalenciaIngrediente;
import vos.AgregarEquivalenciaProducto;
import vos.AgregarIngredienteRestaurante;
import vos.AgregarMenu;
import vos.AgregarProducto;
import vos.AgregarReserva;
import vos.AgregarRestaurante;
import vos.AgregarUsuarioCliente;
import vos.AgregarZona;
import vos.Cliente;
import vos.ConsultarConsumoCliente;
import vos.ConsumoCliente;
import vos.ConsumoRotonda;
import vos.FuncionamientoRotonda;
import vos.Preferencia;
import vos.Ingrediente;
import vos.Menu;
import vos.Pedido;
import vos.PedidoMesa;
import vos.PedidoProducto;
import vos.ProductoSingular;
import vos.Restaurante;
import vos.ServirPedidoProducto;
import vos.SurtirRestaurante;
import vos.Usuario;
import vos.Zona;

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

			daoProducto.addProductoSingular(producto.getProducto(), producto.getCantidad(), producto.getLocal(), producto.getPrecio(), producto.getCoste(),producto.getMax());
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
		DAOTablaProductoSingular daoTablaProductoSingular= new DAOTablaProductoSingular();
		DAOTablaMenu daoTablaMenu=new DAOTablaMenu();

		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoPedido.setConn(conn);
			daoTablaProductoSingular.setConn(conn);
			daoTablaMenu.setConn(conn);

			//////transaccion

			if(pedido.getProductos()!=null)
			{
				double totalValue=0;
				for(int i =0; i<pedido.getProductos().size();i++)
				{
					PedidoProducto ped = pedido.getProductos().get(i);
					if(ped.getIdProducto()>0)
					{
						totalValue+=daoTablaProductoSingular.precioProducto(ped.getLocal(), ped.getIdProducto());
					}
					if(ped.getIdMenu()>0)
					{
						totalValue+=daoTablaMenu.buscarMenuPorId((long)ped.getIdMenu()).getPrecio();
					}
				}

				daoPedido.addPedido(pedido, totalValue);
				Iterator<PedidoProducto> iter= pedido.getProductos().iterator();

				while(iter.hasNext())
				{
					PedidoProducto ped=iter.next();
					ped.setIdPedido(pedido.getIdPedido());
					addPedidoProducto(ped);
				}
			}
			else {
				throw new NoPermissionException("no se puede agregar un pedido de ningun producto");
			}
			this.conn = darConexion();
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

	public void addPedidoProducto(PedidoProducto pedido) throws Exception{
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		DAOTablaPedido daoPedido= new DAOTablaPedido();
		DAOTablaProductoSingular daoTablaProductoSingular= new DAOTablaProductoSingular();

		try 
		{
			this.conn = darConexion();
			daoTablaProductoSingular.setConn(conn);
			daoUsuario.setConn(conn);
			daoPedido.setConn(conn);
			//////transaccion

			if(pedido.getIdMenu()>0 && pedido.getIdProducto()>0)
			{
				throw new NoPermissionException("No se puede agregar un menu y un producto a la vez");
			}
			if(pedido.getIdMenu()>0)
			{
				daoPedido.addMenuPedido(pedido);
			}
			else if(pedido.getIdProducto()>0)
			{
				if(pedido.getEquivalencias()!=null) {
					for(int i=0;i<pedido.getEquivalencias().size();i++) {
						if(pedido.getIdPedido()==pedido.getEquivalencias().get(i))
						{
							throw new NoSuchElementException("no existe una equivalencia del producto con el de id: "+pedido.getEquivalencias().get(i) );
						}
					}
					daoPedido.addProductoPedido(pedido);
				}
			}
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

	public void servirPedidoProducto(ServirPedidoProducto pedido) throws Exception{
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		DAOTablaPedido daoPedido= new DAOTablaPedido();

		try 
		{


			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoPedido.setConn(conn);
			//////transaccion


			if(pedido.getIdMenu()>0 && pedido.getIdProducto()>0)
			{
				throw new NoPermissionException("No se puede agregar un menu y un producto a la vez");
			}
			if(pedido.getIdMenu()>0)
			{
				daoPedido.pedidoServidoMenu(pedido);
			}
			else if(pedido.getIdProducto()>0)
			{
				daoPedido.pedidoServidoProducto(pedido);
			}
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

	public void surtirRestaurante(SurtirRestaurante restaurante) throws Exception{
		DAOTablaRestaurantes daoRestaurante = new DAOTablaRestaurantes();
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoRestaurante.setConn(conn);
			daoUsuario.setConn(conn);
			if(daoUsuario.buscarUsuarioPorCedula(restaurante.getCedulaUsuarioRestaurante()) == null)
			{
				throw new NoSuchElementException("no se encontro el usuario restaurante con la cedula: "+restaurante.getCedulaUsuarioRestaurante());
			}
			if(daoRestaurante.sirveProducto(restaurante.getLocal(), restaurante.getProducto()))
			{
				throw new NoSuchElementException("no existe relacion entre el restaurante y el producto");
			}
			if(!daoUsuario.buscarUsuarioPorCedula(restaurante.getCedulaUsuarioRestaurante()).getRol().equals("RESTAURANTE"))
			{
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}
			//////transaccion

			daoRestaurante.surtirRestaurante(restaurante);
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

	public void addEquivalenciaIngrediente(int id, AgregarEquivalenciaIngrediente userResta) throws Exception {
		DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoIngrediente.setConn(conn);
			daoUsuario.setConn(conn);
			if(daoUsuario.buscarUsuarioPorCedula(userResta.getCedulaUsuarioRestaurante()) == null || userResta.getIdIngrediente() == 0)
			{
				throw new NoSuchElementException("no se encontro el usuario restaurante con la cedula: "+userResta.getCedulaUsuarioRestaurante());
			}
			if(!daoUsuario.buscarUsuarioPorCedula(userResta.getCedulaUsuarioRestaurante()).getRol().equals("RESTAURANTE"))
			{
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}
			if(userResta.getIdIngrediente()==id)
			{
				throw new NoPermissionException("No se puede agregar una rquivalencia con el mismo ingrediente");
			}

			//////transaccion

			daoIngrediente.addEquivalenciaIngrediente(id,userResta.getIdIngrediente(),userResta.getLocal());
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

	public void addEquivalenciaProducto(int id, AgregarEquivalenciaProducto userResta) throws Exception {
		DAOTablaProductoSingular daoIngrediente = new DAOTablaProductoSingular();
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoIngrediente.setConn(conn);
			daoUsuario.setConn(conn);
			if(daoUsuario.buscarUsuarioPorCedula(userResta.getCedulaUsuarioRestaurante()) == null || userResta.getIdProducto() == 0)
			{
				throw new NoSuchElementException("no se encontro el usuario restaurante con la cedula: "+userResta.getCedulaUsuarioRestaurante());
			}
			if(!daoUsuario.buscarUsuarioPorCedula(userResta.getCedulaUsuarioRestaurante()).getRol().equals("RESTAURANTE"))
			{
				throw new NoPermissionException("no se tienen los permisos para relizar esta accion");
			}
			if(userResta.getIdProducto()==id)
			{
				throw new NoPermissionException("No se puede agregar una equivalencia con el mismo producto");
			}

			//////transaccion

			daoIngrediente.addEquivalenciaProducto(id,userResta.getIdProducto(),userResta.getLocal());
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

	public Cliente getInfoCliente(int id) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			Cliente cliente=null;
			//////transaccion
			if(daoUsuario.buscarUsuarioPorCedula(id)==null)
			{
				throw new NoSuchElementException("no existe el usuario con la cedula dada");
			}
			if(!daoUsuario.buscarUsuarioPorCedula(id).getRol().equals("CLIENTE"))
			{
				throw new NoPermissionException("no se puede obtener la informacion de este usuario");
			}

			cliente=daoUsuario.getInfoUsuario(id);

			conn.commit();

			return cliente;
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

	public ProductoSingular darProductoMasOfrecido() throws Exception{
		DAOTablaProductoSingular daoProducto = new DAOTablaProductoSingular();
		try 
		{
			this.conn = darConexion();
			daoProducto.setConn(conn);
			ProductoSingular producto=null;
			//////transaccion

			producto=daoProducto.getProductoMasOfrecido();

			conn.commit();

			return producto;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch(NoSuchElementException e) {
			System.err.println("noSuchElementException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoProducto.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void cancelarPedido(int id) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		DAOTablaPedido daoPedido= new DAOTablaPedido();

		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoPedido.setConn(conn);
			//////transaccion

			Pedido ped= daoPedido.buscarPedidoById(id);
			if(ped.getServido().equals("T"))
			{
				throw new NoPermissionException("no se puede cancelar un pedido ya servido");
			}

			daoPedido.cancelarPedido(ped);
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

	public void cancelarPedidoProducto(int id, int id2) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		DAOTablaPedido daoPedido= new DAOTablaPedido();

		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoPedido.setConn(conn);
			//////transaccion

			Pedido ped= daoPedido.buscarPedidoById(id);
			if(ped.getServido().equals("T"))
			{
				throw new NoPermissionException("no se puede cancelar un pedido ya servido");
			}

			daoPedido.cancelarPedidoProducto(id,id2);
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

	public void addPedidoMesa(PedidoMesa pedido) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		DAOTablaPedido daoPedido= new DAOTablaPedido();
		DAOTablaProductoSingular daoTablaProductoSingular= new DAOTablaProductoSingular();
		DAOTablaMenu daoTablaMenu=new DAOTablaMenu();

		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoPedido.setConn(conn);
			daoTablaProductoSingular.setConn(conn);
			daoTablaMenu.setConn(conn);

			//////transaccion

			if(pedido.getPedidos()!=null)
			{	
				daoPedido.addPedidoMesa(pedido);
				Iterator<Pedido> iter= pedido.getPedidos().iterator();

				while(iter.hasNext())
				{
					Pedido ped=iter.next();
					ped.setIdMesa(pedido.getIdMesa());
					addPedido(ped);
				}

				double totalValue=0;
				for(int i =0; i<pedido.getPedidos().size();i++)
				{
					Pedido ped = pedido.getPedidos().get(i);
					if(ped.getIdPedido()>0)
					{
						totalValue+=daoPedido.precioPedido(ped.getIdPedido());
					}
				}

				daoPedido.setPrecioMesa(totalValue, pedido.getIdMesa());
			}
			else {
				throw new NoPermissionException("no se puede agregar un pedido de ningun producto");
			}
			this.conn = darConexion();
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

	public ArrayList<ConsultarConsumoCliente> getConsumoCliente(long id) throws Exception {

		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			ArrayList<ConsultarConsumoCliente> cliente=new ArrayList<>();
			//////transaccion
			if(daoUsuario.buscarUsuarioPorCedula(id)==null)
			{
				throw new NoSuchElementException("no existe el usuario con la cedula dada");
			}
			if(daoUsuario.buscarUsuarioPorCedula(id).getRol().equals("RESTAURANTE"))
			{
				throw new NoPermissionException("no puede obtener la informacion un usuario restaurante");
			}
			if(daoUsuario.buscarUsuarioPorCedula(id).getRol().equals("CLIENTE"))
			{
				cliente.add(daoUsuario.getConsumoUsuario(id));
			}
			if(daoUsuario.buscarUsuarioPorCedula(id).getRol().equals("ADMINISTRADOR"))
			{
				cliente=daoUsuario.getConsumoUsuarios(); 
			}

			conn.commit();

			return cliente;
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

	public ArrayList<ConsumoCliente> getConsumoRotanda(ConsumoRotonda consumo) throws Exception {

		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			ArrayList<ConsumoCliente> cliente=new ArrayList<>();
			//////transaccion
			if(daoUsuario.buscarUsuarioPorCedula(consumo.getCedula())==null)
			{
				throw new NoSuchElementException("no existe el usuario con la cedula dada");
			}
			if(daoUsuario.buscarUsuarioPorCedula(consumo.getCedula()).getRol().equals("RESTAURANTE"))
			{

				cliente=daoUsuario.getConsumo(consumo.getLocal(),consumo.getFechaInicio(),consumo.getFechaFin());
			}
			if(daoUsuario.buscarUsuarioPorCedula(consumo.getCedula()).getRol().equals("CLIENTE"))
			{
				throw new NoPermissionException("no puede obtener la informacion un usuario restaurante");
			}
			if(daoUsuario.buscarUsuarioPorCedula(consumo.getCedula()).getRol().equals("ADMINISTRADOR"))
			{
				cliente=daoUsuario.getConsumo(consumo);
			}

			conn.commit();

			return cliente;
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

	public ArrayList<ConsumoCliente> getNoConsumoRotanda(ConsumoRotonda consumo) throws Exception {

		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			ArrayList<ConsumoCliente> cliente=new ArrayList<>();
			//////transaccion
			if(daoUsuario.buscarUsuarioPorCedula(consumo.getCedula())==null)
			{
				throw new NoSuchElementException("no existe el usuario con la cedula dada");
			}
			if(daoUsuario.buscarUsuarioPorCedula(consumo.getCedula()).getRol().equals("RESTAURANTE"))
			{

				cliente=daoUsuario.getNoConsumo(consumo.getLocal(),consumo.getFechaInicio(),consumo.getFechaFin());
			}
			if(daoUsuario.buscarUsuarioPorCedula(consumo.getCedula()).getRol().equals("CLIENTE"))
			{
				throw new NoPermissionException("no puede obtener la informacion un usuario restaurante");
			}
			if(daoUsuario.buscarUsuarioPorCedula(consumo.getCedula()).getRol().equals("ADMINISTRADOR"))
			{
				cliente=daoUsuario.getNoConsumo(consumo);
			}

			conn.commit();

			return cliente;
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

	public FuncionamientoRotonda[] getFuncionamiento(long id) throws Exception{
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			daoPedido.setConn(conn);
			FuncionamientoRotonda[] cliente=null;
			//////transaccion
			if(daoUsuario.buscarUsuarioPorCedula(id)==null)
			{
				throw new NoSuchElementException("no existe el usuario con la cedula dada");
			}
			if(daoUsuario.buscarUsuarioPorCedula(id).getRol().equals("RESTAURANTE"))
			{

				throw new NoPermissionException("no puede obtener la informacion un usuario restaurante");
			}
			if(daoUsuario.buscarUsuarioPorCedula(id).getRol().equals("CLIENTE"))
			{
				throw new NoPermissionException("no puede obtener la informacion un usuario cliente");
			}
			if(daoUsuario.buscarUsuarioPorCedula(id).getRol().equals("ADMINISTRADOR"))
			{
				cliente=daoPedido.getFuncionamiento();
			}

			conn.commit();

			return cliente;
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

	public ArrayList<Usuario> getBuenosClientes(long id) throws Exception{
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try 
		{
			this.conn = darConexion();
			daoUsuario.setConn(conn);
			ArrayList<Usuario> cliente=null;
			//////transaccion
			if(daoUsuario.buscarUsuarioPorCedula(id)==null)
			{
				throw new NoSuchElementException("no existe el usuario con la cedula dada");
			}
			if(daoUsuario.buscarUsuarioPorCedula(id).getRol().equals("RESTAURANTE"))
			{

				throw new NoPermissionException("no puede obtener la informacion un usuario restaurante");
			}
			if(daoUsuario.buscarUsuarioPorCedula(id).getRol().equals("CLIENTE"))
			{
				throw new NoPermissionException("no puede obtener la informacion un usuario cliente");
			}
			if(daoUsuario.buscarUsuarioPorCedula(id).getRol().equals("ADMINISTRADOR"))
			{
				cliente=daoUsuario.getBuenosUsuarios();
			}

			conn.commit();

			return cliente;
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


	public void addReserva(AgregarReserva reserva){
		DAOTablaReserva daoReserva= new DAOTablaReserva();
		//DAOTablaCliente daoClientes = new DAOTablaClientes();
		try 
		{
			this.conn = darConexion();
			daoReserva.setConn(conn);
			//daoClientes.setConn(conn);
			Cliente cli = daoClientes.buscarClientePorCedula(reserva.getIdCliente());
			if(daoClientes.buscarClientePorCedula(reserva.getIdCliente()) != null){
				throw new NoSuchElementException("No se encontró el cliente con la cedula: " + reserva.getIdCliente());
			}

			Restaurante restaurante = usuarioCliente.getRestaurante();
			//////transaccion

			daoReserva.addReserva(reserva);;
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

