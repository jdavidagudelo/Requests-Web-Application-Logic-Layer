package co.edu.udea.iw.rtf.client.dto;

import java.io.Serializable;

/**
 * Clase utilizada para obtener la información relacionada con un producto y
 * mostrarla en un lista.
 * 
 * @author Andr�s Vanegas L E-mail: anfevaloudea@gmail.com
 * @author Juan David Agudelo E-mail: jdaaa2009@gmail.com
 */
public class ProductoListado implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Codigo del producto en la base de datos.
	 * */
	private Long codigo;
	/**
	 * Nombre del producto en la base de datos.
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

	public ProductoListado(Long codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public ProductoListado() {
		super();
	}

}
