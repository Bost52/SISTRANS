package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class SurtirRestaurante {
	@JsonProperty(value="usuarioRestaurante")
	private long cedulaUsuarioRestaurante;
	
	@JsonProperty(value="local")
	private int local;
	
	@JsonProperty(value="producto")
	private int producto;
	
	public SurtirRestaurante(@JsonProperty(value="usuarioRestaurante") long cedulaUsuarioRestaurante, @JsonProperty(value="producto") int producto, @JsonProperty(value="local") int local) {
		this.cedulaUsuarioRestaurante=cedulaUsuarioRestaurante;
		this.producto=producto;
		this.local=local;
	}
	
	public long getCedulaUsuarioRestaurante() {
		return cedulaUsuarioRestaurante;
	}
	
	public void setCedulaUsuarioRestaurante(long cedulaUsuarioRestaurante) {
		this.cedulaUsuarioRestaurante = cedulaUsuarioRestaurante;
	}
	
	public int getProducto() {
		return producto;
	}
	
	public void setProducto(int producto) {
		this.producto = producto;
	}

	public int getLocal() {
		return local;
	}
	
	public void setLocal(int local) {
		this.local = local;
	}
}
