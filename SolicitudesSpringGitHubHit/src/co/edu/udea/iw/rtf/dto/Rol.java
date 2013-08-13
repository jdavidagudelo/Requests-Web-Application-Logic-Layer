package co.edu.udea.iw.rtf.dto;

import java.util.Set;

/**
 * Representa un rol de un usuario que accede al sistema de solicitudes, en este
 * caso puede ser un cliente de la empresa, un responsable de responder
 * solicitudes, o un gerente. Sirve para asignar permisos a cada usuario de
 * acuerdo con su rol.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public class Rol {
	/**
	 * Identificador único del rol dentro de la tabla de roles. Este atributo es
	 * obligatorio.
	 * */
	private Long codigo;
	/**
	 * Nombre del rol, puede ser gerente de cuenta, responsable de responder
	 * solicitud o cliente de la empresa. Este atributo es obligatiorio.
	 * */
	private String nombre;
	/**
	 * Lista de los usuarios que tienen este rol en el sistema. Esta lista es
	 * cargada en memoria por hibernate.
	 * */
	private Set<Usuario> usuarios;

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

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

	/**
	 * @param codigo
	 *            identificador único del rol dentro de la tabla de roles.
	 * @param nombre
	 *            nombre del rol
	 * */
	public Rol(Long codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public Rol() {
		super();
	}

}
