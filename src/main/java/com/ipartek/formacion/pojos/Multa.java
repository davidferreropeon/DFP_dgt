package com.ipartek.formacion.pojos;

import java.sql.Date;
import java.sql.Time;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Multa {

	private Long id;

	@NotNull(message = "No puede estar vacio y debe ser un valor numerico")
	private Integer importe;

	@NotEmpty
	@Size(min = 10, max = 150)
	private String concepto;
	private Date fecha;
	private Coche coche;
	private Time hora;

	public Multa() {
		super();
		this.id = -1L;
		this.importe = -1;
		this.concepto = "";
		this.fecha = null;
		this.hora = null;
		this.coche = new Coche();
	}

	public Multa(Long id, Integer importe, String concepto, Date fecha, Time hora, Coche coche) {
		this();
		setId(id);
		setImporte(importe);
		setConcepto(concepto);
		setFecha(fecha);
		setHora(hora);
		setCoche(coche);
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public Coche getCoche() {
		return coche;
	}

	public void setCoche(Coche coche) {
		this.coche = coche;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getImporte() {
		return importe;
	}

	public void setImporte(Integer importe) {
		this.importe = importe;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Multa [id=" + id + ", importe=" + importe + ", concepto=" + concepto + ", fecha=" + fecha + ", coche="
				+ coche + ", hora=" + hora + "]";
	}

}
