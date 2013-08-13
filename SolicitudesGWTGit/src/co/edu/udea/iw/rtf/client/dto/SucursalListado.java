package co.edu.udea.iw.rtf.client.dto;

/**
 * @author Andr�s Vanegas L 
 * E-mail: anfevaloudea@gmail.com
 * @author Juan David Agudelo
 * E-mail: jdaaa2009@gmail.com
 */

import java.io.Serializable;

/**
 * Clase usada para almacenar la información de una sucursal en la capa de
 * vista.
 * */
public class SucursalListado implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Codigo de la sucursal.
	 * */
	private Long codigo;
	/**
	 * Nombre de la sucursal.
	 * */
	private String nombre;

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

	public SucursalListado(Long codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public SucursalListado() {
		super();
	}
}
