package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Categoria;
import vos.Cliente;
import vos.ConsultarConsumoCliente;
import vos.ConsumoCliente;
import vos.ConsumoRotonda;
import vos.Menu;
import vos.ObjetoAnalisis1;
import vos.ObjetoAnalisis2;
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
			long id = rs.getLong("CEDULA");
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

//	public ArrayList<ConsumoCliente> getConsumo(int local,String fechaInicio, String fechaFin) throws SQLException {
//		ArrayList<ConsumoCliente> cliente =new ArrayList<>();
//		String sql = "SELECT CEDULA, NOMBRE, ROL, EMAIL FROM ( SELECT IDPRODUCTO, LOCAL FROM (OFRECEPRODUCTO NATURAL JOIN RESTAURANTE))a1 INNER JOIN( SELECT USUARIO.CEDULA, NOMBRE, ROL, EMAIL, IDPRODUCTO, LOCAL, IDPEDIDO FROM (USUARIO RIGHT OUTER JOIN PEDIDO ON USUARIO.CEDULA= PEDIDO.CEDULA) NATURAL JOIN PEDIDOPRODUCTO WHERE ROL='CLIENTE' AND (FECHAYHORA BETWEEN  TO_DATE('"+fechaInicio+"') AND TO_DATE('"+fechaFin+"')))us ON  a1.LOCAL=us.LOCAL WHERE us.LOCAL="+local+" GROUP BY CEDULA, NOMBRE, ROL, EMAIL";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs=prepStmt.executeQuery();
//		while(rs.next())
//		{
//			ConsumoCliente con= new ConsumoCliente(rs.getString("EMAIL"), rs.getString("ROL"), rs.getString("NOMBRE"), rs.getLong("CEDULA"));
//			cliente.add(con);
//		}
//		
//		return cliente;
//	}
	
	public ArrayList<ConsumoCliente> getConsumo(int local,String fechaInicio, String fechaFin) throws SQLException {
		ArrayList<ConsumoCliente> cliente =new ArrayList<>();
		String sql = "( SELECT USUARIO.CEDULA, NOMBRE, ROL, EMAIL, IDPRODUCTO, LOCAL, IDPEDIDO FROM (USUARIO RIGHT OUTER JOIN PEDIDO ON USUARIO.CEDULA= PEDIDO.CEDULA) NATURAL JOIN PEDIDOPRODUCTO WHERE ROL='CLIENTE' AND (FECHAYHORA BETWEEN  TO_DATE('"+fechaInicio+"') AND TO_DATE('"+fechaFin+"'))) ";
		String sql2="SELECT IDPRODUCTO, LOCAL  FROM (OFRECEPRODUCTO NATURAL JOIN RESTAURANTE)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2=prepStmt2.executeQuery();
		ArrayList<ObjetoAnalisis1> obs1=new ArrayList<>();
		ArrayList<ObjetoAnalisis2> obs2=new ArrayList<>();
		while(rs.next())
		{
			obs1.add(new ObjetoAnalisis1(rs.getInt("CEDULA"), rs.getString("NOMBRE"), rs.getString("ROL"), rs.getString("EMAIL"), rs.getInt("IDPRODUCTO"), rs.getInt("LOCAL"), rs.getInt("IDPEDIDO")));
		}
		
		while(rs2.next())
		{
			obs2.add(new ObjetoAnalisis2(rs2.getInt("IDPRODUCTO"), rs2.getInt("LOCAL")));
		}
		for(int i=0;i<obs1.size();i++)
		{
			if(obs1.get(i).getLocal()==local) {
				for(int j=i;j<obs2.size();i++) {
					if(obs2.get(j).getLocal()==obs1.get(i).getLocal())
					{
						ConsumoCliente con= new ConsumoCliente(obs1.get(i).getEmail(), obs1.get(i).getRol(), obs1.get(i).getNombre(), obs1.get(i).getCedula());
						cliente.add(con);
					}
				}
			}
		}

		
		return cliente;
	}

	public ArrayList<ConsumoCliente> getConsumo(ConsumoRotonda consumo) throws SQLException {
		ArrayList<ConsumoCliente> lista= new ArrayList<>();
		String sql = "SELECT * FROM RESTAURANTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
		int k=0;
		while(rs.next()&& k++<10)
		{
			ArrayList<ConsumoCliente> con=getConsumo(rs.getInt("LOCAL"), consumo.getFechaInicio(), consumo.getFechaFin());
			for(int i=0;i<con.size();i++)
			{
				lista.add(con.get(i));
			}
		}
		
		return lista;
	}
	
	public ArrayList<ConsumoCliente> getNoConsumo(ConsumoRotonda consumo) throws SQLException {
		ArrayList<ConsumoCliente> lista= new ArrayList<>();
		String sql = "SELECT * FROM RESTAURANTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
		
		while(rs.next())
		{
			ArrayList<ConsumoCliente> con=getNoConsumo(rs.getInt("LOCAL"), consumo.getFechaInicio(), consumo.getFechaFin());
			for(int i=0;i<con.size();i++)
			{
				lista.add(con.get(i));
			}
		}
		
		return lista;
	}
	
	public ArrayList<ConsumoCliente> getNoConsumo(int local,String fechaInicio, String fechaFin) throws SQLException {
		ArrayList<ConsumoCliente> cliente =new ArrayList<>();
		String sql = "SELECT CEDULA, NOMBRE, ROL, EMAIL FROM USUARIO WHERE ROL='CLIENTE' AND (CEDULA) NOT IN(SELECT CEDULA FROM ( SELECT IDPRODUCTO, LOCAL FROM (OFRECEPRODUCTO NATURAL JOIN RESTAURANTE))a1 INNER JOIN( SELECT USUARIO.CEDULA, NOMBRE, ROL, EMAIL, IDPRODUCTO, LOCAL, IDPEDIDO FROM (USUARIO RIGHT OUTER JOIN PEDIDO ON USUARIO.CEDULA= PEDIDO.CEDULA) NATURAL JOIN PEDIDOPRODUCTO WHERE ROL='CLIENTE' AND (FECHAYHORA BETWEEN  TO_DATE('"+fechaInicio+"') AND TO_DATE('"+fechaFin+"')))us ON  a1.LOCAL=us.LOCAL WHERE us.LOCAL="+local+" GROUP BY CEDULA, NOMBRE, ROL, EMAIL)";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
		int k=0;
		while(rs.next() && k++<1000)
		{
			ConsumoCliente con= new ConsumoCliente(rs.getString("EMAIL"), rs.getString("ROL"), rs.getString("NOMBRE"), rs.getLong("CEDULA"));
			cliente.add(con);
		}
		
		return cliente;
	}
	
	public ArrayList<Usuario> getBuenosUsuarios() throws SQLException
	{
		ArrayList<Usuario> cliente =new ArrayList<>();
		String sql = "SELECT CEDULA, NOMBRE, EMAIL, ROL FROM (((SELECT  CEDULA,NOM AS NOMBRE, ROL, EMAIL FROM ((PEDIDO NATURAL JOIN (SELECT CEDULA, ROL, EMAIL, NOMBRE AS NOM FROM USUARIO)) INNER JOIN (PEDIDOPRODUCTO NATURAL JOIN (PRODUCTO NATURAL JOIN OFRECEPRODUCTO))  ON PEDIDO.IDPEDIDO=PEDIDOPRODUCTO.IDPEDIDO)  WHERE IDCATEGORIA=2 AND PRECIO>=36885.855 ) UNION ALL (SELECT CEDULA, NOMBRE,EMAIL,ROL  FROM(SELECT SUM(NUMWEEKS) AS TOTALWEEKS FROM (SELECT COUNT(DISTINCT(WEEK)) AS NUMWEEKS,YEAR FROM(SELECT IDPEDIDO, EXTRACT(YEAR FROM FECHAYHORA) AS YEAR, to_number(to_char(FECHAYHORA,'WW')) AS WEEK FROM (PEDIDO )) GROUP BY YEAR)) NATURAL JOIN (SELECT NOMBRE, ROL, CEDULA, EMAIL, SUM(NUMWEEKS) AS TOTALWEEKS FROM (SELECT COUNT(*) AS NUMWEEKS, NOMBRE, CEDULA, EMAIL, ROL, YEAR FROM (SELECT  EXTRACT(YEAR FROM FECHAYHORA)AS YEAR, CEDULA, ROL, NoMBRE, EMAIL , to_number(to_char(FECHAYHORA,'WW')) AS WEEK FROM (PEDIDO NATURAL JOIN USUARIO)) GROUP BY NOMBRE, ROL, CEDULA, EMAIL, YEAR) GROUP BY NOMBRE, ROL, CEDULA, EMAIL)))) UNION ALL SELECT CEDULA, NOMBRE, ROL, EMAIL FROM (PEDIDO NATURAL JOIN PEDIDOPRODUCTO) NATURAL JOIN USUARIO WHERE CEDULA not IN (SELECT CEDULA FROM (PEDIDO NATURAL JOIN PEDIDOMENU) NATURAL JOIN USUARIO CEDULA ) GROUP BY CEDULA, NOMBRE, EMAIL, ROL FETCH FIRST 1000 ROWS ONLY";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next())
		{
			Usuario con = new Usuario(rs.getLong("CEDULA"), rs.getString("NOMBRE"), rs.getString("EMAIL"), rs.getString("ROL"));
			cliente.add(con);
		}
		
		return cliente;
	}
}
