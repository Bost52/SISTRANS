package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Oferta;
import vos.Reserva;

public class DAOTablaOferta{
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
	public DAOTablaOferta() {
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
	
	
	public Oferta buscarOferta(Integer id) throws SQLException, Exception{
		Oferta hospedaje = null;

		String sql = "SELECT * FROM OFERTA WHERE ID_HOSPEDAJE = " + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Integer idx = rs.getInt("ID");
			hospedaje = new Oferta(idx);
		}

		return hospedaje;
	}
	
	
	public void deleteOferta(Integer oferta) throws SQLException, Exception{
		
		String sql = "DELETE FROM OFERTAS WHERE ID_HOSPEDAJE = "+oferta;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
	public ArrayList<Oferta> getOfertas() throws SQLException, Exception{
		ArrayList<Oferta> hospedajes = new ArrayList<Oferta>();

		String sql = "SELECT * FROM OFERTAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Integer idx = rs.getInt("ID");
			hospedajes.add(new Oferta(idx));
		}
		return hospedajes;
	}
	
	public void addOferta(Integer id) throws SQLException, Exception {
		
		String sql = "insert into OFERTAS (ID_HOSPEDAJE) values ("+ id +")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
