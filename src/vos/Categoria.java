package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Categoria {

	@JsonProperty(value="idCategoria")
	private int idCategoria;
	
	@JsonProperty(value="categoria")
	private String categoria;
	
	public Categoria(@JsonProperty(value="idCategoria") int idCategoria, @JsonProperty(value="categoria") String categoria) {
		this.categoria=categoria;
		this.idCategoria=idCategoria;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public int getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
}
