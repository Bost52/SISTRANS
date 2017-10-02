package vos;

import java.util.ArrayList;

public class Ingrediente {

	public String nombre;
	
	public String descripcion;
	
	public String descripcionTraducida;
	
	public ArrayList<Ingrediente> ingredientesEquivalentes;

	public Ingrediente(String nombre, String descripcion, String descripcionTraducida,
			ArrayList<Ingrediente> ingredientesEquivalentes) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.descripcionTraducida = descripcionTraducida;
		this.ingredientesEquivalentes = ingredientesEquivalentes;
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
	
	
}
