package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Preferencia {

	@JsonProperty(value="idPreferencia")
	private int idPreferencia;
	
	@JsonProperty(value="cedula")
	private int cedula;
	
	@JsonProperty(value="idZona")
	private int idZona;
	
	@JsonProperty(value="idCategoria")
	private int idCategoria;
	
	@JsonProperty(value="maximo")
	private double maximo;
	
	@JsonProperty(value="minimo")
	private double minimo;
	
	public Preferencia(@JsonProperty(value="idPreferencia") int idPreferencia,@JsonProperty(value="cedula") int cedula,@JsonProperty(value="idZona") int idZona,@JsonProperty(value="idCategoria") int idCategoria,@JsonProperty(value="maximo") double maximo,@JsonProperty(value="minimo") double minimo) {
		this.cedula=cedula;
		this.idCategoria=idCategoria;
		this.idPreferencia=idPreferencia;
		this.idZona=idZona;
		this.maximo=maximo;
		this.minimo=minimo;
	}
	
	public int getCedula() {
		return cedula;
	}
	
	public void setCedula(int cedula) {
		this.cedula = cedula;
	}
	
	public int getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public int getIdPreferencia() {
		return idPreferencia;
	}
	
	public void setIdPreferencia(int idPreferencia) {
		this.idPreferencia = idPreferencia;
	}
	
	public int getIdZona() {
		return idZona;
	}
	
	public void setIdZona(int idZona) {
		this.idZona = idZona;
	}
	
	public double getMaximo() {
		return maximo;
	}
	
	public void setMaximo(double maximo) {
		this.maximo = maximo;
	}
	
	public double getMinimo() {
		return minimo;
	}
	
	public void setMinimo(double minimo) {
		this.minimo = minimo;
	}
}
