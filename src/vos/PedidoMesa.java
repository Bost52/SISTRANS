package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoMesa {

	@JsonProperty(value="idMesa")
	private int idMesa;
	
	@JsonProperty(value="pedidos")
	private ArrayList<Pedido> pedidos;
	
	public PedidoMesa(@JsonProperty(value="idMesa") int idMesa,@JsonProperty(value="pedidos") ArrayList<Pedido> pedidos) {
		this.idMesa=idMesa;
		this.pedidos=pedidos;
	}

	public int getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(int idMesa) {
		this.idMesa = idMesa;
	}

	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	
}
