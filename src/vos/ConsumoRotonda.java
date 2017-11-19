package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsumoRotonda {
	
	@JsonProperty(value="cedula")
	private long cedula;
	
	@JsonProperty(value="local")
	private int local;
	
	@JsonProperty(value="fechaInicio")
	private String fechaInicio;
	
	@JsonProperty(value="fechaFin")
	private String fechaFin;
	
	public ConsumoRotonda (@JsonProperty(value="fechaInicio") String fechaInicio,@JsonProperty(value="fechaFin") String fechaFin, @JsonProperty(value="local") int local,@JsonProperty(value="cedula") long cedula) {
		this.cedula = cedula;
		this.local=local;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
	}

	public int getLocal() {
		return local;
	}
	
	public void setLocal(int local) {
		this.local = local;
	}
	
	public long getCedula() {
		return cedula;
	}
	
	public void setCedula(long cedula) {
		this.cedula = cedula;
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
