package vos;

import java.util.ArrayList;

public class Zona {
	
	private String nombre;
	
	private ArrayList<Restaurante> restaurantes;
	
	private Integer capacidadComensales;
	
	private boolean aptoParaPersonasEnCondicionesEspeciales;
	
	private String condicionesTecnicas;
	
	private TipoDeEspacio tipoEsp;
	
	private TipoDeComida tipoCom;
	
	public enum TipoDeEspacio{
		ABIERTO, CERRADO
	}

	
	public Zona(String nombre, ArrayList<Restaurante> restaurantes, Integer capacidadComensales,
			boolean aptoParaPersonasEnCondicionesEspeciales, String condicionesTecnicas, TipoDeEspacio tipoEsp,
			TipoDeComida tipoCom) {
		super();
		this.nombre = nombre;
		this.restaurantes = restaurantes;
		this.capacidadComensales = capacidadComensales;
		this.aptoParaPersonasEnCondicionesEspeciales = aptoParaPersonasEnCondicionesEspeciales;
		this.condicionesTecnicas = condicionesTecnicas;
		this.tipoEsp = tipoEsp;
		this.tipoCom = tipoCom;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Restaurante> getRestaurantes() {
		return restaurantes;
	}

	public void setRestaurantes(ArrayList<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}

	public Integer getCapacidadComensales() {
		return capacidadComensales;
	}

	public void setCapacidadComensales(Integer capacidadComensales) {
		this.capacidadComensales = capacidadComensales;
	}

	public boolean isAptoParaPersonasEnCondicionesEspeciales() {
		return aptoParaPersonasEnCondicionesEspeciales;
	}

	public void setAptoParaPersonasEnCondicionesEspeciales(boolean aptoParaPersonasEnCondicionesEspeciales) {
		this.aptoParaPersonasEnCondicionesEspeciales = aptoParaPersonasEnCondicionesEspeciales;
	}

	public String getCondicionesTecnicas() {
		return condicionesTecnicas;
	}

	public void setCondicionesTecnicas(String condicionesTecnicas) {
		this.condicionesTecnicas = condicionesTecnicas;
	}

	public TipoDeEspacio getTipoEsp() {
		return tipoEsp;
	}

	public void setTipoEsp(TipoDeEspacio tipoEsp) {
		this.tipoEsp = tipoEsp;
	}

	public TipoDeComida getTipoCom() {
		return tipoCom;
	}

	public void setTipoCom(TipoDeComida tipoCom) {
		this.tipoCom = tipoCom;
	}
	
	
}
