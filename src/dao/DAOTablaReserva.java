package dao;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ConsultaHospServicio;
import vos.ConsultasPeriodos;
import vos.Hospedaje;
import vos.HospedajeIndicador;
import vos.IngresosPeriodoAlojamiento;
import vos.Reserva;
import vos.UsoPorTipoUsuario;
import vos.UsoPorUsuario;


public class DAOTablaReserva {

	/**
	 * Arraylits de recursos que se usan para la ejecuci√≥n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi√≥n a la base de datos
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
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexi√≥n que entra como parametro.
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
		String inic = Integer.parseInt(ini[1])+"/"+Integer.parseInt(ini[0])+"/"+Integer.parseInt(ini[2]);
		String fin = Integer.parseInt(fini[1])+"/"+Integer.parseInt(fini[0])+"/"+Integer.parseInt(fini[2]);

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
		String[] fin = reserva.getFechaFin().split("-");
		Date inic = new Date(Integer.parseInt(ini[0]),Integer.parseInt(ini[1]), Integer.parseInt(ini[2]));
		Date fini = new Date(Integer.parseInt(fin[0]),Integer.parseInt(fin[1]), Integer.parseInt(fin[2]));
//		String inic = Integer.parseInt(ini[0])+"/"+Integer.parseInt(ini[1])+"/"+Integer.parseInt(ini[2]);
//		String fin = Integer.parseInt(fini[0])+"/"+Integer.parseInt(fini[1])+"/"+Integer.parseInt(fini[2]);
		System.out.println(inic + " abcd " + fin);
		
		String sql = "DELETE FROM RESERVA WHERE ID_CLIENTE = "+idCli+" AND ID_HOSPEDAJE = "+idHosp+" AND FECHA_INICIO = TO_DATE('"+inic+"', 'YYYY/MM/DD') AND FECHA_TERMINACION =TO_DATE('"+fini+"', 'YYYY/MM/DD')";

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

		String sql = "SELECT * FROM RESERVA WHERE ID_CLIENTE = "+idCli+" AND FECHA_INICIO = TO_DATE('"+inic+"', 'YYYY/MM/DD') AND FECHA_TERMINACION =TO_DATE('"+fin+"', 'YYYY/MM/DD')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()) {
			Integer idCliente = rs.getInt("ID_CLIENTE");
			Integer idHospedaje = rs.getInt("ID_HOSPEDAJE");
			String fechaInicio = rs.getString("FECHA_INICIO");
			String fechaFinal = rs.getString("FECHA_TERMINACION");			
			reserva = new Reserva(idCliente, idHospedaje, fechaInicio, fechaFinal, -1, 0);
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
		System.out.println(fechaFin + " hey " + fechaIn);
		
		String sql = "(SELECT O.ID_HOSPEDAJE FROM OFERTAS O INNER JOIN HOSPEDAJE H ON (O.ID_HOSPEDAJE = H.ID) WHERE H.TIPO = 'HabHostal') MINUS (SELECT R.ID_HOSPEDAJE FROM RESERVA R INNER JOIN HOSPEDAJE H1 ON (R.ID_HOSPEDAJE = H1.ID) WHERE H1.TIPO = 'habhostal' AND FECHA_INICIO = TO_DATE('10/04/2018','MM/DD/YYYY') AND FECHA_TERMINACION = TO_DATE('10/04/2018','MM/DD/YYYY'))";
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

	public UsoPorUsuario darUsoDeUsuarioDado(Integer id) throws SQLException, Exception{
		UsoPorUsuario datos = null;
		
		String sql = "SELECT CLIENTE, TIMME/TIEMPO_TOTAL AS USO, TIMME AS NOCHES_USO, DINERO_PAGADO\r\n" + 
				" FROM (SELECT CLIENTE, SUM(TIEMPO) AS TIMME, SYSDATE-TO_DATE('01/01/2017') AS TIEMPO_TOTAL, SUM(INGRESO) AS DINERO_PAGADO\r\n" + 
				" FROM (SELECT RESERVA.ID_CLIENTE AS CLIENTE, RESERVA.FECHA_TERMINACION - RESERVA.FECHA_INICIO AS TIEMPO, INGRESO\r\n" + 
				" FROM RESERVA)\r\n" + 
				" GROUP BY CLIENTE)\r\n" + 
				" WHERE CLIENTE = "+id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Integer cliente = rs.getInt("CLIENTE");
			Double uso = rs.getDouble("USO");
			Integer nochesUso = rs.getInt("NOCHES_USO");
			Integer dinero = rs.getInt("DINERO_PAGADO");
			datos = new UsoPorUsuario(cliente, uso, nochesUso, dinero);
		}
		
		return datos;
	}
	
	public ArrayList<UsoPorTipoUsuario> darUsoPorTipoUsuario() throws SQLException, Exception{
		ArrayList<UsoPorTipoUsuario> uso = new ArrayList<UsoPorTipoUsuario>();

		String sql = "SELECT T2.TIPO, T2.U/T2.CU AS USOTIPO\r\n" + 
				" FROM (SELECT CLIENTE.TIPO, SUM(T1.USO) AS U, COUNT(*) AS CU\r\n" + 
				" FROM (SELECT CLIENTE, TIMME/TIEMPO_TOTAL AS USO\r\n" + 
				" FROM (SELECT CLIENTE, SUM(TIEMPO) AS TIMME, SYSDATE-TO_DATE('01/01/2017') AS TIEMPO_TOTAL\r\n" + 
				" FROM (SELECT RESERVA.ID_CLIENTE AS CLIENTE, RESERVA.FECHA_TERMINACION - RESERVA.FECHA_INICIO AS TIEMPO\r\n" + 
				" FROM RESERVA)\r\n" + 
				" GROUP BY CLIENTE))T1, CLIENTE\r\n" + 
				" WHERE T1.CLIENTE = CLIENTE.ID\r\n" + 
				" GROUP BY CLIENTE.TIPO)T2";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String tipo = rs.getString("TIPO");
			Double usoTipo = rs.getDouble("USOTIPO");
			uso.add(new UsoPorTipoUsuario(tipo, usoTipo));
		}
		return uso;
	}
	
	public ArrayList<Integer> darHospedajesDisponiblesConServicio(ConsultaHospServicio consulta) throws SQLException, Exception{
		ArrayList<Integer> hospedajes = new ArrayList<Integer>();

		String servicio = consulta.getServicio();
		String inicio1 = consulta.getFechaInicio();
		String fin1 = consulta.getFechaFin();
		

		String sql = "SELECT T1.ID_HOSPEDAJE\r\n" + 
				" FROM (SELECT ID_HOSPEDAJE \r\n" + 
				" FROM RESERVA\r\n" + 
				" MINUS \r\n" + 
				" SELECT RESERVA.ID_HOSPEDAJE \r\n" + 
				" FROM RESERVA \r\n" + 
				" WHERE FECHA_INICIO = TO_DATE('"+inicio1+"') AND FECHA_TERMINACION = TO_DATE('"+fin1+"'))T1, SERVICIO\r\n" + 
				" WHERE T1.ID_HOSPEDAJE = SERVICIO.ID_HOSPEDAJE AND SERVICIO.TIPOSERVICIO = '"+servicio+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Integer id = rs.getInt("ID_HOSPEDAJE");
			hospedajes.add(id);
		}
		return hospedajes;
	}
	
	public ArrayList<HospedajeIndicador> darIndiceDeOcupacionPorHospedaje() throws SQLException, Exception{
		ArrayList<HospedajeIndicador> hospedajes = new ArrayList<HospedajeIndicador>();

		String sql = "SELECT HOSPEDAJE, TIMME/TIEMPO_TOTAL AS INDICADOR\r\n" + 
				" FROM (SELECT HOSPEDAJE, SUM(TIEMPO) AS TIMME, SYSDATE-TO_DATE('01/01/2017') AS TIEMPO_TOTAL\r\n" + 
				" FROM (SELECT RESERVA.ID_HOSPEDAJE AS HOSPEDAJE, RESERVA.FECHA_TERMINACION - RESERVA.FECHA_INICIO AS TIEMPO\r\n" + 
				" FROM RESERVA)\r\n" + 
				" GROUP BY HOSPEDAJE)\r\n" + 
				" ORDER BY INDICADOR ASC";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Integer hosp = rs.getInt("HOSPEDAJE");
			Double ind = rs.getDouble("INDICADOR");
			hospedajes.add(new HospedajeIndicador(hosp,ind));
		}
		return hospedajes;
	}
	
	public ArrayList<Integer> darClientesFrecuentes(Integer id) throws SQLException, Exception{
		ArrayList<Integer> frecuentes = new ArrayList<Integer>();

		String sql = "SELECT ID\r\n" + 
				" FROM(SELECT RESERVA.ID_CLIENTE AS ID, COUNT(*) AS CUENTA\r\n" + 
				" FROM RESERVA\r\n" + 
				" WHERE RESERVA.ID_HOSPEDAJE = "+id+" \r\n" + 
				" GROUP BY RESERVA.ID_CLIENTE)\r\n" + 
				" WHERE CUENTA >= 3\r\n" + 
				" UNION\r\n" + 
				" SELECT ID\r\n" + 
				" FROM (SELECT CLIENTE AS ID, SUM(TIEMPO) AS TIME\r\n" + 
				" FROM (SELECT RESERVA.ID_CLIENTE AS CLIENTE, RESERVA.ID_HOSPEDAJE AS RESERVA, RESERVA.FECHA_TERMINACION - RESERVA.FECHA_INICIO AS TIEMPO\r\n" + 
				" FROM RESERVA\r\n" + 
				" WHERE RESERVA.ID_HOSPEDAJE = "+id+")\r\n" + 
				" GROUP BY CLIENTE)\r\n" + 
				" WHERE TIME >= 15";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Integer ids = rs.getInt("ID");
			frecuentes.add(ids);
		}
		return frecuentes;
	}
	
	public ArrayList<ConsultasPeriodos> darSemanasDeMayorDemandaSegunTipoAlojamiento(String tipo) throws SQLException, Exception{
		ArrayList<ConsultasPeriodos> semanas = new ArrayList<ConsultasPeriodos>();

		String sql = "SELECT A—O, SEMANA, RESERVAS_TOTALES\r\n" + 
				" FROM(SELECT to_char(reserva.fecha_inicio - 7/24,'IYYY') as A—O, to_char(reserva.fecha_inicio - 7/24,'IW') as SEMANA,count(reserva.id_cliente) as RESERVAS_TOTALES\r\n" + 
				" FROM reserva, hospedaje\r\n" + 
				" WHERE reserva.id_hospedaje = hospedaje.id AND hospedaje.tipo = '"+tipo+"'\r\n" + 
				" GROUP BY to_char(reserva.fecha_inicio - 7/24,'IYYY'), to_char(reserva.fecha_inicio - 7/24,'IW'))\r\n" + 
				" WHERE RESERVAS_TOTALES >= ALL(SELECT count(reserva.id_cliente) as RESERVAS_TOTALES\r\n" + 
				"                                FROM reserva, hospedaje\r\n" + 
				"                                WHERE reserva.id_hospedaje = hospedaje.id AND hospedaje.tipo = '"+tipo+"'\r\n" + 
				"                                GROUP BY to_char(reserva.fecha_inicio - 7/24,'IYYY'), to_char(reserva.fecha_inicio - 7/24,'IW'))";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String anio = rs.getString("A—O");
			String semana = rs.getString("SEMANA");
			Integer reservas = rs.getInt("RESERVAS_TOTALES");
			semanas.add(new ConsultasPeriodos(anio, semana, reservas));
		}
		return semanas;
	}
	
	public ArrayList<ConsultasPeriodos> darMesesDeMayorDemandaSegunTipoAlojamiento(String tipo) throws SQLException, Exception{
		ArrayList<ConsultasPeriodos> meses = new ArrayList<ConsultasPeriodos>();

		String sql = "SELECT A—O, MES, RESERVAS_TOTALES\r\n" + 
				" FROM(SELECT extract(year from RESERVA.FECHA_INICIO) as A—O, extract(month from RESERVA.FECHA_INICIO) AS MES, COUNT(RESERVA.ID_CLIENTE) AS RESERVAS_TOTALES\r\n" + 
				" FROM RESERVA, HOSPEDAJE\r\n" + 
				" WHERE RESERVA.ID_HOSPEDAJE = HOSPEDAJE.ID\r\n" + 
				"      AND HOSPEDAJE.TIPO = '"+tipo+"'\r\n" + 
				" group by extract(year from FECHA_INICIO),extract(month from FECHA_INICIO))\r\n" + 
				" WHERE RESERVAS_TOTALES >= ALL(SELECT COUNT(RESERVA.ID_CLIENTE) AS RESERVAS_TOTALES\r\n" + 
				"                                FROM RESERVA, HOSPEDAJE\r\n" + 
				"                                WHERE RESERVA.ID_HOSPEDAJE = HOSPEDAJE.ID AND HOSPEDAJE.TIPO = '"+tipo+"'\r\n" + 
				"                                group by extract(year from RESERVA.FECHA_INICIO),extract(month from RESERVA.FECHA_INICIO))\r\n" + 
				"                                ";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String anio = rs.getString("A—O");
			String mes = rs.getString("MES");
			Integer reservas = rs.getInt("RESERVAS_TOTALES");
			meses.add(new ConsultasPeriodos(anio, mes, reservas));
		}
		return meses;
	}
	
	public ArrayList<ConsultasPeriodos> darSemanasDeMenorDemandaSegunTipoAlojamiento(String tipo) throws SQLException, Exception{
		ArrayList<ConsultasPeriodos> semanas = new ArrayList<ConsultasPeriodos>();

		String sql = "SELECT A—O, SEMANA, RESERVAS_TOTALES\r\n" + 
				" FROM(SELECT to_char(reserva.fecha_inicio - 7/24,'IYYY') as A—O, to_char(reserva.fecha_inicio - 7/24,'IW') as SEMANA,count(reserva.id_cliente) as RESERVAS_TOTALES\r\n" + 
				" FROM reserva, hospedaje\r\n" + 
				" WHERE reserva.id_hospedaje = hospedaje.id AND hospedaje.tipo = '"+tipo+"'\r\n" + 
				" GROUP BY to_char(reserva.fecha_inicio - 7/24,'IYYY'), to_char(reserva.fecha_inicio - 7/24,'IW'))\r\n" + 
				" WHERE RESERVAS_TOTALES <= ALL(SELECT count(reserva.id_cliente) as RESERVAS_TOTALES\r\n" + 
				"                                FROM reserva, hospedaje\r\n" + 
				"                                WHERE reserva.id_hospedaje = hospedaje.id AND hospedaje.tipo = '"+tipo+"'\r\n" + 
				"                                GROUP BY to_char(reserva.fecha_inicio - 7/24,'IYYY'), to_char(reserva.fecha_inicio - 7/24,'IW'))";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String anio = rs.getString("A—O");
			String semana = rs.getString("SEMANA");
			Integer reservas = rs.getInt("RESERVAS_TOTALES");
			semanas.add(new ConsultasPeriodos(anio, semana, reservas));
		}
		return semanas;
	}
	
	public ArrayList<ConsultasPeriodos> darMesesDeMenorDemandaSegunTipoAlojamiento(String tipo) throws SQLException, Exception{
		ArrayList<ConsultasPeriodos> meses = new ArrayList<ConsultasPeriodos>();

		String sql = "SELECT A—O, MES, RESERVAS_TOTALES\r\n" + 
				" FROM(SELECT extract(year from RESERVA.FECHA_INICIO) as A—O, extract(month from RESERVA.FECHA_INICIO) AS MES, COUNT(RESERVA.ID_CLIENTE) AS RESERVAS_TOTALES\r\n" + 
				" FROM RESERVA, HOSPEDAJE\r\n" + 
				" WHERE RESERVA.ID_HOSPEDAJE = HOSPEDAJE.ID\r\n" + 
				"      AND HOSPEDAJE.TIPO = '"+tipo+"'\r\n" + 
				" group by extract(year from FECHA_INICIO),extract(month from FECHA_INICIO))\r\n" + 
				" WHERE RESERVAS_TOTALES <= ALL(SELECT COUNT(RESERVA.ID_CLIENTE) AS RESERVAS_TOTALES\r\n" + 
				"                                FROM RESERVA, HOSPEDAJE\r\n" + 
				"                                WHERE RESERVA.ID_HOSPEDAJE = HOSPEDAJE.ID AND HOSPEDAJE.TIPO = '"+tipo+"'\r\n" + 
				"                                group by extract(year from RESERVA.FECHA_INICIO),extract(month from RESERVA.FECHA_INICIO))";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String anio = rs.getString("A—O");
			String mes = rs.getString("MES");
			Integer reservas = rs.getInt("RESERVAS_TOTALES");
			meses.add(new ConsultasPeriodos(anio, mes, reservas));
		}
		return meses;
	}
	
	public ArrayList<ConsultasPeriodos> darSemanasDeMayorIngresoSegunTipoAlojamiento(String tipo) throws SQLException, Exception{
		ArrayList<ConsultasPeriodos> semanas = new ArrayList<ConsultasPeriodos>();

		String sql = "SELECT A—O, SEMANA, INGRESOS_TOTALES\r\n" + 
				" FROM(SELECT to_char(reserva.fecha_inicio - 7/24,'IYYY') as A—O, to_char(reserva.fecha_inicio - 7/24,'IW') as SEMANA, SUM(reserva.ingreso) as INGRESOS_TOTALES\r\n" + 
				" FROM reserva, hospedaje\r\n" + 
				" WHERE reserva.id_hospedaje = hospedaje.id AND hospedaje.tipo = '"+tipo+"'\r\n" + 
				" GROUP BY to_char(reserva.fecha_inicio - 7/24,'IYYY'), to_char(reserva.fecha_inicio - 7/24,'IW'))\r\n" + 
				" WHERE INGRESOS_TOTALES >= ALL(SELECT SUM(reserva.INGRESO) as INGRESOS_TOTALES\r\n" + 
				"                                FROM reserva, hospedaje\r\n" + 
				"                                WHERE reserva.id_hospedaje = hospedaje.id AND hospedaje.tipo = '"+tipo+"'\r\n" + 
				"                                GROUP BY to_char(reserva.fecha_inicio - 7/24,'IYYY'), to_char(reserva.fecha_inicio - 7/24,'IW'))";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String anio = rs.getString("A—O");
			String semana = rs.getString("SEMANA");
			Integer ingresos = rs.getInt("INGRESOS_TOTALES");
			semanas.add(new ConsultasPeriodos(anio, semana, ingresos));
		}
		return semanas;
	}
	
	public ArrayList<ConsultasPeriodos> darMesesDeMayorIngresoSegunTipoAlojamiento(String tipo) throws SQLException, Exception{
		ArrayList<ConsultasPeriodos> meses = new ArrayList<ConsultasPeriodos>();

		String sql = "SELECT A—O, MES, INGRESOS_TOTALES\r\n" + 
				" FROM(SELECT extract(year from RESERVA.FECHA_INICIO) as A—O, extract(month from RESERVA.FECHA_INICIO) AS MES, SUM(RESERVA.INGRESO) AS INGRESOS_TOTALES\r\n" + 
				" FROM RESERVA, HOSPEDAJE\r\n" + 
				" WHERE RESERVA.ID_HOSPEDAJE = HOSPEDAJE.ID\r\n" + 
				"      AND HOSPEDAJE.TIPO = '"+tipo+"'\r\n" + 
				" group by extract(year from FECHA_INICIO),extract(month from FECHA_INICIO))\r\n" + 
				" WHERE INGRESOS_TOTALES >= ALL(SELECT SUM(RESERVA.INGRESO) AS INGRESOS_TOTALES\r\n" + 
				"                                FROM RESERVA, HOSPEDAJE\r\n" + 
				"                                WHERE RESERVA.ID_HOSPEDAJE = HOSPEDAJE.ID AND HOSPEDAJE.TIPO = '"+tipo+"'\r\n" + 
				"                                group by extract(year from RESERVA.FECHA_INICIO),extract(month from RESERVA.FECHA_INICIO))";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String anio = rs.getString("A—O");
			String mes = rs.getString("MES");
			Integer ingresos = rs.getInt("INGRESOS_TOTALES");
			meses.add(new ConsultasPeriodos(anio, mes, ingresos));
		}
		return meses;
	}
	
	public void reacomodarHospedajes(Integer hospedaje) throws Exception{
		ArrayList<Reserva> reacomodables = new ArrayList<Reserva>();
		//String sql = "select * from reserva where id_hospedaje = " + hospedaje;
		
		PreparedStatement prepStmt = conn.prepareStatement("select r.id_cliente, r.id_hospedaje, r.fecha_inicio, r.fecha_terminacion, r.masiva, r.ingreso, h.tipo from reserva r inner join hospedaje h on (h.id = r.id_hospedaje) where id_hospedaje = " + hospedaje);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		
		//las busco
		String tipo = "";
		while(rs.next()){
			reacomodables.add(new Reserva(rs.getInt("ID_CLIENTE"), rs.getInt("ID_HOSPEDAJE"), rs.getDate("FECHA_INICIO").toString(), rs.getDate("FECHA_TERMINACION").toString(), rs.getInt("MASIVA"), rs.getInt("INGRESO")));
			tipo = rs.getString("TIPO");
		}
		
		System.out.println("hay " + reacomodables.size() + " reacomodables");
		
		//Busco donde reacomodarlas
		PreparedStatement prepStmt2 = conn.prepareStatement("select id as i from hospedaje where tipo = '" + tipo+ "' and id !=" + hospedaje);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();
		
		ArrayList<Integer> hospedajes = new ArrayList<Integer>();
		while(rs2.next()){
			hospedajes.add(rs2.getInt("I"));
		}
		
		
		//las reacomodo
		for(int i = 0; i < reacomodables.size(); i++){
			String[] fin = reacomodables.get(i).getFechaFin().split("-");
			Date fini = new Date(Integer.parseInt(fin[0]),Integer.parseInt(fin[1]), Integer.parseInt(fin[2]));
			PreparedStatement prepStmt1 = conn.prepareStatement("insert into reserva(id_hospedaje, id_cliente, fecha_inicio, fecha_terminacion) values(" + hospedajes.get(i)+","+ reacomodables.get(i).getIdCliente()+","+ "SYSDATE"+","+ "TO_DATE('"+ fini+"','YYYY/MM/DD'))");
			recursos.add(prepStmt1);
			ResultSet rs1 = prepStmt1.executeQuery();
		}
		
		
		
		//las elimino
		for(int i = 0; i < reacomodables.size(); i++){
			deleteReserva(reacomodables.get(i));
		}
		
	}
}
