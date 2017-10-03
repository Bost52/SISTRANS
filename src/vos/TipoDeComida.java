package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class TipoDeComida {

	@JsonProperty(value="idTipo")
	private int idTipo;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	public TipoDeComida(@JsonProperty(value="idTipo") int idTipo,	@JsonProperty(value="tipo") String tipo)
	{
		this.idTipo=idTipo;
		this.tipo=tipo;
	}
	
	public int getIdTipo() {
		return idTipo;
	}
	
	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
