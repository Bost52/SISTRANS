package vos;

import java.sql.Date;

//import java.util.Date;

public class Reserva {

	private Integer idCliente;
	
	private Integer idHospedaje;
	
	private String fechaInicio;
	
	private String fechaFin;

	public Reserva(Integer cli, Integer hosp, String inic, String fin) {
		super();
		idCliente = cli;
		idHospedaje = hosp;
		fechaInicio = inic;
		fechaFin = fin;
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
