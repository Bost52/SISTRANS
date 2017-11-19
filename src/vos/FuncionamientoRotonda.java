package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class FuncionamientoRotonda {

	@JsonProperty(value="productoMasConsumido")
	private ProductoSingular productoMasConsumido;
	
	@JsonProperty(value="restauranteMasFrecuentado")
	private Restaurante restauranteMasFrecuentado;
	
	@JsonProperty(value="productoMenosConsumido")
	private ProductoSingular productoMenosConsumido;
	
	@JsonProperty(value="restauranteMenosFrecuentado")
	private Restaurante restauranteMenosFrecuentado;
	
	@JsonProperty(value="dia")
	private String dia;
	
	public FuncionamientoRotonda(@JsonProperty(value="productoMasConsumido")  ProductoSingular productoMasConsumido,@JsonProperty(value="restauranteMasFrecuentado") Restaurante restauranteMasFrecuentado,@JsonProperty(value="productoMenosConsumido") ProductoSingular productoMenosConsumido,@JsonProperty(value="restauranteMenosFrecuentado") Restaurante restauranteMenosFrecuentado,@JsonProperty(value="dia") String dia)
	{
		
	}

	public ProductoSingular getProductoMasConsumido() {
		return productoMasConsumido;
	}

	public void setProductoMasConsumido(ProductoSingular productoMasConsumido) {
		this.productoMasConsumido = productoMasConsumido;
	}

	public Restaurante getRestauranteMasFrecuentado() {
		return restauranteMasFrecuentado;
	}

	public void setRestauranteMasFrecuentado(Restaurante restauranteMasFrecuentado) {
		this.restauranteMasFrecuentado = restauranteMasFrecuentado;
	}

	public ProductoSingular getProductoMenosConsumido() {
		return productoMenosConsumido;
	}

	public void setProductoMenosConsumido(ProductoSingular productoMenosConsumido) {
		this.productoMenosConsumido = productoMenosConsumido;
	}

	public Restaurante getRestauranteMenosFrecuentado() {
		return restauranteMenosFrecuentado;
	}

	public void setRestauranteMenosFrecuentado(Restaurante restauranteMenosFrecuentado) {
		this.restauranteMenosFrecuentado = restauranteMenosFrecuentado;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
	
	
}
