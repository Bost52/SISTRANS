package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class Menu {

	private ArrayList<ProductoSingular> productos;

	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="precio")
	private double precio; 
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="local")
	private int local;
	
	@JsonProperty(value="max")
	private int max;
	
	@JsonProperty(value="cantidad")
	private int cantidad;
	
	@JsonProperty(value="coste")
	private double coste;
	
	public Menu(@JsonProperty(value="coste") double coste,@JsonProperty(value="cantidad") int cantidad,@JsonProperty(value="max") int max,@JsonProperty(value="id")Long id, @JsonProperty(value="precio")double precio,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="local") int local) {
		this.id = id;
		this.precio = precio;
		this.nombre = nombre;
		this.local = local;
		this.coste=coste;
		this.cantidad=cantidad;
		this.max=max;
	}

	public ArrayList<ProductoSingular> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<ProductoSingular> productos) {
		this.productos = productos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
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

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
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
	
	
	
}
