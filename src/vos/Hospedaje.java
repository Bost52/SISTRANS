package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Hospedaje {

	@JsonProperty(value="id")
	private Integer id;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	
	public Hospedaje (@JsonProperty(value="id")Integer id, 	@JsonProperty(value="tipo") String tipo){
		this.id = id;
		this.tipo = tipo;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
