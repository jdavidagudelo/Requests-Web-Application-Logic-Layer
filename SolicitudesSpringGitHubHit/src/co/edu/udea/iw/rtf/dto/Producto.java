package co.edu.udea.iw.rtf.dto;

import java.util.Set;

/**
 * Clase que representa un producto vendido por la empresa.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public class Producto {
	/**
	 * Identificador único del producto en la tabla de productos. Este atributo
	 * es obligatorio.
	 * */
	private Long codigo;
	/**
	 * Nombre del producto. Este atributo es obligatorio.
	 * */
	private String nombre;
	/**
	 * Lista de las solicitudes realizadas para este producto. Esta lista es
	 * llenada automaticamente por hibernate.
	 * */
	private Set<Solicitud> solicitudes;

	public Set<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(Set<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

	/**
	 * Indica si el producto está activo, es decir puede ser utilizado en las
	 * selecciones de los clientes de la empresa al realizar las solicitudes.
	 * Este atributo es opcional.
	 * */
	private Boolean activo = Boolean.TRUE;

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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	/**
	 * @param codigo
	 *            identificador único del producto en la base de datos.
	 * @param nombre
	 *            el nombre del producto
	 * @param activo
	 *            indica si el producto puede ser utilizado en las selecciones
	 *            del usuario.
	 * */
	public Producto(Long codigo, String nombre, Boolean activo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.activo = activo;
	}

	public Producto() {
		super();
	}

}
