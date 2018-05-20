package vos;

import java.sql.Date;

//import java.util.Date;

public class Reserva {

	private Integer idCliente;
	
	private Integer idHospedaje;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	private int masiva;
	
	private int ingreso;

	public Reserva(Integer cli, Integer hosp, String inic, String fin, int masi, int ingre) {
		super();
		idCliente = cli;
		idHospedaje = hosp;
		fechaInicio = inic;
		fechaFin = fin;
		this.masiva = masi;
		this.ingreso = ingre;
	}

	public int getMasiva() {
		return masiva;
	}

	public void setMasiva(int masiva) {
		this.masiva = masiva;
	}

	public int getIngreso() {
		return ingreso;
	}

	public void setIngreso(int ingreso) {
		this.ingreso = ingreso;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdHospedaje() {
		return idHospedaje;
	}

	public void setIdHospedaje(Integer idHospedaje) {
		this.idHospedaje = idHospedaje;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
}
