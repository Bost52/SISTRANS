package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class AnioPresenteYCorrido {
	
	@JsonProperty(value="fechaInicio")
	private Date fechaInicio;
	
	@JsonProperty(value="fechaFinal")
	private Date fechaFinal;
	
	
	public AnioPresenteYCorrido(@JsonProperty(value="fechaInicio")Date fechaInicio, @JsonProperty(value="fechaFinal")Date fechaFinal) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFinal = fechaFinal;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
}
