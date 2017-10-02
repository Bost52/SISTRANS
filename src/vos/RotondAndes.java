package vos;

import java.util.ArrayList;

public class RotondAndes {

	private ArrayList<Restaurante> restaurantes;
	
	private ArrayList<Zona> zonas;
	
	private ArrayList<ClienteFrecuente> clientes;
	
	private ArrayList<Organizador> organizadores;

	public RotondAndes(ArrayList<Restaurante> restaurantes, ArrayList<Zona> zonas, ArrayList<ClienteFrecuente> clientes,
			ArrayList<Organizador> organizadores) {
		super();
		this.restaurantes = restaurantes;
		this.zonas = zonas;
		this.clientes = clientes;
		this.organizadores = organizadores;
	}

	public void agregarRestaurante(Restaurante res){
		restaurantes.add(res);
	}
	
	public boolean eliminarRestaurante(Restaurante res){
		return restaurantes.remove(res);	
	}
	
	public void agregarZona(Zona zona){
		zonas.add(zona);
	}
	
	public boolean eliminarZona(Zona zona){
		return zonas.remove(zona);	
	}
	
	public void agregarCliente(ClienteFrecuente clien){
		clientes.add(clien);
	}
	
	public boolean eliminarCliente(Restaurante res){
		return restaurantes.remove(res);	
	}
	
	public void agregarOrganizador(Organizador org){
		organizadores.add(org);
	}
	
	public boolean eliminarOrganizador(Organizador org){
		return organizadores.remove(org);	
	}
	
	
	// getters y setters
	
	public ArrayList<Restaurante> getRestaurantes() {
		return restaurantes;
	}

	public void setRestaurantes(ArrayList<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}

	public ArrayList<Zona> getZonas() {
		return zonas;
	}

	public void setZonas(ArrayList<Zona> zonas) {
		this.zonas = zonas;
	}

	public ArrayList<ClienteFrecuente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<ClienteFrecuente> clientes) {
		this.clientes = clientes;
	}

	public ArrayList<Organizador> getOrganizadores() {
		return organizadores;
	}

	public void setOrganizadores(ArrayList<Organizador> organizadores) {
		this.organizadores = organizadores;
	}
	
	
	
}
