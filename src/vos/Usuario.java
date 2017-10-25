package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {

	@JsonProperty(value="cedula")
	private long cedula;

	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="email")
	private String email;
	
	@JsonProperty(value="rol")
	private String rol;
	
	public Usuario(@JsonProperty(value="cedula") long cedula,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="email") String email,@JsonProperty(value="rol") String rol) {
		
		this.cedula=cedula;
		this.email=email;
		this.nombre=nombre;
		this.rol=rol;
	}
	
	public long getCedula() {
		return cedula;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setCedula(int cedula) {
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
}
