package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultarFuncionamiento {

	@JsonProperty(value="idGerente")
	private int id;


	//Si es operador o alojamiento
	@JsonProperty(value="tipo")
	private String tipo;


	// Si es los mas o los menos
	@JsonProperty(value="tipo2")
	private String tipo2;

	
	public ConsultarFuncionamiento(@JsonProperty(value="idGerente") int id, @JsonProperty(value="tipo") String tipo, @JsonProperty(value="tipo2") String tipo2) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.tipo2 = tipo2;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo2() {
		return tipo2;
	}

	public void setTipo2(String tipo2) {
		this.tipo2 = tipo2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}