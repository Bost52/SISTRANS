package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoProducto {

	@JsonProperty(value="local")
	private int local;
	
	@JsonProperty(value="producto")
	private int idProducto;
	
	@JsonProperty(value="menu")
	private int idMenu;
	
	@JsonProperty(value="pedido")
	private int idPedido;

	public PedidoProducto(@JsonProperty(value="producto") int idProducto,@JsonProperty(value="menu") int idMenu,@JsonProperty(value="pedido") int idPedido,@JsonProperty(value="local") int local) {

		this.idMenu=idMenu;
		this.local=local;
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
	
	public int getLocal() {
		return local;
	}
	
	public void setLocal(int local) {
		this.local = local;
	}
	
}
