package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultarClientes {

	@JsonProperty(value="admistrador")
	private int cedulaAdministrador;
	
	public ConsultarClientes(@JsonProperty(value="admistrador") int cedulaAdministrador) {
		this.cedulaAdministrador=cedulaAdministrador;
	}
	
	public int getCedulaAdministrador() {
		return cedulaAdministrador;
	}
	
	public void setCedulaAdministrador(int cedulaAdministrador) {
		this.cedulaAdministrador = cedulaAdministrador;
	}
}
