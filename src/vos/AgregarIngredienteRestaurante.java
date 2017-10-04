package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AgregarIngredienteRestaurante {

	@JsonProperty(value="usuarioRestaurante")
	private int cedulaUsuarioRestaurante;
	
	@JsonProperty(value="ingrediente")
	private Ingrediente ingrediente;
	
	public AgregarIngredienteRestaurante(@JsonProperty(value="usuarioRestaurante") int cedulaUsuarioRestaurante, @JsonProperty(value="ingrediente") Ingrediente ingrediente) {
		this.cedulaUsuarioRestaurante = cedulaUsuarioRestaurante;
		this.ingrediente = ingrediente;
	}

	public int getCedulaUsuarioRestaurante() {
		return cedulaUsuarioRestaurante;
	}

	public void setCedulaUsuarioRestaurante(int cedulaUsuarioRestaurante) {
		this.cedulaUsuarioRestaurante = cedulaUsuarioRestaurante;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	
	
}
