package vos;

import java.sql.Date;

//import java.util.Date;

public class Reserva {

	private Integer idCliente;
	
	private Integer idHospedaje;
	
	private Date fechaInicio;
	
	private Date fechaFin;

	public Reserva(Integer cli, Integer hosp, Date inic, Date fin) {
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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	
	
}
