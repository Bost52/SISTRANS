/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Monitores 2017-20
 */
public class DAOTablaRestaurantes {


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
	public DAOTablaRestaurantes() {
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

	public ArrayList<Restaurante> darRestaurantes() throws SQLException, Exception {
		
		DAOTablaUsuarios daoUsuario= new DAOTablaUsuarios();
		DAOTablaTipoDeComida daoTipoComida = new DAOTablaTipoDeComida();
		DAOTablaZona daoZona= new DAOTablaZona();
		daoZona.setConn(conn);
		daoTipoComida.setConn(conn);
		daoUsuario.setConn(conn);
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			int local = rs.getInt("LOCAL");
			String urlPagina = rs.getString("URLPAGINAWEB");
			Usuario representante = daoUsuario.buscarUsuarioPorCedula((rs.getInt("IDREPRESENTANTE")));
			TipoDeComida tipoComida=daoTipoComida.buscarTipoComidaPorId(rs.getInt("IDTIPOCOMIDA"));
			Zona zona = daoZona.buscarZonaPorId(rs.getInt("IDZONA"));
			restaurantes.add(new Restaurante(local, nombre, urlPagina, representante, tipoComida,zona));
		}
		return restaurantes;
	}
	public void addRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "insert into Restaurante (NOMBRE, URLPAGINAWEB, IDTIPOCOMIDA, IDZONA, IDREPRESENTANTE) values ('"+restaurante.getNombre()+"', '"+restaurante.getUrlPaginaWeb()+"', "+restaurante.getTipoCom().getIdTipo()+", "+restaurante.getZona().getIdZona()+", "+restaurante.getRepresentante().getCedula()+")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
//	
//	public void updateRestaurante(Restaurante restaurante) throws SQLException, Exception {
//
//		String sql = "UPDATE RESTAURANTE SET ";
//		sql += "NAME='" + restaurante.getName() + "',";
//		sql += " WHERE ID = " + restaurante.getId();
//
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//	}
//
//
//	public void deleteRestaurante(Restaurante video) throws SQLException, Exception {
//
//		String sql = "DELETE FROM RESTAURANTE";
//		sql += " WHERE ID = " + video.getId();
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//	}
	

}
