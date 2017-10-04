package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AgregarMenu {

	@JsonProperty(value="cedulaRestaurante")
	private int cedulaRestaurante;
	
	@JsonProperty(value="menu")
	private Menu menu;
	
	public AgregarMenu(@JsonProperty(value="cedulaRestaurante") int cedulaRestaurante, 	@JsonProperty(value="menu") Menu menu) {
		this.cedulaRestaurante = cedulaRestaurante;
		this.menu = menu;
	}

	public int getCedulaRestaurante() {
		return cedulaRestaurante;
	}
	
	public void setCedulaRestaurante(int cedulaRestaurante) {
		this.cedulaRestaurante = cedulaRestaurante;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
