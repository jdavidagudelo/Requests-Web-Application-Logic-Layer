package co.edu.udea.iw.rtf.dto;

import java.util.Set;

/**
 * Esta clase representa un tipo de solicitud recibida por la empresa de parte
 * de sus clientes.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public class TipoSolicitud {
	/**
	 * Identificador único utilizado para diferenciar el tipo de solicitud
	 * dentro de la tabla de tipos de solicitudes. Este atributo es obligatorio.
	 * */
	private Long codigo;
	/**
	 * El nombre del tipo de solicitud. Puede ser peticion, queja, reclamo o
	 * sugerencia. Este atributo es obligatorio.
	 * */
	private String nombre;
	/**
	 * Indica si el tipo de solicitud puede ser usado por el usuario. Este
	 * atributo es obligatorio.
	 * */
	private Boolean activo = Boolean.TRUE;
	/**
	 * Contiene todas las solicitudes de este tipo existentes en el sistema. Es
	 * cargada automaticamente por hibernate.
	 * */
	private Set<Solicitud> solicitudes;

	public Set<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(Set<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
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

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public TipoSolicitud(Long codigo, String nombre, Boolean activo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.activo = activo;
	}

	public TipoSolicitud() {
		super();
	}

}
