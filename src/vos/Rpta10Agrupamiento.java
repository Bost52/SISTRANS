package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Rpta10Agrupamiento {

	@JsonProperty(value="criterio")
	private Double criterio;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	

	public Rpta10Agrupamiento(Double criterio, String tipo) {
		this.criterio = criterio;
		this.tipo = tipo;
	}

	public Double getCriterio() {
		return criterio;
	}

	public void setCriterio(Double criterio) {
		this.criterio = criterio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
