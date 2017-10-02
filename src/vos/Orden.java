package vos;

import java.util.ArrayList;

public class Orden {

	private Long id;
	
	private ArrayList<Producto> productos;
	
	private Double costoTotal;
	
	private Long idCliente;
	
	public Orden(Long id, ArrayList<Producto> productos, Double costoTotal, Long idCliente) {
		super();
		this.id = id;
		this.productos = productos;
		this.costoTotal = costoTotal;
		this.idCliente = idCliente;
	}
	
	public void agregarProducto(Producto prod){
		productos.add(prod);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public Double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(Double costoTotal) {
		this.costoTotal = costoTotal;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	
	
}
