package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class HospedajeIndicador {

	
	
	private Integer hospedaje;
	
	
	private Double indicador;

	public HospedajeIndicador(Integer hospedaje, Double indicador) {
		this.hospedaje = hospedaje;
		this.indicador = indicador;
	}

	public Integer getHospedaje() {
		return hospedaje;
	}

	public void setHospedaje(Integer hospedaje) {
		this.hospedaje = hospedaje;
	}

	public Double getIndicador() {
		return indicador;
	}

	public void setIndicador(Double indicador) {
		this.indicador = indicador;
	}
	
	
}
