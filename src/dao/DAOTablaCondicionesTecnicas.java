package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOTablaCondicionesTecnicas {

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
	public DAOTablaCondicionesTecnicas() {
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

	public ArrayList<CondicionesTecnicas> darCondicionesTecnicas() throws SQLException, Exception {
		ArrayList<ClienteFrecuente> resp = new ArrayList<ClienteFrecuente>();

		String sql = "SELECT * FROM CLIENTEFRECUENTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			Long pse = rs.getLong("PSE");
			
			resp.add(new ClienteFrecuente(id, nombre, pse));
		}
		return resp;
	}

	public ArrayList<ClienteFrecuente> buscarProductoSigularPorName(String name) throws SQLException, Exception {
		ArrayList<ClienteFrecuente> resp = new ArrayList<ClienteFrecuente>();

		String sql = "SELECT * FROM CLIENTEFRECUENTE WHERE NAME ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			Long pse = rs.getLong("PSE");
			
			resp.add(new ClienteFrecuente(id, nombre, pse));
		}

		return resp;
	}

	public ClienteFrecuente buscarClienteFrecuentePorId(Long id) throws SQLException, Exception 
	{
		ClienteFrecuente resp = null;

		String sql = "SELECT * FROM RESTAURANTE WHERE ID =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long idP = rs.getLong("ID");
			String descripcion = rs.getString("DESESP");
			String descripcionTraducida = rs.getString("DESING");
			
			resp = new ClienteFrecuente(id, nombre, pse);
		}

		return resp;
	}

	public void addClienteFrecuente(ClienteFrecuente par) throws SQLException, Exception {

		String sql = "INSERT INTO CLIENTEFRECUENTE VALUES (";
		sql += par.getId() + ",'";
		sql += par.getNombre() + "',";
		sql += par.getCuentaPSE() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void updateClienteFrecuente(ClienteFrecuente par) throws SQLException, Exception {

		String sql = "UPDATE CLIENTEFRECUENTE SET ";
		sql += "ID= " + par.getId() + ",";
		sql += "NOMBRE= '" + par.getNombre() + "',";
		sql += "PSE= " + par.getCuentaPSE();



		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deleteClienteFrecuente(ClienteFrecuente par) throws SQLException, Exception {

		String sql = "DELETE FROM CLIENTEFRECUENTE";
		sql += " WHERE ID = " + par.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
