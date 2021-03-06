package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;

public class DAOTablaCliente {

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
	public DAOTablaCliente() {
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
	
	
	public Cliente buscarClientePorCedula(int cedula) throws SQLException{
		Cliente cliente = null;

		String sql = "SELECT * FROM CLIENTE WHERE ID = " + cedula;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Integer id = rs.getInt("ID");
			String nombre = rs.getString("NOMBRE");
			cliente = new Cliente(id, nombre);
			System.out.println(cliente == null);
		}

		return cliente;
	}
	
	public ArrayList<Integer> getBuenosClientes() throws SQLException{
		ArrayList<Integer> resp = new ArrayList<>();
		String sql = "select distinct(cliente) from(SELECT DISTINCT(ID_CLIENTE) AS CLIENTE FROM RESERVA WHERE INGRESO >= 300000 UNION SELECT DISTINCT(CLI) AS CLIENTE FROM(SELECT ID_CLIENTE AS CLI, FECHA_INICIO FROM RESERVA) WHERE (SELECT COUNT(DISTINCT(extract(year from FECHA_INICIO))) FROM RESERVA WHERE ID_CLIENTE = CLI GROUP BY ID_CLIENTE)>=2 AND (SELECT COUNT(DISTINCT(extract(month from FECHA_INICIO))) FROM RESERVA WHERE ID_CLIENTE = CLI GROUP BY ID_CLIENTE)>=12 UNION SELECT DISTINCT(ID_CLIENTE) AS CLIENTE FROM RESERVA WHERE ID_HOSPEDAJE IN (SELECT ID_HOSPEDAJE FROM HABITACIONHOTEL WHERE TIPOHABITACION = 'Suite'))";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()) {
			resp.add(rs.getInt("CLIENTE"));
		}
		
		return resp;
	}
}