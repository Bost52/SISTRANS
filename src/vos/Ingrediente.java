package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingrediente {

	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="nombre")
	public String nombre;
	
	@JsonProperty(value="descripcionespañol")
	public String descripcion;
	
	@JsonProperty(value="descripcioningles")
	public String descripcionTraducida;
	
	@JsonProperty(value="cantidad")
	private int cantidad;
	
	public ArrayList<Ingrediente> ingredientesEquivalentes;

	public Ingrediente(@JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="descripcionespañol")String descripcion, @JsonProperty(value="descripcioningles") String descripcionTraducida, @JsonProperty(value="cantidad")int cantidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.descripcionTraducida = descripcionTraducida;
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionTraducida() {
		return descripcionTraducida;
	}

	public void setDescripcionTraducida(String descripcionTraducida) {
		this.descripcionTraducida = descripcionTraducida;
	}

	public ArrayList<Ingrediente> getIngredientesEquivalentes() {
		return ingredientesEquivalentes;
	}

	public void setIngredientesEquivalentes(ArrayList<Ingrediente> ingredientesEquivalentes) {
		this.ingredientesEquivalentes = ingredientesEquivalentes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}
