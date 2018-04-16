package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class AgregarReserva {

	@JsonProperty(value="idCliente")
	private Integer idCliente;
	
	@JsonProperty(value="idHospedaje")
	private Integer idHospedaje;
	
	@JsonProperty(value="fechaInicio")
	private String fechaInic;
	
	@JsonProperty(value="fechaFin")
	private String fechaFin;
	
	public AgregarReserva(@JsonProperty(value="idCliente") Integer idCli, @JsonProperty(value="idHospedaje") Integer idHosp, @JsonProperty(value="fechaInicio") String fecIn, @JsonProperty(value="fechaFin") String feFin) {
		this.idCliente = idCli;
		this.idHospedaje = idHosp;
		this.fechaInic = fecIn;
		this.fechaFin = feFin;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdHospedaje() {
		return idHospedaje;
	}

	public void setIdHospedaje(Integer idHospedaje) {
		this.idHospedaje = idHospedaje;
	}

	public String getFechaInic() {
		return fechaInic;
	}

	public void setFechaInic(String fechaInic) {
		this.fechaInic = fechaInic;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}


	
	
}
