package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaHospServicio {

	@JsonProperty(value="fechaInicio")
	private String fechaInicio;
	
	@JsonProperty(value="fechaFin")
	private String fechaFin;
	
	@JsonProperty(value="servicio")
	private String servicio;

	public ConsultaHospServicio(@JsonProperty(value="fechaInicio")String fechaInicio, @JsonProperty(value="fechaFin")String fechaFin, @JsonProperty(value="servicio")String servicio) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.servicio = servicio;
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

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	
	
	
}
