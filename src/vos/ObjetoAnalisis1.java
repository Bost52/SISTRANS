package vos;

public class ObjetoAnalisis1 {

	private int cedula;
	
	private String nombre;
	
	private String rol;
	
	private String email;
	
	private int idProducto;
	
	private int local;
	
	private int idPedido;

	public ObjetoAnalisis1(int cedula, String nombre, String rol, String email, int idProducto, int local,
			int idPedido) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.rol = rol;
		this.email = email;
		this.idProducto = idProducto;
		this.local = local;
		this.idPedido = idPedido;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	
	
}
