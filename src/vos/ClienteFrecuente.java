package vos;

public class ClienteFrecuente {

	private Long id;
	
	private String nombre;
	
	private Long cuentaPSE;
	
	private Orden ordenActual;
	
	public ClienteFrecuente(Long id, String nombre, Long cuentaPSE, Orden ordenActual) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cuentaPSE = cuentaPSE;
		this.ordenActual = ordenActual;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getCuentaPSE() {
		return cuentaPSE;
	}

	public void setCuentaPSE(Long cuentaPSE) {
		this.cuentaPSE = cuentaPSE;
	}

	public Orden getOrdenActual() {
		return ordenActual;
	}

	public void setOrdenActual(Orden ordenActual) {
		this.ordenActual = ordenActual;
	}
	
	
}
