package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Pedido {

	@JsonProperty(value="fechaHora")
	private Date fechaHora;
	
	@JsonProperty(value="idPedido")
	private int idPedido;
	
	@JsonProperty(value="idPago")
	private int idPago;
	
	@JsonProperty(value="cliente")
	private Usuario cliente;
	
	public Pedido(@JsonProperty(value="fechaHora") Date fechaHora,@JsonProperty(value="idPedido") int idPedido,@JsonProperty(value="idPago") int idPago, @JsonProperty(value="cliente") Usuario cliente) {
		this.fechaHora=fechaHora;
		this.idPago=idPago;
		this.idPedido=idPedido;
		this.cliente=cliente;
	}
	
	public Date getFechaHora() {
		return fechaHora;
	}
	
	public void setFechaHora(Date fechaHora) {
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
}
