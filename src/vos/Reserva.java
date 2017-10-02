package vos;

import java.sql.Date;

//import java.util.Date;

public class Reserva {

	private Date fechaYHora;
	
	private Integer numeroComensales;
	
	private Menu menu;

	public Reserva(Date fechaYHora, Integer numeroComensales, Menu menu) {
		super();
		this.fechaYHora = fechaYHora;
		this.numeroComensales = numeroComensales;
		this.menu = menu;
	}

	public Date getFechaYHora() {
		return fechaYHora;
	}

	public void setFechaYHora(Date fechaYHora) {
		this.fechaYHora = fechaYHora;
	}

	public Integer getNumeroComensales() {
		return numeroComensales;
	}

	public void setNumeroComensales(Integer numeroComensales) {
		this.numeroComensales = numeroComensales;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	
	
}
