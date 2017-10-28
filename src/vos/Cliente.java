package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente {

	@JsonProperty(value="cedula")
	private long cedula;

	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="email")
	private String email;
	
	@JsonProperty(value="prferencia")
	private ArrayList<Preferencia> preferencias;
	
	@JsonProperty(value="pedidos")
	private ArrayList<Pedido> pedidos;
	
	@JsonProperty(value="fercuencia")
	private double frecuencia;
	
	public Cliente(@JsonProperty(value="fercuencia") double frecuencia,@JsonProperty(value="pedidos") ArrayList<Pedido> pedidos,@JsonProperty(value="prferencia") ArrayList<Preferencia> preferencias,@JsonProperty(value="email") String email,@JsonProperty(value="cedula") long cedula, @JsonProperty(value="nombre") String nombre) {
		this.cedula=cedula;
		this.nombre=nombre;
		this.email=email;
		this.preferencias=preferencias;
		this.pedidos=pedidos;
		this.frecuencia=frecuencia;
	}
	
	public Cliente() {
	}

	public ArrayList<Preferencia> getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(ArrayList<Preferencia> preferencias) {
		this.preferencias = preferencias;
	}

	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public double getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(double frecuencia) {
		this.frecuencia = frecuencia;
	}

	public void setCedula(long cedula) {
		this.cedula = cedula;
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
	
}
