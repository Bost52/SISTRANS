package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AgregarEquivalenciaProducto {
	@JsonProperty(value="usuarioRestaurante")
	private int cedulaUsuarioRestaurante;
	
	@JsonProperty(value="local")
	private int local;
	
	@JsonProperty(value="idProducto")
	private int idProducto;
	
	public AgregarEquivalenciaProducto (@JsonProperty(value="local") int local,@JsonProperty(value="usuarioRestaurante") int cedulaUsuarioRestaurante, @JsonProperty(value="idProducto") int idProducto) {
		this.cedulaUsuarioRestaurante = cedulaUsuarioRestaurante;
		this.idProducto = idProducto;
		this.local=local;
	}
	public int getIdProducto() {
		return idProducto;
	}
	
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
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
