package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Rpta10Ordenamiento {

	@JsonProperty(value="id")
	private Integer id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="tipo")
	private String tipo;

		
	public Rpta10Ordenamiento(@JsonProperty(value="id") Integer id,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="tipo") String tipo) {
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}