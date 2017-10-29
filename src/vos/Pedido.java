package vos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Pedido {
	@JsonProperty(value="fechaHora")
	private String fechaHora;
	
	@JsonProperty(value="idPedido")
	private int idPedido;
	
	@JsonProperty(value="idPago")
	private int idPago;
	
	@JsonProperty(value="cliente")
	private Usuario cliente;
	
	@JsonProperty(value="servido")
	private String servido;
	
	@JsonProperty(value="productos")
	private ArrayList<PedidoProducto> productos; 
	
	@JsonProperty(value="idMesa")
	private int idMesa;
	
	@JsonProperty(value="precio")
	private double precio;
	
	
	public Pedido(@JsonProperty(value="productos") ArrayList<PedidoProducto> productos,@JsonProperty(value="fechaHora") String date,@JsonProperty(value="idPedido") int idPedido,@JsonProperty(value="idPago") int idPago, @JsonProperty(value="cliente") Usuario cliente, @JsonProperty(value="servido") String servido) {
		this.fechaHora=date;
		this.idPago=idPago;
		this.idPedido=idPedido;
		this.cliente=cliente;
		this.servido=servido;
		this.productos=productos;
	}
	
	public String getFechaHora() {
		return fechaHora;
	}
	
	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	public int getIdPago() {
		return idPago;
	}
	
	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}
	
	public int getIdPedido() {
		return idPedido;
	}
	
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	
	public Usuario getCliente() {
		return cliente;
	}
	
	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}
	
	public String getServido() {
		return servido;
	}
	
	public void setServido(String servido) {
		this.servido = servido;
	}
	
	public ArrayList<PedidoProducto> getProductos() {
		return productos;
	}
	
	public void setProductos(ArrayList<PedidoProducto> productos) {
		this.productos = productos;
	}

	public int getIdMesa() {
		return idMesa;
	}
	
	public void setIdMesa(int idMesa) {
		this.idMesa = idMesa;
	}

	public double getPrecio() {
		return precio;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
}
