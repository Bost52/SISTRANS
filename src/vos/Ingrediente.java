package vos;

import java.util.ArrayList;

public class Ingrediente {

	private Long id;
	
	public String nombre;
	
	public String descripcion;
	
	public String descripcionTraducida;
	
	public ArrayList<Ingrediente> ingredientesEquivalentes;

	public Ingrediente(Long id, String nombre, String descripcion, String descripcionTraducida) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.descripcionTraducida = descripcionTraducida;
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
	
	
}
