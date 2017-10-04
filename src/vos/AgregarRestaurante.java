package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AgregarRestaurante {
	
	@JsonProperty(value="admistrador")
	private int cedulaAdministrador;
	
	@JsonProperty(value="restaurante")
	private Restaurante restaurante;
	
	public AgregarRestaurante(@JsonProperty(value="admistrador") int cedulaAdministrador, 	@JsonProperty(value="restaurante") Restaurante restaurante) {
		this.cedulaAdministrador=cedulaAdministrador;
		this.restaurante=restaurante;
	}
	
	public int getCedulaAdministrador() {
		return cedulaAdministrador;
	}
	
	public void setCedulaAdministrador(int cedulaAdministrador) {
		this.cedulaAdministrador = cedulaAdministrador;
	}
	
	public Restaurante getRestaurante() {
		return restaurante;
	}
	
	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
}
