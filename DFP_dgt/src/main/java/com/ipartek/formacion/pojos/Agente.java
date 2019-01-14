package com.ipartek.formacion.pojos;

public class Agente {

	private Long id;
	private String nombre;
	private Integer placa;

	public Agente() {
		super();
		this.id = -1L;
		this.nombre = "";
		this.placa = -1;
	}

	public Agente(Long id, String nombre, Integer placa, Integer id_departamento) {
		this();
		setId(id);
		setNombre(nombre);
		setPlaca(placa);
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

	public Integer getPlaca() {
		return placa;
	}

	public void setPlaca(Integer placa) {
		this.placa = placa;
	}

	@Override
	public String toString() {
		return "Agente [id=" + id + ", nombre=" + nombre + ", placa=" + placa + "]";
	}

}
