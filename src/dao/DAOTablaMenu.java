package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Menu;
import vos.ProductoSingular;

public class DAOTablaMenu {

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
	public DAOTablaMenu() {
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

	public ArrayList<Menu> darMenus() throws SQLException, Exception {
		ArrayList<Menu> resp = new ArrayList<Menu>();

		String sql = "SELECT * FROM MENU";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			double precio = rs.getDouble("PRECIO");
			
			resp.add(new Menu(id, precio));
		}
		return resp;
	}

	public ArrayList<Menu> buscarProductoSigularPorName(String name) throws SQLException, Exception {
		ArrayList<Menu> resp = new ArrayList<Menu>();

		String sql = "SELECT * FROM MENU WHERE NAME ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			double precio = rs.getDouble("PRECIO");
			
			resp.add(new Menu(id, precio));
		}

		return resp;
	}

	public Menu buscarMenuPorId(Long id) throws SQLException, Exception 
	{
		Menu resp = null;

		String sql = "SELECT * FROM RESTAURANTE WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long idP = rs.getLong("ID");
			double precio = rs.getDouble("PRECIO");
			
			resp = new Menu(idP, precio);
		}

		return resp;
	}

	public void addMenu(Menu par) throws SQLException, Exception {

		String sql = "INSERT INTO MENU VALUES (";
		sql += par.getId() + ",";
		sql += par.getPrecio() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateMenu(Menu par) throws SQLException, Exception {

		String sql = "UPDATE MENU SET ";
		sql += "ID= " + par.getId() + ",";
		sql += "PRECIO= " + par.getPrecio() + ",";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deleteMenu(Menu par) throws SQLException, Exception {

		String sql = "DELETE FROM MENU";
		sql += " WHERE ID = " + par.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
