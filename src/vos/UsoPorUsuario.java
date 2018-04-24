package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class UsoPorUsuario {

	@JsonProperty(value="cliente")
	private Integer cliente;
	
	@JsonProperty(value="uso")
	private Double uso;
	
	@JsonProperty(value="nochesUso")
	private Integer nochesUso;
	
	@JsonProperty(value="dineroPagado")
	private Integer dineroPagado;
	
	public UsoPorUsuario(@JsonProperty(value="cliente")Integer cliente, @JsonProperty(value="uso") Double uso, @JsonProperty(value="nochesUso") Integer nochesUso, @JsonProperty(value="dineroPagado") Integer dineroPagado){
		this.cliente = cliente;
		this.uso = uso;
		this.nochesUso = nochesUso;
		this.dineroPagado = dineroPagado;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public Double getUso() {
		return uso;
	}

	public void setUso(Double uso) {
		this.uso = uso;
	}

	public Integer getNochesUso() {
		return nochesUso;
	}

	public void setNochesUso(Integer nochesUso) {
		this.nochesUso = nochesUso;
	}

	public Integer getDineroPagado() {
		return dineroPagado;
	}

	public void setDineroPagado(Integer dineroPagado) {
		this.dineroPagado = dineroPagado;
	}
	
	
}
