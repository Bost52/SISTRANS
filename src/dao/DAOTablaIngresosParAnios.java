package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.IngresosParAnios;
import vos.Reserva;

public class DAOTablaIngresosParAnios {

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
	public DAOTablaIngresosParAnios() {
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
	
	public ArrayList<IngresosParAnios> dineroRecibidoPorProveedorParAnios() throws SQLException, Exception {
		
		Integer idOperador;
		Integer ingresos;
		ArrayList<IngresosParAnios> rpta = new ArrayList<IngresosParAnios>();
		
		String sql = "SELECT ID, SUM(INGRESOS) AS INGRESOS_TOTALES" + 
				"FROM((SELECT HOSTAL.ID_EMPRESA AS ID, SUM(T2.RECIBIDO) AS INGRESOS" + 
				"FROM (SELECT T1.ID_HOSPEDAJE, HABITACIONHOSTAL.COSTOPORNOCHE AS RECIBIDO, T1.ID_CLIENTE, HABITACIONHOSTAL.ID_HOSTAL" + 
				"FROM(SELECT RESERVA.ID_HOSPEDAJE, RESERVA.ID_CLIENTE" + 
				"FROM RESERVA" + 
				"WHERE RESERVA.FECHA_TERMINACION BETWEEN '01/01/2017' AND '31/12/2018')T1 INNER JOIN HABITACIONHOSTAL " + 
				"ON T1.ID_HOSPEDAJE = HABITACIONHOSTAL.ID_HOSPEDAJE)T2 INNER JOIN HOSTAL ON T2.ID_HOSTAL = HOSTAL.ID_EMPRESA" + 
				"GROUP BY HOSTAL.ID_EMPRESA)" + 
				"UNION" + 
				"(SELECT HOTEL.ID_EMPRESA AS ID, SUM(T2.RECIBIDO) AS INGRESOS" + 
				"FROM (SELECT T1.ID_HOSPEDAJE, HABITACIONHOTEL.COSTOPORNOCHE AS RECIBIDO, T1.ID_CLIENTE, HABITACIONHOTEL.ID_HOTEL" + 
				"FROM(SELECT RESERVA.ID_HOSPEDAJE, RESERVA.ID_CLIENTE" + 
				"FROM RESERVA" + 
				"WHERE RESERVA.FECHA_TERMINACION BETWEEN '01/01/2017' AND '31/12/2018')T1 INNER JOIN HABITACIONHOTEL " + 
				"ON T1.ID_HOSPEDAJE = HABITACIONHOTEL.ID_HOSPEDAJE)T2 INNER JOIN HOTEL ON T2.ID_HOTEL = HOTEL.ID_EMPRESA" + 
				"GROUP BY HOTEL.ID_EMPRESA)" + 
				"UNION" + 
				"(SELECT MIEMBRO_UNIVERSITARIO.ID_PERSONA AS ID, SUM(T2.RECIBIDO) AS INGRESOS" + 
				"FROM (SELECT T1.ID_HOSPEDAJE, APARTAMENTO.COSTO_POR_NOCHE AS RECIBIDO, T1.ID_CLIENTE, APARTAMENTO.ID_MIEMBRO_UNIVERSITARIO" + 
				"FROM(SELECT RESERVA.ID_HOSPEDAJE, RESERVA.ID_CLIENTE" + 
				"FROM RESERVA" + 
				"WHERE RESERVA.FECHA_TERMINACION BETWEEN '01/01/2017' AND '31/12/2018')T1 INNER JOIN APARTAMENTO" + 
				"ON T1.ID_HOSPEDAJE = APARTAMENTO.ID_HOSPEDAJE)T2 INNER JOIN MIEMBRO_UNIVERSITARIO ON T2.ID_MIEMBRO_UNIVERSITARIO = MIEMBRO_UNIVERSITARIO.ID_PERSONA" + 
				"GROUP BY MIEMBRO_UNIVERSITARIO.ID_PERSONA)" + 
				"UNION" + 
				"(SELECT PERSONA_NATURAL.ID_OPERADOR AS ID, SUM(T2.RECIBIDO) AS INGRESOS" + 
				"FROM (SELECT T1.ID_HOSPEDAJE, VIVIENDA.COSTOPORNOCHE AS RECIBIDO, T1.ID_CLIENTE, VIVIENDA.ID_PERSONA" + 
				"FROM(SELECT RESERVA.ID_HOSPEDAJE, RESERVA.ID_CLIENTE" + 
				"FROM RESERVA" + 
				"WHERE RESERVA.FECHA_TERMINACION BETWEEN '01/01/2017' AND '31/12/2018')T1 INNER JOIN VIVIENDA" + 
				"ON T1.ID_HOSPEDAJE = VIVIENDA.ID_HOSPEDAJE)T2 INNER JOIN PERSONA_NATURAL ON T2.ID_PERSONA = PERSONA_NATURAL.ID_OPERADOR" + 
				"GROUP BY PERSONA_NATURAL.ID_OPERADOR))" + 
				"GROUP BY ID";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()) {
			idOperador = rs.getInt("ID");
			ingresos = rs.getInt("INGRESOS_TOTALES");
			rpta.add(new IngresosParAnios(idOperador, ingresos));
		}

		return rpta;
	}
	
}
