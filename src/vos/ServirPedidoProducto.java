package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServirPedidoProducto {

	@JsonProperty(value="idPedido")
	private int idPedido;
	
	@JsonProperty(value="idProducto")
	private int idProducto;
	
	@JsonProperty(value="idMenu")
	private int idMenu;
	
	public ServirPedidoProducto(@JsonProperty(value="idPedido") int idPedido, @JsonProperty(value="idProducto") int idProducto, @JsonProperty(value="idMenu") int idMenu) {
		this.idMenu=idMenu;
		this.idPedido=idPedido;
		this.idProducto=idProducto;
	}
	
	public int getIdMenu() {
		return idMenu;
	}
	
	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}
	
	public int getIdPedido() {
		return idPedido;
	}
	
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	
	public int getIdProducto() {
		return idProducto;
	}
	
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	
}
