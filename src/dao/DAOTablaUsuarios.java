package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Categoria;
import vos.Cliente;
import vos.ConsultarConsumoCliente;
import vos.Menu;
import vos.Pedido;
import vos.Preferencia;
import vos.ProductoSingular;
import vos.Usuario;

public class DAOTablaUsuarios {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAORestaurantes
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaUsuarios() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}

	public ArrayList<Usuario> darUsuarios() throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			long cedula = rs.getLong("CEDULA");
			String email = rs.getString("EMAIL");
			String rol = rs.getString("ROL");
			usuarios.add(new Usuario(cedula, nombre, email, rol));
		}
		return usuarios;
	}

	public Usuario buscarUsuarioPorCedula(long cedula) throws SQLException, Exception 
	{
		Usuario usuario = null;

		String sql = "SELECT * FROM USUARIO WHERE CEDULA =" + cedula;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String nombre = rs.getString("NOMBRE");
			int id = rs.getInt("CEDULA");
			String email = rs.getString("EMAIL");
			String rol = rs.getString("ROL");

			usuario = new Usuario(id, nombre, email, rol);
		}

		return usuario;
	}


	public void addUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "insert into USUARIO (CEDULA, ROL, EMAIL, NOMBRE) values ("+usuario.getCedula()+", '"+usuario.getRol()+"', '"+usuario.getEmail()+"', '"+usuario.getNombre()+"')";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void addPreferencia(int id,int cedula, int idCategoria, double maximo, double minimo, int idZona) throws SQLException
	{
		String sql = "insert into PREFERENCIAS (IDPREFERENCIA, CEDULA, IDCATEGORIA, MAXIMO, MINIMO,IDZONA) values ("+id+","+cedula+", "+idCategoria+", "+maximo+", "+minimo+", "+idZona+")";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void updatePreferencia(int id,int cedula, int idCategoria, double maximo, double minimo, int idZona) throws SQLException,Exception
	{
		String sql = "update PREFERENCIAS set IDCATEGORIA="+idCategoria+", MAXIMO="+maximo+", MINIMO="+minimo+",IDZONA="+idZona+" WHERE IDPREFERENCIA="+id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void deletePreferencia(int id) throws SQLException,Exception
	{
		String sql = "delete from PREFERENCIAS  WHERE IDPREFERENCIA="+id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public Cliente getInfoUsuario(int id) throws Exception {
		Cliente cliente =new Cliente( );
		String sql = "SELECT * FROM USUARIO WHERE CEDULA="+id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();

		if(rs.next()) {
			cliente.setCedula(id);
			cliente.setEmail(rs.getString("EMAIL"));
			cliente.setNombre(rs.getString("NOMBRE"));
		}

		String sql2 = "SELECT * FROM (PREFERENCIAS NATURAL JOIN CATEGORIAPRODUCTO) natural join (ZONA INNER join TIPODECOMIDA ON IDTIPO=IDTIPOCOMIDA) WHERE CEDULA="+id;
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2=prepStmt2.executeQuery();	
		ArrayList<Preferencia> preferencias= new ArrayList<>();

		while(rs2.next()) {
			Preferencia pref=new Preferencia(rs2.getInt("IDPREFERENCIA"), id, rs2.getInt("IDZONA"), rs2.getInt("IDCATEGORIA"), rs2.getDouble("MAXIMO") ,(double) rs2.getDouble("MINIMO"));
			preferencias.add(pref);
		}

		cliente.setPreferencias(preferencias);

		String sql3 = "SELECT * FROM pedido WHERE CEDULA="+id;
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		ResultSet rs3=prepStmt3.executeQuery();	
		ArrayList<Pedido> pedidos= new ArrayList<>();	
		
		while(rs3.next()) {
			Pedido ped= new Pedido(null, rs3.getDate("FECHAYHORA").toString(), rs3.getInt("IDPEDIDO"), rs3.getInt("IDPAGO"), this.buscarUsuarioPorCedula(id), rs3.getString("SERVIDO"));
			pedidos.add(ped);
		}
		
		cliente.setPedidos(pedidos);
		
		cliente.setFrecuencia(((double)pedidos.size())/7);
		

		return cliente;
	}

	public ConsultarConsumoCliente getConsumoUsuario(long id) throws SQLException {
		ConsultarConsumoCliente cliente =new ConsultarConsumoCliente();
		String sql = "SELECT * FROM ((PEDIDO NATURAL JOIN PEDIDOPRODUCTO)Natural join producto)natural join CATEGORIAPRODUCTO WHERE CEDULA ="+id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
		cliente.setProductos(new ArrayList<>());
		cliente.setProductosMesa(new ArrayList<>());
		cliente.setCedula(id);
		cliente.setMenus(new ArrayList<>());
		cliente.setMenusMesa(new ArrayList<>());

		while(rs.next()) {
			Categoria cat = new Categoria(rs.getInt("IDCATEGORIA"), rs.getString("CATEGORIA"));
			ProductoSingular prod= new ProductoSingular(rs.getInt("IDPRODUCTO"), rs.getString("NOMBRE"), rs.getString("DESCRIPCIONESPANOL"), rs.getString("DESCRIPCIONINGLES"), cat);
			if(rs.getInt("IDMESA")>0 )
			{
				cliente.getProductosMesa().add(prod);
			}
			else {
				cliente.getProductos().add(prod);
			}
		}
		
		String sql2 = "SELECT * FROM (PEDIDO NATURAL JOIN PEDIDOMENU) natural join Menu WHERE CEDULA ="+id;

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2=prepStmt2.executeQuery();

		while(rs2.next()) {
			Menu menu=new Menu(rs2.getDouble("COSTE"), rs2.getInt("CANTIDAD"), rs2.getInt("MAX"), rs2.getLong("IDMENU"), rs2.getDouble("PRECIOTOTAL"), rs2.getString("NOMBRE"), rs2.getInt("LOCAL"));
			if(rs2.getInt("IDMESA")>0 )
			{
				cliente.getMenusMesa().add(menu);
			}
			else {
				cliente.getMenus().add(menu);
			}
		}
		
		return cliente;
	}

	public ArrayList<ConsultarConsumoCliente> getConsumoUsuarios() throws SQLException {
		
		ArrayList<ConsultarConsumoCliente> lista= new ArrayList<>();
		String sql = "SELECT * FROM USUARIO WHERE ROL='CLIENTE'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
		
		while(rs.next())
		{
			lista.add(getConsumoUsuario(rs.getLong("CEDULA")));
		}
		
		return lista;
	}
}
