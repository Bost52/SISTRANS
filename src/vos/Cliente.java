package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente {

	@JsonProperty(value="id")
	private Integer id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	public Cliente(@JsonProperty(value="id")Integer id,@JsonProperty(value="nombre") String nom){
		this.id = id;
		this.nombre = nom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
