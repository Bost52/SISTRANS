package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class DatosTopPorTipoAlojamiento {

	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="tipoConsulta")
	private Integer tipoConsulta;

	public DatosTopPorTipoAlojamiento(@JsonProperty(value="tipo") String tipo, @JsonProperty(value="tipoConsulta") Integer tipoConsulta) {
		this.tipo = tipo;
		this.tipoConsulta = tipoConsulta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(Integer tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}
	
	
}
