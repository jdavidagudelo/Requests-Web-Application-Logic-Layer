package co.edu.udea.iw.rtf.client.dto;

import java.io.Serializable;

import com.google.gwt.i18n.client.Dictionary;

/**
 * Clase usada para almacenar la sesión de un usuario, la instancia de esta
 * clase será manjeada usando el patrón singleton y sera borrada una vez el
 * usuario cierra la sesion.
 * */
public class UsuarioSingleton implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Instancia que se garantiza que solo existe una a lo largo de la ejecucion
	 * del programa.
	 * */
	private static UsuarioSingleton instance;
	/**
	 * Login del usuario.
	 * */
	private String login;
	/**
	 * Indica si el usuario tiene privilegios para asignar solicitudea a
	 * usuarios.
	 * */
	private Boolean isAdministrador;

	/**
	 * Si la instancia es null la incializa y la retorna.
	 * */
	public static UsuarioSingleton getInstance() {
		if (instance == null) {
			instance = new UsuarioSingleton();
		}
		return instance;
	}

	public Boolean getIsAdministrador() {
		return isAdministrador;
	}

	public void setIsAdministrador(Boolean isAdministrador) {
		this.isAdministrador = isAdministrador;
	}

	public static void clearInstance() {
		instance = null;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	private UsuarioSingleton() {

	}

	/**
	 * Incializa la instancia de la clase a partir de un diccionario que
	 * contiene una variable javascript asociada con la sesión del usuario.
	 * 
	 * @param dic
	 *            Variable que contiene una variable javascript que corresponde
	 *            a un par nombre valor.
	 * */
	public static void setUpFromDictionary(Dictionary dic) {
		instance = new UsuarioSingleton();
		String login = dic.get("login");
		Boolean isAdministrador = Boolean.valueOf(dic.get("isAdministrador"));
		if (login != null && !login.isEmpty()) {
			instance.setLogin(login);
			instance.setIsAdministrador(isAdministrador);
		} else {
			instance.setLogin(null);
			instance.setIsAdministrador(Boolean.FALSE);
		}
	}

}
