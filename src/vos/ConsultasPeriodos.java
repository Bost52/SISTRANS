package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultasPeriodos {

//	@JsonProperty(value="anio")
	private String anio;
	
//	@JsonProperty(value="periodo")
	private String periodo;
	
//	@JsonProperty(value="reservas")
	private Integer reservas;

	public ConsultasPeriodos(String anio, String periodo, Integer reservas) {
		this.anio = anio;
		this.periodo = periodo;
		this.reservas = reservas;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Integer getReservas() {
		return reservas;
	}

	public void setReservas(Integer reservas) {
		this.reservas = reservas;
	}
	
	
}
