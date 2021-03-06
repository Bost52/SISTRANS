package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Agrupamiento10 {
	
	@JsonProperty(value="fechaInicioInferior")
	private String fechaInicioInferior;

	@JsonProperty(value="fechaInicioSuperior")
	private String fechaInicioSuperior;

	@JsonProperty(value="fechaFinInferior")
	private String fechaFinInferior;

	@JsonProperty(value="fechaFinSuperior")
	private String fechaFinSuperior;

	@JsonProperty(value="idHospedaje")
	private int idHospedaje;

	@JsonProperty(value="ordenamiento")
	private String ordenamiento;

	@JsonProperty(value="criterioAgrupamiento")
	private String criterioAgrupamiento;	

	public Agrupamiento10(@JsonProperty(value="fechaInicioInferior") String fechaInicioInferior,@JsonProperty(value="fechaInicioSuperior") String fechaInicioSuperior,@JsonProperty(value="fechaFinInferior") String fechaFinInferior,
			@JsonProperty(value="fechaFinSuperior") String fechaFinSuperior,@JsonProperty(value="idHospedaje") int idHospedaje,@JsonProperty(value="ordenamiento") String ordenamiento,@JsonProperty(value="criterioAgrupamiento") String criterioAgrupamiento) {
		this.fechaInicioInferior = fechaInicioInferior;
		this.fechaInicioSuperior = fechaInicioSuperior;
		this.fechaFinInferior = fechaFinInferior;
		this.fechaFinSuperior = fechaFinSuperior;
		this.idHospedaje = idHospedaje;
		this.ordenamiento = ordenamiento;
	}
	
	public String getCriterioAgrupamiento() {
		return criterioAgrupamiento;
	}



	public void setCriterioAgrupamiento(String criterioAgrupamiento) {
		this.criterioAgrupamiento = criterioAgrupamiento;
	}



	public String getOrdenamiento() {
		return ordenamiento;
	}

	public void setOrdenamiento(String ordenamiento) {
		this.ordenamiento = ordenamiento;
	}

	public String getFechaInicioInferior() {
		return fechaInicioInferior;
	}

	public void setFechaInicioInferior(String fechaInicioInferior) {
		this.fechaInicioInferior = fechaInicioInferior;
	}

	public String getFechaInicioSuperior() {
		return fechaInicioSuperior;
	}

	public void setFechaInicioSuperior(String fechaInicioSuperior) {
		this.fechaInicioSuperior = fechaInicioSuperior;
	}

	public String getFechaFinInferior() {
		return fechaFinInferior;
	}

	public void setFechaFinInferior(String fechaFinInferior) {
		this.fechaFinInferior = fechaFinInferior;
	}

	public String getFechaFinSuperior() {
		return fechaFinSuperior;
	}

	public void setFechaFinSuperior(String fechaFinuperior) {
		this.fechaFinSuperior = fechaFinuperior;
	}

	public int getIdHospedaje() {
		return idHospedaje;
	}

	public void setIdHospedaje(int idHospedaje) {
		this.idHospedaje = idHospedaje;
	}

}
