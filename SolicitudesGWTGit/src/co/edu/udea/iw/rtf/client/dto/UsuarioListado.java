package co.edu.udea.iw.rtf.client.dto;

/**
 * @author Andr�s Vanegas L 
 * E-mail: anfevaloudea@gmail.com
 * @author Juan David Agudelo
 * E-mail: jdaaa2009@gmail.com
 */

import java.io.Serializable;

/**
 * Clase que permite almacenar la información de un usuario en la capa de vista.
 * */
public class UsuarioListado implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * El login del usuario.
	 * */
	private String login;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public UsuarioListado(String login, String nombre) {
		super();
		this.login = login;
	}

	public UsuarioListado() {
		super();
	}

}
