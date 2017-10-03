package vos;

public class Producto {

	private Long id;
	
	private double precio;
	
	private TipoProducto tipo;
	
	public enum TipoProducto{
		VEGETARIANO, PASTAS, CHINO, TACOS, SIN_GLUTEN, SANDWICH
	}

	
	public Producto(Long id, double precio) {
		super();
		this.id = id;
		this.precio = precio;
	}

	public TipoProducto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProducto tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
}
