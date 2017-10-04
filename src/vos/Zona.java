package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Zona {
	
	@JsonProperty(value="idZona")
	private int idZona;
	
	@JsonProperty(value="tipoComida")
	private TipoDeComida tipoCom;
	
	
	public Zona(@JsonProperty(value="idZona") int idZona,@JsonProperty(value="tipoComida") TipoDeComida tipoComida) {
		this.idZona=idZona;
		this.tipoCom=tipoComida;
	}

	public TipoDeComida getTipoCom() {
		return tipoCom;
	}

	public void setTipoCom(TipoDeComida tipoCom) {
		this.tipoCom = tipoCom;
	}
	
	public int getIdZona() {
		return idZona;
	}
	
	public void setIdZona(int idZona) {
		this.idZona = idZona;
	}
	
}
