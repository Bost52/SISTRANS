package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AgregarZona {

	@JsonProperty(value="administrador")
	private int cedulaAdministrador;
	
	@JsonProperty(value="zona")
	private Zona zona;
	
	public AgregarZona(@JsonProperty(value="administrador") int cedulaAdministrador, @JsonProperty(value="zona") Zona zona) {
		this.cedulaAdministrador=cedulaAdministrador;
		this.zona = zona;
	}
	
	public Zona getZona() {
		return zona;
	}
	
	public void setZona(Zona zona) {
		this.zona = zona;
	}
	
	public int getCedulaAdministrador() {
		return cedulaAdministrador;
	}
	
	public void setCedulaAdministrador(int cedulaAdministrador) {
		this.cedulaAdministrador = cedulaAdministrador;
	}
	
}
