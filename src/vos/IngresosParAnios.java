package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IngresosParAnios {

	@JsonProperty(value="id")
	private long id;
	
	@JsonProperty(value="ingresos")
	private int ingresos;
	
	public IngresosParAnios(long id, int ingresos) {
		super();
		this.id = id;
		this.ingresos = ingresos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getIngresos() {
		return ingresos;
	}

	public void setIngresos(int ingresos) {
		this.ingresos = ingresos;
	}

}
