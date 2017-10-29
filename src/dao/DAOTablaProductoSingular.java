package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Categoria;
import vos.ConsultarProductosPorFiltros;
import vos.ProductoSingular;

public class DAOTablaProductoSingular {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaProductoSingular() {
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

	public ArrayList<ProductoSingular> darProductos() throws SQLException, Exception {
		ArrayList<ProductoSingular> productos = new ArrayList<ProductoSingular>();

		DAOTablaCategoria daoCategoria= new DAOTablaCategoria();
		daoCategoria.setConn(conn);

		String sql = "SELECT * FROM PRODUCTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			int id = rs.getInt("IDPRODUCTO");
			String descripcion = rs.getString("DESCRIPCIONESPANOL");
			String descripcionTraducida = rs.getString("DESCRIPCIONINGLES");
			Categoria categoria=daoCategoria.buscarCategoria(rs.getInt("IDCATEGORIA"));

			productos.add(new ProductoSingular(id, name, descripcion, descripcionTraducida, categoria));
		}
		return productos;
	}

	public ProductoSingular buscarProductoSingularPorId(int id) throws SQLException, Exception 
	{

		DAOTablaCategoria daoCategoria= new DAOTablaCategoria();
		daoCategoria.setConn(conn);

		ProductoSingular resp = null;

		String sql = "SELECT * FROM PRODUCTO WHERE IDPRODUCTO =" + id;


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String name = rs.getString("NOMBRE");
			int idProducto = rs.getInt("IDPRODUCTO");
			String descripcion = rs.getString("DESCRIPCIONESPANOL");
			String descripcionTraducida = rs.getString("DESCRIPCIONINGLES");
			Categoria categoria=daoCategoria.buscarCategoria(rs.getInt("IDCATEGORIA"));

			resp=new ProductoSingular(idProducto, name, descripcion, descripcionTraducida, categoria);
		}

		return resp;
	}

	public void addProductoSingular(ProductoSingular par,int cantidad, int local, double precio, double costo, int max) throws SQLException, Exception {

		if(par.getIdProducto()!=0 && buscarProductoSingularPorId(par.getIdProducto())!=null)
		{

			String sql = "insert into OFRECEPRODUCTO (IDPRODUCTO, LOCAL, CANTIDAD, PRECIO, COSTE) values ('"+par.getIdProducto()+"', "+local+", "+cantidad+", "+precio+", "+costo+")";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
		else
		{
			String sqll = "insert into PRODUCTO (IDPRODUCTO, NOMBRE, DESCRIPCIONESPANOL, DESCRIPCIONINGLES, IDCATEGORIA) values ("+par.getIdProducto()+",'"+par.getNombre()+"', '"+par.getDescripcion()+"', '"+par.getDescripcionTraducida()+"', "+par.getCategoria().getIdCategoria()+")";

			PreparedStatement prepStmt = conn.prepareStatement(sqll);
			recursos.add(prepStmt);
			prepStmt.executeQuery();

			String sql2 = "insert into OFRECEPRODUCTO (IDPRODUCTO, LOCAL, CANTIDAD, PRECIO, COSTE, MAX) values ('"+par.getIdProducto()+"', "+local+", "+cantidad+", "+precio+", "+costo+","+max+")";

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();
		}

	}

	public void addEquivalenciaProducto(int idPrincipal,int idProducto, int local) throws SQLException {
		String sql = "INSERT INTO SIMILITUDESPRODUCTO  ( IDPROD1, IDPROD2, LOCAL) VALUES (";
		sql += idPrincipal + ",";
		sql += idProducto + ",";
		sql +=local + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public ProductoSingular getProductoMasOfrecido() throws SQLException {
		String sql = "SELECT * \r\n" + 
				"FROM PRODUCTO NATURAL JOIN(SELECT * \r\n" + 
				"FROM (SELECT  MAX(TOTALMENUS) AS TOTALMENUS\r\n" + 
				"FROM(SELECT IDPRODUCTO, COUNT(IDMENU) AS TOTALMENUS\r\n" + 
				"FROM (PRODUCTO P NATURAL JOIN TIENEPRODUCTO T) GROUP BY IDPRODUCTO)) NATURAL JOIN (SELECT IDPRODUCTO, COUNT(IDMENU) AS TOTALMENUS\r\n" + 
				"FROM (PRODUCTO P NATURAL JOIN TIENEPRODUCTO T) GROUP BY IDPRODUCTO))";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();

		if(rs.next())
		{
			String sql2 = "SElECT * FROM CATEGORIAPRODUCTO WHERE IDCATEGORIA="+rs.getInt("IDCATEGORIA");


			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2=prepStmt2.executeQuery();

			if(rs2.next())
			{
				Categoria cat= new Categoria(rs.getInt("IDCATEGORIA"), rs2.getString("CATEGORIA"));


				ProductoSingular producto=new ProductoSingular(rs.getInt("IDPRODUCTO"), rs.getString("NOMBRE"), rs.getString("DESCRIPCIONESPANOL"), rs.getString("DESCRIPCIONINGLES"), cat);

				return producto;
			}
		}
		return null;
	}

	public boolean existEquiv(int id1, int id2, int local) throws SQLException
	{

		String sql = "SELECT * FROM SIMILITUDESPRODUCTO WHERE IDPROD1="+id1 +" AND IDPROD2="+id2+" AND LOCAL="+ local;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		String sql2 = "SELECT * FROM SIMILITUDESPRODUCTO WHERE IDPROD1="+id2 +" AND IDPROD2="+id1+" AND LOCAL="+ local;

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();

		if(rs.next()||rs2.next())
		{
			return true;
		}

		return false;
	}

	public double precioProducto(int local, int idProducto) throws SQLException
	{


		String sql = "SELECT PRECIO FROM OFRECEPRODUCTO WHERE LOCAL="+local+" AND IDPRODUCTO="+ idProducto;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
		{
			return rs.getDouble("PRECIO");
		}
		return 0;
	}

}
