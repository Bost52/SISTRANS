package vos;

import java.text.SimpleDateFormat;
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
	
	
	public Pedido(@JsonProperty(value="fechaHora") String date,@JsonProperty(value="idPedido") int idPedido,@JsonProperty(value="idPago") int idPago, @JsonProperty(value="cliente") Usuario cliente) {
		this.fechaHora=date;
		this.idPago=idPago;
		this.idPedido=idPedido;
		this.cliente=cliente;
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
}
