package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductoSingular{
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="descripcionTraducida")
	private String descripcionTraducida;
	
	@JsonProperty(value="idProducto")
	private int idProducto;
	
	@JsonProperty(value="categoria")
	private Categoria categoria;
	
	


	public ProductoSingular(@JsonProperty(value="idProducto") int id, @JsonProperty(value="nombre") String nombre,@JsonProperty(value="descripcion") String descripcion,@JsonProperty(value="descripcionTraducida")String descripcionTraducida,@JsonProperty(value="categoria") Categoria categoria) {
		this.idProducto=id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.descripcionTraducida = descripcionTraducida;
		this.categoria=categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionTraducida() {
		return descripcionTraducida;
	}

	public void setDescripcionTraducida(String descripcionTraducida) {
		this.descripcionTraducida = descripcionTraducida;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public int getIdProducto() {
		return idProducto;
	}
	
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}


	
	
}
