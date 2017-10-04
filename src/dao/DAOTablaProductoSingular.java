package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

		String sql = "SELECT * FROM PRODUCTOSINGULAR";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			Integer cantidad = rs.getInt("CANTIDAD");
			String descripcion = rs.getString("DESESP");
			String descripcionTraducida = rs.getString("DESING");
			double precio = rs.getDouble("PRECIO");
			double tiempo = rs.getDouble("TIEMPO");
			double costo = rs.getDouble("COSTO");
			
			productos.add(new ProductoSingular(id, precio, cantidad, name, descripcion, descripcionTraducida, tiempo, costo));
		}
		return productos;
	}

	public ArrayList<ProductoSingular> buscarProductoSigularPorName(String name) throws SQLException, Exception {
		ArrayList<ProductoSingular> productos = new ArrayList<ProductoSingular>();

		String sql = "SELECT * FROM PRODUCTOSINGULAR WHERE NAME ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			Integer cantidad = rs.getInt("CANTIDAD");
			String descripcion = rs.getString("DESESP");
			String descripcionTraducida = rs.getString("DESING");
			double precio = rs.getDouble("PRECIO");
			double tiempo = rs.getDouble("TIEMPO");
			double costo = rs.getDouble("COSTO");
			
			productos.add(new ProductoSingular(id, precio, cantidad, nombre, descripcion, descripcionTraducida, tiempo, costo));
		}

		return productos;
	}

	public ProductoSingular buscarProductoSingularPorId(Long id) throws SQLException, Exception 
	{
		ProductoSingular resp = null;

		String sql = "SELECT * FROM PRODUCTOSINGULAR WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String name = rs.getString("NOMBRE");
			Long idP = rs.getLong("ID");
			Integer cantidad = rs.getInt("CANTIDAD");
			String descripcion = rs.getString("DESESP");
			String descripcionTraducida = rs.getString("DESING");
			double precio = rs.getDouble("PRECIO");
			double tiempo = rs.getDouble("TIEMPO");
			double costo = rs.getDouble("COSTO");
			
			resp = new ProductoSingular(idP, precio, cantidad, name, descripcion, descripcionTraducida, tiempo, costo);
		}

		return resp;
	}
	
	
	public ArrayList<ProductoSingular> darProductosPorFiltros(ConsultarProductosPorFiltros filtros){
		ArrayList<ProductoSingular> productos = new ArrayList<ProductoSingular>();
		
		int localRest = filtros.getRestaurante().getLocal();
		double precioMax = filtros.getPrecioMayor();
		double precioMin = filtros.getPrecioMenor();
		String categoria = filtros.getCategoria().toString();
		
		String sql = "SELECT * FROM PRODUCTO, OFRECEPRODUCTO  WHERE ID =";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String name = rs.getString("NOMBRE");
			Long idP = rs.getLong("ID");
			Integer cantidad = rs.getInt("CANTIDAD");
			String descripcion = rs.getString("DESESP");
			String descripcionTraducida = rs.getString("DESING");
			double precio = rs.getDouble("PRECIO");
			double tiempo = rs.getDouble("TIEMPO");
			double costo = rs.getDouble("COSTO");
			
			productos.add(new ProductoSingular(idP, precio, cantidad, name, descripcion, descripcionTraducida, tiempo, costo));
		}
		
		return productos;
	}

	public void addProductoSingular(ProductoSingular par) throws SQLException, Exception {

		String sql = "INSERT INTO PRODUCTOSINGULAR VALUES (";
		sql += par.getId() + ",";
		sql += par.getPrecio() + ",";
		sql += par.getCantidadDisponible() + ",'";
		sql += par.getNombre() + "','";
		sql += par.getDescripcion() + "','";
		sql += par.getDescripcionTraducida() + "',";
		sql += par.getTiempoDePreparacion() + ",";
		sql += par.getCostoProduccion() ;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateProductoSingular(ProductoSingular par) throws SQLException, Exception {

		String sql = "UPDATE PRODUCTOSINGULAR SET ";
		sql += "ID= " + par.getId() + ",";
		sql += "PRECIO= " + par.getPrecio() + ",";
		sql += "CANTIDAD= " + par.getCantidadDisponible() + ",";
		sql += "NOMBRE= '" + par.getNombre() + "',";
		sql += "DESESP= '" + par.getDescripcion() + "',";
		sql += "DESING= '" + par.getDescripcionTraducida() + "',";
		sql += "TIEMPO= " + par.getTiempoDePreparacion() + ",";
		sql += "COSTO= " + par.getCostoProduccion() + "";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deleteProductoSingular(ProductoSingular par) throws SQLException, Exception {

		String sql = "DELETE FROM PRODUCTOSINGULAR";
		sql += " WHERE ID = " + par.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
