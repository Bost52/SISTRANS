package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Funcionamiento;
import vos.IngresosParAnios;

public class DAOTablaOperador {
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
	public DAOTablaOperador() {
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


	public boolean getGerente(int id) throws SQLException{
		String sql = "Select * from  PERSONA_NATURAL where id_operador = " + id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		boolean resp = false;
		
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()) {
			String tipo = rs.getString("TIPO");
			resp = tipo.equals("GERENTE");
		}

		return resp;
	}
	
	public ArrayList<Funcionamiento> funcionamiento1() throws SQLException{
		ArrayList<Funcionamiento> resp = new ArrayList<>();
		String sql = "SELECT ANIO, SEMANA, HOSP FROM(SELECT to_char(fecha_inicio - 7/24,'IYYY') as ANIO, to_char(fecha_inicio - 7/24,'IW') as SEMANA,count(ID_HOSPEDAJE) as RESERVAS_HOSP, ID_HOSPEDAJE AS HOSP FROM reserva GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) WHERE RESERVAS_HOSP >= ALL(SELECT count(ID_HOSPEDAJE) FROM reserva WHERE to_char(fecha_inicio - 7/24,'IYYY')=ANIO AND to_char(fecha_inicio - 7/24,'IW')=SEMANA GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) ORDER BY ANIO,SEMANA";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next()) {
			Funcionamiento fun = new Funcionamiento();
			fun.setAnio(rs.getInt("ANIO"));
			fun.setSemana(rs.getInt("SEMANA"));
			fun.setHospedaje(rs.getInt("HOSP"));
			
			resp.add(fun);
		}
		return resp;
	}
	
	public ArrayList<Funcionamiento> funcionamiento2() throws SQLException{
		ArrayList<Funcionamiento> resp = new ArrayList<>();
		String sql = "SELECT ANIO, SEMANA, HOSP FROM(SELECT to_char(fecha_inicio - 7/24,'IYYY') as ANIO, to_char(fecha_inicio - 7/24,'IW') as SEMANA,count(ID_HOSPEDAJE) as RESERVAS_HOSP, ID_HOSPEDAJE AS HOSP FROM reserva GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) WHERE RESERVAS_HOSP <= ALL(SELECT count(ID_HOSPEDAJE) FROM reserva WHERE to_char(fecha_inicio - 7/24,'IYYY')=ANIO AND to_char(fecha_inicio - 7/24,'IW')=SEMANA GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) ORDER BY ANIO,SEMANA";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next()) {
			Funcionamiento fun = new Funcionamiento();
			fun.setAnio(rs.getInt("ANIO"));
			fun.setSemana(rs.getInt("SEMANA"));
			fun.setHospedaje(rs.getInt("HOSP"));
			
			resp.add(fun);
		}
		return resp;
	}
	
	public ArrayList<Funcionamiento> funcionamiento3() throws SQLException{
		ArrayList<Funcionamiento> resp = new ArrayList<>();
		
		String sql = "SELECT ANIO, SEMANA, ID_MIEMBRO_UNIVERSITARIO FROM(SELECT ANIO, SEMANA, HOSP FROM(SELECT to_char(fecha_inicio - 7/24,'IYYY') as ANIO, to_char(fecha_inicio - 7/24,'IW') as SEMANA,count(ID_HOSPEDAJE) as RESERVAS_HOSP, ID_HOSPEDAJE AS HOSP FROM reserva GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) WHERE RESERVAS_HOSP >= ALL(SELECT count(ID_HOSPEDAJE) FROM reserva WHERE to_char(fecha_inicio - 7/24,'IYYY')=ANIO AND to_char(fecha_inicio - 7/24,'IW')=SEMANA GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE))T1, APARTAMENTO WHERE APARTAMENTO.ID_HOSPEDAJE = T1.HOSP UNION SELECT ANIO, SEMANA, ID_HOSTAL FROM(SELECT ANIO, SEMANA, HOSP FROM(SELECT to_char(fecha_inicio - 7/24,'IYYY') as ANIO, to_char(fecha_inicio - 7/24,'IW') as SEMANA,count(ID_HOSPEDAJE) as RESERVAS_HOSP, ID_HOSPEDAJE AS HOSP FROM reserva GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) WHERE RESERVAS_HOSP >= ALL(SELECT count(ID_HOSPEDAJE) FROM reserva WHERE to_char(fecha_inicio - 7/24,'IYYY')=ANIO AND to_char(fecha_inicio - 7/24,'IW')=SEMANA GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE))T1, HABITACIONHOSTAL WHERE HABITACIONHOSTAL.ID_HOSPEDAJE = T1.HOSP UNION SELECT ANIO, SEMANA, ID_HOTEL FROM(SELECT ANIO, SEMANA, HOSP FROM(SELECT to_char(fecha_inicio - 7/24,'IYYY') as ANIO, to_char(fecha_inicio - 7/24,'IW') as SEMANA,count(ID_HOSPEDAJE) as RESERVAS_HOSP, ID_HOSPEDAJE AS HOSP FROM reserva GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) WHERE RESERVAS_HOSP >= ALL(SELECT count(ID_HOSPEDAJE) FROM reserva WHERE to_char(fecha_inicio - 7/24,'IYYY')=ANIO AND to_char(fecha_inicio - 7/24,'IW')=SEMANA GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE))T1, HABITACIONHOTEL WHERE HABITACIONHOTEL.ID_HOSPEDAJE = T1.HOSP UNION SELECT ANIO, SEMANA, ID_PERSONA FROM(SELECT ANIO, SEMANA, HOSP FROM(SELECT to_char(fecha_inicio - 7/24,'IYYY') as ANIO, to_char(fecha_inicio - 7/24,'IW') as SEMANA,count(ID_HOSPEDAJE) as RESERVAS_HOSP, ID_HOSPEDAJE AS HOSP FROM reserva GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) WHERE RESERVAS_HOSP >= ALL(SELECT count(ID_HOSPEDAJE) FROM reserva WHERE to_char(fecha_inicio - 7/24,'IYYY')=ANIO AND to_char(fecha_inicio - 7/24,'IW')=SEMANA GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE))T1, VIVIENDA WHERE VIVIENDA.ID_HOSPEDAJE = T1.HOSP";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next()) {
			Funcionamiento fun = new Funcionamiento();
			fun.setAnio(rs.getInt("ANIO"));
			fun.setSemana(rs.getInt("SEMANA"));
			fun.setHospedaje(rs.getInt("ID_MIEMBRO_UNIVERSITARIO"));
			
			resp.add(fun);
		}
		return resp;
	}
	
	public ArrayList<Funcionamiento> funcionamiento4() throws SQLException{
		ArrayList<Funcionamiento> resp = new ArrayList<>();
		String sql = "SELECT ANIO, SEMANA, ID_MIEMBRO_UNIVERSITARIO FROM(SELECT ANIO, SEMANA, HOSP FROM(SELECT to_char(fecha_inicio - 7/24,'IYYY') as ANIO, to_char(fecha_inicio - 7/24,'IW') as SEMANA,count(ID_HOSPEDAJE) as RESERVAS_HOSP, ID_HOSPEDAJE AS HOSP FROM reserva GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) WHERE RESERVAS_HOSP <= ALL(SELECT count(ID_HOSPEDAJE) FROM reserva WHERE to_char(fecha_inicio - 7/24,'IYYY')=ANIO AND to_char(fecha_inicio - 7/24,'IW')=SEMANA GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE))T1, APARTAMENTO WHERE APARTAMENTO.ID_HOSPEDAJE = T1.HOSP UNION SELECT ANIO, SEMANA, ID_HOSTAL FROM(SELECT ANIO, SEMANA, HOSP FROM(SELECT to_char(fecha_inicio - 7/24,'IYYY') as ANIO, to_char(fecha_inicio - 7/24,'IW') as SEMANA,count(ID_HOSPEDAJE) as RESERVAS_HOSP, ID_HOSPEDAJE AS HOSP FROM reserva GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) WHERE RESERVAS_HOSP <= ALL(SELECT count(ID_HOSPEDAJE) FROM reserva WHERE to_char(fecha_inicio - 7/24,'IYYY')=ANIO AND to_char(fecha_inicio - 7/24,'IW')=SEMANA GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE))T1, HABITACIONHOSTAL WHERE HABITACIONHOSTAL.ID_HOSPEDAJE = T1.HOSP UNION SELECT ANIO, SEMANA, ID_HOTEL FROM(SELECT ANIO, SEMANA, HOSP FROM(SELECT to_char(fecha_inicio - 7/24,'IYYY') as ANIO, to_char(fecha_inicio - 7/24,'IW') as SEMANA,count(ID_HOSPEDAJE) as RESERVAS_HOSP, ID_HOSPEDAJE AS HOSP FROM reserva GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) WHERE RESERVAS_HOSP <= ALL(SELECT count(ID_HOSPEDAJE) FROM reserva WHERE to_char(fecha_inicio - 7/24,'IYYY')=ANIO AND to_char(fecha_inicio - 7/24,'IW')=SEMANA GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE))T1, HABITACIONHOTEL WHERE HABITACIONHOTEL.ID_HOSPEDAJE = T1.HOSP UNION SELECT ANIO, SEMANA, ID_PERSONA FROM(SELECT ANIO, SEMANA, HOSP FROM(SELECT to_char(fecha_inicio - 7/24,'IYYY') as ANIO, to_char(fecha_inicio - 7/24,'IW') as SEMANA,count(ID_HOSPEDAJE) as RESERVAS_HOSP, ID_HOSPEDAJE AS HOSP FROM reserva GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE) WHERE RESERVAS_HOSP <= ALL(SELECT count(ID_HOSPEDAJE) FROM reserva WHERE to_char(fecha_inicio - 7/24,'IYYY')=ANIO AND to_char(fecha_inicio - 7/24,'IW')=SEMANA GROUP BY to_char(fecha_inicio - 7/24,'IYYY'), to_char(fecha_inicio - 7/24,'IW'),ID_HOSPEDAJE))T1, VIVIENDA WHERE VIVIENDA.ID_HOSPEDAJE = T1.HOSP";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next()) {
			Funcionamiento fun = new Funcionamiento();
			fun.setAnio(rs.getInt("ANIO"));
			fun.setSemana(rs.getInt("SEMANA"));
			fun.setHospedaje(rs.getInt("ID_MIEMBRO_UNIVERSITARIO"));
			
			resp.add(fun);
		}
		return resp;
	}
}