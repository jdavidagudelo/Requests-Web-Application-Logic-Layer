package co.edu.udea.iw.rtf.dto;

import java.util.Set;

/**
 * Esta clase representa una sucursal de la empresa en la cual fue atendido un
 * cliente que realizo una solicitud.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public class Sucursal {
	/**
	 * Identificador único de una sucursal dentro de la tabla de sucursales.
	 * Este atributo es obligatorio.
	 * */
	private Long codigo;
	/**
	 * El nombre de la sucursal. Este atributo es obligatorio.
	 * */
	private String nombre;
	/**
	 * La direccion de la sucursal. Este atributo es opcional.
	 * */
	private String direccion;
	/**
	 * El telefono de la sucursal. este atributo es opcional.
	 * */
	private String telefono;
	/**
	 * Indica si la sucursal puede ser utilizada en las selecciones de los
	 * clientes. Este atributo es opcional.
	 * */
	private Boolean activo = Boolean.TRUE;
	/**
	 * Este atributo representa todos las solicitudes realizadas en esta
	 * sucursal, mediante un mapeo many-to-one en hibernate.
	 * */
	private Set<Solicitud> solicitudes;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Sucursal(Long codigo, String nombre, String direccion,
			String telefono, Boolean activo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.activo = activo;
	}

	public Set<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(Set<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public Sucursal() {
		super();
	}

}
