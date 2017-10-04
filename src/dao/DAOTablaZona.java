package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Restaurante;
import vos.TipoDeComida;
import vos.Usuario;
import vos.Zona;

public class DAOTablaZona {
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
	public DAOTablaZona() {
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
	
	public Connection getConn() {
		return conn;
	}
	
	public Zona buscarZonaPorId(int id) throws SQLException, Exception 
	{
		DAOTablaTipoDeComida daoTipo =new DAOTablaTipoDeComida();
		daoTipo.setConn(conn);
		Zona zona = null;

		String sql = "SELECT * FROM ZONA WHERE IDZONA =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			TipoDeComida tipo = daoTipo.buscarTipoComidaPorId(rs.getInt("IDTIPOCOMIDA"));
			int id2 = rs.getInt("IDZONA");
			
			zona = new Zona(id2, tipo);
		}

		return zona;
	}

}
