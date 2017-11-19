package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsumoCliente {
	
	@JsonProperty(value="cedula")
	private long cedula;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="rol")
	private String rol;
	
	@JsonProperty(value="email")
	private String email;
	
	public ConsumoCliente (@JsonProperty(value="email") String email, @JsonProperty(value="rol") String rol,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="cedula") long cedula) {
		this.cedula = cedula;
		this.nombre=nombre;
		this.email=email;
		this.rol=rol;
	}
	
	public ConsumoCliente() {
		// TODO Auto-generated constructor stub
	}

	public long getCedula() {
		return cedula;
	}
	
	public void setCedula(long cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
