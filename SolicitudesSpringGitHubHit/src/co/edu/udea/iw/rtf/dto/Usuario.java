package co.edu.udea.iw.rtf.dto;

import java.util.Set;

/**
 * Esta clase representa un usuario que se registra en el sistema para responder
 * preguntas o para responderlas.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public class Usuario {
	/**
	 * Nombre de usuario, este será el identificador único de los usuarios. Este
	 * atributo es obligatorio.
	 * */
	private String login;
	/**
	 * Nombre completo del usuario. Este atributo es obligatorio.
	 * */
	private String nombreCompleto;
	/**
	 * Clave del usuario, está clave debe estar cifrada por seguridad. Este
	 * atributo es obligatorio.
	 * */
	private String clave;
	/**
	 * Indica si el usuario está activo o no. Este atributo es opcional.
	 * */
	private Boolean activo = Boolean.TRUE;
	/**
	 * Indica el rol del usuario en el sistema. Este atributo representa una
	 * clave foranea a la tabla de roles. Este atributo es obligatorio.
	 * */
	private Rol rol;
	/**
	 * Representa las solicitudes que han sido asignadas al usuario para que las
	 * responda. Este atributo es almacenado automaticamente por hibernate.
	 * */
	private Set<Solicitud> solicitudesUsuarioAsignada;
	/**
	 * Reprensenta la lista de las solicitudes que el usuario ha respondido.
	 * Esta lista es llenada por hibernate.
	 * */
	private Set<Solicitud> solicitudesUsuarioResponde;

	public Set<Solicitud> getSolicitudesUsuarioAsignada() {
		return solicitudesUsuarioAsignada;
	}

	public void setSolicitudesUsuarioAsignada(
			Set<Solicitud> solicitudesUsuarioAsignada) {
		this.solicitudesUsuarioAsignada = solicitudesUsuarioAsignada;
	}

	public Set<Solicitud> getSolicitudesUsuarioResponde() {
		return solicitudesUsuarioResponde;
	}

	public void setSolicitudesUsuarioResponde(
			Set<Solicitud> solicitudesUsuarioResponde) {
		this.solicitudesUsuarioResponde = solicitudesUsuarioResponde;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Usuario(String login, String nombreCompleto, String clave,
			Boolean activo, Rol rol) {
		super();
		this.login = login;
		this.nombreCompleto = nombreCompleto;
		this.clave = clave;
		this.activo = activo;
		this.rol = rol;
	}

	public Usuario() {
		super();
	}

}
