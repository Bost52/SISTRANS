package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AgregarProducto {
	@JsonProperty(value="cedulaRestaurante")
	private int cedulaRestaurante;
	
	@JsonProperty(value="producto")
	private ProductoSingular producto;
	
	@JsonProperty(value="local")
	private int local;
	
	@JsonProperty(value="cantidad")
	private int cantidad;
	
	@JsonProperty(value="coste")
	private double coste;
	
	@JsonProperty(value="precio")
	private double precio;
	
	public AgregarProducto(@JsonProperty(value="cedulaRestaurante") int cedulaRestaurante, 	@JsonProperty(value="producto") ProductoSingular producto,@JsonProperty(value="local") int local,@JsonProperty(value="cantidad") int cantidad,	@JsonProperty(value="precio")  double precio,	@JsonProperty(value="coste") double coste) {
		this.cedulaRestaurante=cedulaRestaurante;
		this.producto=producto;
		this.cantidad=cantidad;
		this.coste=coste;
		this.local=local;
		this.precio=precio;
	}

	public int getCedulaRestaurante() {
		return cedulaRestaurante;
	}
	
	public void setCedulaRestaurante(int cedulaRestaurante) {
		this.cedulaRestaurante = cedulaRestaurante;
	}
	
	public ProductoSingular getProducto() {
		return producto;
	}
	
	public void setProducto(ProductoSingular producto) {
		this.producto = producto;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public double getCoste() {
		return coste;
	}
	
	public void setCoste(double coste) {
		this.coste = coste;
	}
	
	public int getLocal() {
		return local;
	}
	
	public void setLocal(int local) {
		this.local = local;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}
	

