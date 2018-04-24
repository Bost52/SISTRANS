package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class UsoPorTipoUsuario {

	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="usoTipo")
	private Double usoTipo;

	public UsoPorTipoUsuario(@JsonProperty(value="tipo") String tipo, @JsonProperty(value="usoTipo") Double usoTipo) {
		this.tipo = tipo;
		this.usoTipo = usoTipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getUsoTipo() {
		return usoTipo;
	}

	public void setUsoTipo(Double usoTipo) {
		this.usoTipo = usoTipo;
	}
	
		
}
