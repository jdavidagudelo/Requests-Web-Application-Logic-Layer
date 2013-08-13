package co.edu.udea.iw.rtf.client.dto;

/**
 * @author Andr�s Vanegas L 
 * E-mail: anfevaloudea@gmail.com
 * @author Juan David Agudelo
 * E-mail: jdaaa2009@gmail.com
 */

import java.io.Serializable;

/**
 * Clase usada para almacenar la información de un tipo de solicitud en la
 * vista.
 * */
public class TipoSolicitudListado implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Codigo del tipo de solicitud.
	 * */
	private Long codigo;
	/**
	 * Nombre del tipo de solicitud.
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

	public TipoSolicitudListado(Long codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public TipoSolicitudListado() {
		super();
	}
}
