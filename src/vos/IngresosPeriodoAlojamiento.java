package vos;

public class IngresosPeriodoAlojamiento {

//	@JsonProperty(value="anio")
	private String anio;
	
//	@JsonProperty(value="periodo")
	private String periodo;
	
//	@JsonProperty(value="ingresos")
	private Integer ingresos;

	public IngresosPeriodoAlojamiento(String anio, String periodo, Integer ingresos) {
		super();
		this.anio = anio;
		this.periodo = periodo;
		this.ingresos = ingresos;
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

	public Integer getIngresos() {
		return ingresos;
	}

	public void setIngresos(Integer ingresos) {
		this.ingresos = ingresos;
	}

	
}
