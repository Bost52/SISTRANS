package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultarBuenosClientes {


	@JsonProperty(value="idGerente")
	private int id;

	public ConsultarBuenosClientes(@JsonProperty(value="idGerente")int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
