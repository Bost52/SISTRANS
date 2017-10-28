package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AgregarEquivalenciaIngrediente {
	
	@JsonProperty(value="usuarioRestaurante")
	private int cedulaUsuarioRestaurante;
	
	@JsonProperty(value="local")
	private int local;
	
	@JsonProperty(value="idIngrediente")
	private int idIngrediente;
	
	public AgregarEquivalenciaIngrediente (@JsonProperty(value="local") int local,@JsonProperty(value="usuarioRestaurante") int cedulaUsuarioRestaurante, @JsonProperty(value="idIngrediente") int idIngrediente) {
		this.cedulaUsuarioRestaurante = cedulaUsuarioRestaurante;
		this.idIngrediente = idIngrediente;
		this.local=local;
	}
	public int getIdIngrediente() {
		return idIngrediente;
	}
	
	public void setIdIngrediente(int idIngrediente) {
		this.idIngrediente = idIngrediente;
	}
	
	public int getCedulaUsuarioRestaurante() {
		return cedulaUsuarioRestaurante;
	}
	
	public void setCedulaUsuarioRestaurante(int cedulaUsuarioRestaurante) {
		this.cedulaUsuarioRestaurante = cedulaUsuarioRestaurante;
	}
	
	public int getLocal() {
		return local;
	}
	
	public void setLocal(int local) {
		this.local = local;
	}
}
