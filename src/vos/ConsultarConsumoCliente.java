package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultarConsumoCliente {

	@JsonProperty(value="cedula")
	private long cedula;
	
	@JsonProperty(value="productos")
	private ArrayList<ProductoSingular> productos;
	
	@JsonProperty(value="menus")
	private ArrayList<Menu> menus;
	
	@JsonProperty(value="productosMesa")
	private ArrayList<ProductoSingular> productosMesa;
	
	@JsonProperty(value="menusMesa")
	private ArrayList<Menu> menusMesa;
	
	public ConsultarConsumoCliente() {
	}
	
	public ConsultarConsumoCliente(@JsonProperty(value="cedula") int cedula,@JsonProperty(value="productos") ArrayList<ProductoSingular> productos,@JsonProperty(value="menus") ArrayList<Menu> menus,@JsonProperty(value="productosMesa") ArrayList<ProductoSingular> productosMesa, @JsonProperty(value="menusMesa") ArrayList<Menu> menusMesa ) {
		this.cedula=cedula;
		this.menus=menus;
		this.menusMesa=menusMesa;
		this.productosMesa=productosMesa;
		this.productos=productos;
	}

	public long getCedula() {
		return cedula;
	}

	public void setCedula(long cedula) {
		this.cedula = cedula;
	}

	public ArrayList<ProductoSingular> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<ProductoSingular> productos) {
		this.productos = productos;
	}

	public ArrayList<Menu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}

	public ArrayList<ProductoSingular> getProductosMesa() {
		return productosMesa;
	}

	public void setProductosMesa(ArrayList<ProductoSingular> productosMesa) {
		this.productosMesa = productosMesa;
	}

	public ArrayList<Menu> getMenusMesa() {
		return menusMesa;
	}

	public void setMenusMesa(ArrayList<Menu> menusMesa) {
		this.menusMesa = menusMesa;
	}
	
	
}
