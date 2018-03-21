package vos;

public class Cliente {

	
	private Integer id;
	
	private String nombre;
	
	public Cliente(Integer id, String nom){
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
