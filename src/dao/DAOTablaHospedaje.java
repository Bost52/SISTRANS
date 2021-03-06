package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;
import vos.Hospedaje;
import vos.Reserva;

public class DAOTablaHospedaje {

	
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
	public DAOTablaHospedaje() {
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
	
	
	public Hospedaje buscarHospedaje(Integer id) throws SQLException{
		Hospedaje hospedaje = null;

		String sql = "SELECT * FROM HOSPEDAJE WHERE ID = " + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Integer idx = rs.getInt("ID");
			String tipo = rs.getString("TIPO");
			hospedaje = new Hospedaje(idx, tipo);
		}

		return hospedaje;
	}
	
	
	public void deleteHospedaje(Integer id) throws SQLException{
		String sql = "DELETE FROM HOSPEDAJE WHERE ID = "+id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
	public ArrayList<Hospedaje> getHospedajes() throws SQLException{
		ArrayList<Hospedaje> hospedajes = new ArrayList<Hospedaje>();

		String sql = "SELECT * FROM HOSPEDAJE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Integer idx = rs.getInt("ID");
			String tipo = rs.getString("TIPO");
			hospedajes.add(new Hospedaje(idx, tipo));
		}
		return hospedajes;
	}
}
