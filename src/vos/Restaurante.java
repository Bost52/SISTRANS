package vos;

import org.codehaus.jackson.annotate.*;


public class Restaurante {

	@JsonProperty(value="id")
	private Long id;

	@JsonProperty(value="name")
	private String name;

	public Restaurante(@JsonProperty(value="id")Long id, @JsonProperty(value="name")String name) {
		super();
		this.id = id;
		this.name = name;
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



}
