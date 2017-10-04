package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingrediente;

public class DAOTablaIngrediente {

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
	public DAOTablaIngrediente() {
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

	public ArrayList<Ingrediente> darIngredientes() throws SQLException, Exception {
		ArrayList<Ingrediente> resp = new ArrayList<Ingrediente>();

		String sql = "SELECT * FROM INGREDIENTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("IDINGREDIENTE");
			String descripcion = rs.getString("DESCRIPCIONESPAÑOL");
			String descripcionTraducida = rs.getString("DESCRIPCIONINGLES");
			int cantidad = rs.getInt("CANTIDAD");
			
			resp.add(new Ingrediente(id, nombre, descripcion, descripcionTraducida, cantidad));
		}
		return resp;
	}

	public ArrayList<Ingrediente> buscarProductoSigularPorName(String name) throws SQLException, Exception {
		ArrayList<Ingrediente> resp = new ArrayList<Ingrediente>();

		String sql = "SELECT * FROM INGREDIENTE WHERE NAME ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("IDINGREDIENTE");
			String descripcion = rs.getString("DESCRIPCIONESPAÑOL");
			String descripcionTraducida = rs.getString("DESCRIPCIONINGLES");
			int cantidad = rs.getInt("CANTIDAD");
			
			resp.add(new Ingrediente(id, nombre, descripcion, descripcionTraducida, cantidad));
		}

		return resp;
	}

	public Ingrediente buscarIngredientePorId(Long id) throws SQLException, Exception 
	{
		Ingrediente resp = null;

		String sql = "SELECT * FROM RESTAURANTE WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long idp = rs.getLong("IDINGREDIENTE");
			String descripcion = rs.getString("DESCRIPCIONESPAÑOL");
			String descripcionTraducida = rs.getString("DESCRIPCIONINGLES");
			int cantidad = rs.getInt("CANTIDAD");
			
			resp = new Ingrediente(idp, nombre, descripcion, descripcionTraducida, cantidad);
		}

		return resp;
	}

	public void addIngrediente(Ingrediente par) throws SQLException, Exception {

		String sql = "INSERT INTO INGREDIENTE  ( IDINGREDIENTE, NOMBRE, DESCRIPCIONESPAÑOL, CANTIDAD, DESCRIPCIONINGLES) VALUES (";
		sql += par.getId() + ",'";
		sql += par.getNombre() + "','";
		sql += par.getDescripcion() + "',";
		sql += par.getCantidad() + ",'";
		sql += par.getDescripcionTraducida() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateIngrediente(Ingrediente par) throws SQLException, Exception {

		String sql = "UPDATE INGREDIENTE SET ";
		sql += "ID= " + par.getId() + ",";
		sql += "NOMBRE= '" + par.getNombre() + "',";
		sql += "DESESP= '" + par.getDescripcion() + "',";
		sql += "DESING= '" + par.getDescripcionTraducida() + "'";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deleteIngrediente(Ingrediente par) throws SQLException, Exception {

		String sql = "DELETE FROM INGREDIENTE";
		sql += " WHERE ID = " + par.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}