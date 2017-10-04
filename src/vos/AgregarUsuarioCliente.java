package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AgregarUsuarioCliente {

	@JsonProperty(value="admistrador")
	private int cedulaAdministrador;
	
	@JsonProperty(value="usuario")
	private Usuario usuario;
	
	public AgregarUsuarioCliente(@JsonProperty(value="admistrador") int cedulaAdministrador, @JsonProperty(value="usuario") Usuario usuario) {
		this.cedulaAdministrador=cedulaAdministrador;
		this.usuario=usuario;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public int getCedulaAdministrador() {
		return cedulaAdministrador;
	}
	
	public void setCedulaAdministrador(int cedulaAdministrador) {
		this.cedulaAdministrador = cedulaAdministrador;
	}
	
	
	
}
