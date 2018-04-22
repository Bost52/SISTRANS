package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaMasiva {


	@JsonProperty(value="id")
	private int id;

	@JsonProperty(value="usuario")
	private int idUsuario;

	@JsonProperty(value="cantidad")
	private int cantidad;

	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="fechaInicio")
	private String fechaInicio;
	
	@JsonProperty(value="fechaFin")
	private String fechaFin;


	//private Servicios[] servicios;


	public ReservaMasiva(@JsonProperty(value="id") int id, @JsonProperty(value="usuario") int idUsuario, @JsonProperty(value="cantidad") int cant, @JsonProperty(value="tipo") String tipo, @JsonProperty(value="fechaInicio")String fechaIni, @JsonProperty(value="fechaFin")String fechaFin){
		this.id = id;
		this.idUsuario = idUsuario;
		this.cantidad = cant;
		this.tipo = tipo;
		this.fechaInicio = fechaIni;
		this.fechaFin = fechaFin;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public String getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
}
