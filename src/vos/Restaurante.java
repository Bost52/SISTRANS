package vos;

import org.codehaus.jackson.annotate.*;


public class Restaurante {

	@JsonProperty(value="local")
	private int local;

	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="urlpaginaweb")
	private String urlPaginaWeb;
	
	@JsonProperty(value="tipoComida")
	private TipoDeComida tipoCom;
	
	@JsonProperty(value="representante")
	private Usuario representante;

	public Restaurante(@JsonProperty(value="local")int local, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="url") String urlPagina,@JsonProperty(value="representante") Usuario representante ,@JsonProperty(value="tipoComida") TipoDeComida tipoComida) {
		super();
		this.local = local;
		this.nombre = nombre;
		this.urlPaginaWeb = urlPagina;
		this.representante=representante;
		this.tipoCom=tipoComida;
	}

	public int getLocal() {
		return local;
	}

	public void setId(int local) {
		this.local = local;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrlPaginaWeb() {
		return urlPaginaWeb;
	}

	public void setUrlPaginaWeb(String urlPaginaWeb) {
		this.urlPaginaWeb = urlPaginaWeb;
	}

	public TipoDeComida getTipoCom() {
		return tipoCom;
	}

	public void setTipoCom(TipoDeComida tipoCom) {
		this.tipoCom = tipoCom;
	}

	public Usuario getRepresentante() {
		return representante;
	}

	public void setRepresentante(Usuario representante) {
		this.representante = representante;
	}

}
