package vos;

import org.codehaus.jackson.annotate.*;


public class Restaurante {

	@JsonProperty(value="id")
	private Long id;

	@JsonProperty(value="name")
	private String name;
	
	@JsonProperty(value="url")
	private String urlPaginaWeb;
	
	private TipoDeComida tipoCom;
	
	private Representante representante;

	public Restaurante(@JsonProperty(value="id")Long id, @JsonProperty(value="name")String name, @JsonProperty(value="url") String urlPagina) {
		super();
		this.id = id;
		this.name = name;
		this.urlPaginaWeb = urlPagina;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

}
