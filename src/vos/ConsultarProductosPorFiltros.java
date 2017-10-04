package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.ProductoSingular.TipoProductoSigular;

public class ConsultarProductosPorFiltros {

	@JsonProperty(value="restaurante")
	private Restaurante restaurante;
	
	@JsonProperty(value="precioMaximo")
	private double precioMayor;
	
	@JsonProperty(value="precioMinimo")
	private double precioMenor;
	
	@JsonProperty(value="categoria")
	private TipoProductoSigular categoria;
	
	public ConsultarProductosPorFiltros(@JsonProperty(value="restaurante") Restaurante restaurante, @JsonProperty(value="precioMax") double precioMax,
			@JsonProperty(value="precioMin") double precioMin, 
			@JsonProperty(value="categoria") TipoProductoSigular categoria) {
		
		this.restaurante = restaurante;
		this.precioMayor = precioMax;
		this.precioMenor = precioMin;
		this.categoria = categoria;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public double getPrecioMayor() {
		return precioMayor;
	}

	public void setPrecioMayor(double precioMayor) {
		this.precioMayor = precioMayor;
	}

	public double getPrecioMenor() {
		return precioMenor;
	}

	public void setPrecioMenor(double precioMenor) {
		this.precioMenor = precioMenor;
	}

	public TipoProductoSigular getCategoria() {
		return categoria;
	}

	public void setCategoria(TipoProductoSigular categoria) {
		this.categoria = categoria;
	}
	
	
}
