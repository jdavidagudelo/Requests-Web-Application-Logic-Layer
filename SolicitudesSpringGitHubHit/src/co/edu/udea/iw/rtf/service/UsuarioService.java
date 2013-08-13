package co.edu.udea.iw.rtf.service;

import java.util.List;
import java.util.ResourceBundle;

import co.edu.udea.iw.rtf.dao.UsuarioDAO;
import co.edu.udea.iw.rtf.dto.Rol;
import co.edu.udea.iw.rtf.dto.Usuario;
import co.edu.udea.iw.rtf.util.encode.Cifrar;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;
import co.edu.udea.iw.rtf.util.validations.Validaciones;

/**
 * Esta clase contiene la lógica del negocio para los usuarios del sistema.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class UsuarioService {
	/**
	 * Este atributo será inyectado en este bean por Spring durante la ejecución
	 * del programa. Permite el acceso a la tabla de usuarios de la base de
	 * datos.
	 * */
	public UsuarioDAO usuarioDAO;
	/**
	 * Este atributo será inyectado en este bean por Spring durante la ejecución
	 * del programa. Permite el acceso a la tabla de roles de la base de datos.
	 * */
	public RolService rolService;

	public RolService getRolService() {
		return rolService;
	}

	public void setRolService(RolService rolService) {
		this.rolService = rolService;
	}

	/**
	 * Permite obtener la lista de los usuarios de la base de datos.
	 * 
	 * @return La lista de usuario disponibles dentro de la base de datos.
	 * @throws IWDaoException
	 *             En caso de error en el acceso a los datos.
	 * */
	public List<Usuario> obtener() throws IWDaoException {
		return usuarioDAO.obtener();
	}

	/**
	 * Permite obtener el usuario con el login ingresado como argumento.
	 * 
	 * @param login
	 *            el login del usuario de la base de datos.
	 * @return el usuario que se desea buscar en la base de datos.
	 * @throws IWDaoException
	 *             En caso de presentar error en el acceso a los datos.
	 * */
	public Usuario obtener(String login) throws IWDaoException {
		if (Validaciones.isTextoVacio(login)) {
			throw new NullPointerException(
					"El login del usuario no puede ser nulo o vacio");
		}
		return usuarioDAO.obtener(login);
	}

	/**
	 * Valida que el usuario y la clave del usuario sean validos.
	 * 
	 * @param login
	 *            identificador único del usuario
	 * @param pws
	 *            clave del usuario sin cifrar.
	 * @return True si los datos del usuario son validos, false en caso
	 *         contrario.
	 * @throws IWDaoException
	 *             En caso de presentarse error en acceso a los datos.
	 */
	public Boolean validar(String login, String pws) throws IWDaoException {

		Cifrar cifrar = new Cifrar();

		if (Validaciones.isTextoVacio(login)) {
			throw new NullPointerException("El login no puede ser vacio");
		}

		if (Validaciones.isTextoVacio(pws)) {
			throw new NullPointerException("La contraseña no puede ser vacia");
		}

		Usuario usuario = usuarioDAO.obtener(login);

		if (usuario == null) {
			return false;
		}

		if (!usuario.getClave().equals(cifrar.encrypt(pws))) {
			return false;
		}

		return true;

	}
	public Boolean isAdministrador(String loginUsuario) throws IWDaoException
	{
		ResourceBundle configuracion = ResourceBundle
				.getBundle("co.edu.udea.iw.rtf.util.properties.configuracion");
		String usuarioAdministradorName = configuracion.getString("usuarioAdministradorName");
		if(loginUsuario == null)
		{
			return Boolean.FALSE;
		}
		Usuario usuario = usuarioDAO.obtener(loginUsuario);
		if(usuario == null)
		{
			return false;
		}
		return usuario.getRol().getNombre().equalsIgnoreCase(usuarioAdministradorName);
	}
	/**
	 * Método que permite crear un nuevo usuario en la base de datos.
	 * 
	 * @param login
	 *            El identificador unico del usuario que se desea crear. En caso
	 *            de que sea vacio o nulo o ya exista un usuario con el mismo
	 *            login se lanzará una excepcion.
	 * @param nombres
	 *            El nombre completo del nuevo usuario. No puede ser nulo o
	 *            vacio.
	 * @param pws
	 *            La clave del nuevo usuario sin cifrar. No puede ser nula o
	 *            vacia.
	 * @param rol
	 *            El codigo del rol del nuevo usuario. No puede ser nulo y debe
	 *            existir en la base de datos.
	 * @throws IWDaoException
	 *             En caso de que el login del nuevo usuario ya exista en la
	 *             base de datos.
	 * @throws NullPointerException
	 *             En caso de que alguno de los campos sea invalido.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * */
	public Usuario crearUsuario(String login, String nombres, String pws,
			Long rol) throws IWDaoException {
		if (Validaciones.isTextoVacio(login)) {
			throw new NullPointerException(
					"El login del usuario no puede ser nulo o una cadena de caracteres vacia");
		}
		if (login.length() > 15) {
			throw new NullPointerException(
					"El login del usuario no puede tener más de 15 caracteres");
		}
		if (Validaciones.isTextoVacio(nombres)) {
			throw new NullPointerException(
					"Los nombres del usuario no pueden ser nulos o una cadena de caracteres vacia");
		}

		if (Validaciones.isTextoVacio(pws)) {
			throw new NullPointerException(
					"La contrasela del usuario no puede ser nulo o una cadena de caracteres vacia");
		}
		if (pws.length() > 25) {
			throw new NullPointerException(
					"La clave del usuario no puede tener más de 25 caracteres");
		}
		if (rol == null) {
			throw new NullPointerException(
					"El rol del usuario  no puede ser nulo o una cadena de caracteres vacia");
		}
		Rol objRol = rolService.obtener(rol);
		Usuario usuarioDatos = null;
		usuarioDatos = usuarioDAO.obtener(login);
		if (usuarioDatos != null) {
			throw new IWDaoException(
					"El usuario con el login ingresado ya existe en la base de datos.");
		}
		if (objRol == null) {
			throw new IWDaoException("El rol no existe en la base de datos");
		}
		Cifrar cifrar = new Cifrar();
		Usuario usuario = new Usuario();
		usuario.setLogin(login);
		usuario.setNombreCompleto(nombres);
		usuario.setClave(cifrar.encrypt(pws));
		usuario.setRol(objRol);
		usuario = usuarioDAO.guardar(usuario);
		return usuario;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
}