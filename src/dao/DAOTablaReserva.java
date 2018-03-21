package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Hospedaje;

import vos.Reserva;

public class DAOTablaReserva {

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
	public DAOTablaReserva() {
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


	public void addReserva(Reserva reserva) throws SQLException, Exception {
		Integer idCli = reserva.getIdCliente();
		Integer idHosp = reserva.getIdHospedaje();
		Date inic = reserva.getFechaInicio();
		Date fin = reserva.getFechaFin();

		String sql = "insert into RESERVA (ID_CLIENTE, ID_HOSPEDAJE, FECHA_INICIO, FECHA_FINAL) values ("+ idCli+ ", "+ idHosp+", "+ inic + ", " + fin+")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void deleteReserva(Reserva reserva) throws SQLException, Exception {
		Integer idCli = reserva.getIdCliente();
		Integer idHosp = reserva.getIdHospedaje();
		Date inic = reserva.getFechaInicio();
		Date fin = reserva.getFechaFin();

		String sql = "DELETE FROM RESERVA WHERE ID_CLIENTE = "+idCli+" AND ID_HOSPEDAJE = "+idHosp+" AND FECHA_INICIO = "+inic+" AND FECHA_FINAL = "+fin;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public Reserva buscarReserva(Reserva reserva) throws SQLException, Exception {
		Integer idCli = reserva.getIdCliente();
		Integer idHosp = reserva.getIdHospedaje();
		Date inic = reserva.getFechaInicio();
		Date fin = reserva.getFechaFin();

		String sql = "SELECT * FROM RESERVA WHERE ID_CLIENTE = "+idCli+" AND ID_HOSPEDAJE = "+idHosp+" AND FECHA_INICIO = "+inic+" AND FECHA_FINAL = "+fin;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()) {
			Integer idCliente = rs.getInt("ID_CLIENTE");
			Integer idHospedaje = rs.getInt("ID_HOSPEDAJE");
			Date fechaInicio = rs.getDate("FECHA_INICIO");
			Date fechaFinal = rs.getDate("FECHA_TERMINACION");			
			reserva = new Reserva(idCliente, idHospedaje, fechaInicio, fechaFinal);
		}

		return reserva;
	}


	public Hospedaje[] darVeinteHospedajesPopulares() throws SQLException{
		Hospedaje[] resp = new Hospedaje[20];

		String sql = "SELECT * FROM (SELECT R.ID_HOSPEDAJE, COUNT(*) FROM RESERVA R GROUP BY R.ID_HOSPEDAJE ORDER BY COUNT(*) DESC) WHERE ROWNUM <=20;";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Integer id = rs.getInt("ID");
			String tipo = rs.getString("TIPO");
			
			resp[i] = new Hospedaje(id, tipo);
			i++;		

		}
		return resp;
	}
}
