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


	public void addReserva(Reserva reserva, int masiva) throws SQLException, Exception {
		Integer idCli = reserva.getIdCliente();
		Integer idHosp = reserva.getIdHospedaje();
		String[] ini = reserva.getFechaInicio().split("/");
		String[] fini = reserva.getFechaFin().split("/");
		String inic = Integer.parseInt(ini[0])+"/"+Integer.parseInt(ini[1])+"/"+Integer.parseInt(ini[2]);
		String fin = Integer.parseInt(fini[0])+"/"+Integer.parseInt(fini[1])+"/"+Integer.parseInt(fini[2]);

		String sql = "";
		if(masiva == -1){
			sql = "insert into RESERVA (ID_CLIENTE, ID_HOSPEDAJE, FECHA_INICIO, FECHA_TERMINACION) values ("+ idCli+ ", "+ idHosp+", "+"TO_DATE('"+inic+"', 'MM/DD/YYYY'),  "+ "  TO_DATE('"+fin+"', 'MM/DD/YYYY'))";
		}
		else{
			sql = "insert into RESERVA (ID_CLIENTE, ID_HOSPEDAJE, FECHA_INICIO, FECHA_TERMINACION, MASIVA) values ("+ idCli+ ", "+ idHosp+", "+"TO_DATE('"+inic+"', 'MM/DD/YYYY'),  "+ "  TO_DATE('"+fin+"', 'MM/DD/YYYY'), " + masiva + ")";
		}
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public void deleteReserva(Reserva reserva) throws SQLException, Exception {
		Integer idCli = reserva.getIdCliente();
		Integer idHosp = reserva.getIdHospedaje();
		String[] ini = reserva.getFechaInicio().split("-");
		String[] fini = reserva.getFechaFin().split("-");
		String inic = Integer.parseInt(ini[0])+"/"+Integer.parseInt(ini[1])+"/"+Integer.parseInt(ini[2]);
		String fin = Integer.parseInt(fini[0])+"/"+Integer.parseInt(fini[1])+"/"+Integer.parseInt(fini[2]);
		String sql = "DELETE FROM RESERVA WHERE ID_CLIENTE = "+idCli+" AND ID_HOSPEDAJE = "+idHosp+" AND FECHA_INICIO = TO_DATE('"+inic+"', 'MM/DD/YYYY') AND FECHA_TERMINACION =TO_DATE('"+fin+"', 'MM/DD/YYYY')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public Reserva buscarReserva(Reserva reserva) throws SQLException, Exception {
		Integer idCli = reserva.getIdCliente();
		Integer idHosp = reserva.getIdHospedaje();
		String[] ini = reserva.getFechaInicio().split("-");
		String[] fini = reserva.getFechaFin().split("-");
		Date inic = new Date(Integer.parseInt(ini[0]),Integer.parseInt(ini[1]),Integer.parseInt(ini[2]));
		Date fin = new Date(Integer.parseInt(fini[0]),Integer.parseInt(fini[1]),Integer.parseInt(fini[2]));

		String sql = "SELECT * FROM RESERVA WHERE ID_CLIENTE = "+idCli+" AND FECHA_INICIO = TO_DATE('"+inic+"', 'MM/DD/YYYY') AND FECHA_TERMINACION =TO_DATE('"+fin+"', 'MM/DD/YYYY')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()) {
			Integer idCliente = rs.getInt("ID_CLIENTE");
			Integer idHospedaje = rs.getInt("ID_HOSPEDAJE");
			String fechaInicio = rs.getString("FECHA_INICIO");
			String fechaFinal = rs.getString("FECHA_TERMINACION");			
			reserva = new Reserva(idCliente, idHospedaje, fechaInicio, fechaFinal);
		}

		return reserva;
	}

	public Integer[] darVeinteHospedajesPopulares() throws SQLException{
		Integer[] resp = new Integer[20];

		String sql = "SELECT * FROM (SELECT R.ID_HOSPEDAJE, COUNT(*) FROM RESERVA R GROUP BY R.ID_HOSPEDAJE ORDER BY COUNT(*) DESC) WHERE ROWNUM <=20";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Integer id = rs.getInt("ID_HOSPEDAJE");
			resp[i] = id;
			i++;		

		}
		return resp;
	}

	public void addReservaMasiva(int id) throws SQLException, Exception {
		String sql = "insert into RESERVAMASIVA (ID) VALUES (" + id + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


	public ArrayList<Integer> verificarMasivas(String tipo, String fechaIn, String fechaFin) throws SQLException, Exception{
		ArrayList<Integer> resp = new ArrayList<Integer>();

		String[] ini = fechaIn.split("/");
		String[] fini = fechaFin.split("/");
		Date inic = new Date(Integer.parseInt(ini[0]),Integer.parseInt(ini[1]),Integer.parseInt(ini[2]));
		Date fin = new Date(Integer.parseInt(fini[0]),Integer.parseInt(fini[1]),Integer.parseInt(fini[2]));
		System.out.println(Integer.parseInt(ini[0]));

		String sql = "(SELECT O.ID_HOSPEDAJE FROM OFERTAS O INNER JOIN HOSPEDAJE H ON (O.ID_HOSPEDAJE = H.ID) WHERE H.TIPO = '"+ tipo+"') MINUS (SELECT R.ID_HOSPEDAJE FROM RESERVA R INNER JOIN HOSPEDAJE H1 ON (R.ID_HOSPEDAJE = H1.ID) WHERE H1.TIPO = '" + tipo +"' AND FECHA_INICIO = TO_DATE('" + fechaIn+"') AND FECHA_TERMINACION =TO_DATE('"+fechaFin+"'))";

		//		String sql = "(SELECT O.ID_HOSPEDAJE FROM OFERTAS O INNER JOIN HOSPEDAJE H ON (O.ID_HOSPEDAJE = H.ID) WHERE H.TIPO = '" + tipo + "') MINUS (SELECT R.ID_HOSPEDAJE FROM RESERVA R INNER JOIN HOSPEDAJE H1 ON (R.ID_HOSPEDAJE = H1.ID) WHERE H1.TIPO = '" + tipo
		//				+ "' AND FECHA_INICIO = TO_DATE('"+Integer.parseInt(ini[0]) + "/" + Integer.parseInt(ini[1]) + "/" + Integer.parseInt(ini[2])+"', 'MM/DD/YYYY') AND FECHA_TERMINACION =TO_DATE('"+ Integer.parseInt(fini[0]) + "/" + Integer.parseInt(fini[0])+ "/" + Integer.parseInt(fini[2])+"', 'MM/DD/YYYY'))";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()) {	
			resp.add(rs.getInt("ID_HOSPEDAJE"));
			System.out.println(rs.getInt("ID_HOSPEDAJE") +"hosp");
		}
		return resp;
	}

	public void cancelarSingularesMasiva(int idMasiva) throws SQLException{
		String sql = "DELETE FROM RESERVA WHERE MASIVA = " + idMasiva;
		String sql1 = "DELETE FROM RESERVAMASIVA WHERE ID = " + idMasiva;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt);
		prepStmt1.executeQuery();
	}


	public void cancelarMasiva(int idMasiva) throws SQLException{
		String sql = "DELETE FROM RESERVAMASIVA WHERE ID = " + idMasiva;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
