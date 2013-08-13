package co.edu.udea.iw.rtf.dao;

import java.util.List;

import co.edu.udea.iw.rtf.dto.Usuario;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Interfaz utilizada para acceder a la tabla de usuarios de la base de datos.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public interface UsuarioDAO {
	/**
	 * Este método permite obtener la lista de todos los usuarios de la base de
	 * datos.
	 * 
	 * @return Lista de usuarios dentro de la base de datos de usuarios.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a la base de
	 *             datos.
	 * */
	public List<Usuario> obtener() throws IWDaoException;

	/**
	 * Permite obtener el usuario con el login ingresado como argumento.
	 * 
	 * @param login
	 *            el login del usuario que se desea obtener de la tabla de
	 *            usuarios.
	 * @return el usuario con el login ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos.
	 * */
	public Usuario obtener(String login) throws IWDaoException;

	/**
	 * Permite guardar un usuario en la base de datos.
	 * 
	 * @param usuario
	 *            usuario que contiene los campos requeridos para crear un nuevo
	 *            usuario en la tabla de usuarios de la base de datos.
	 * @return el usuario ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos o
	 *             el usuario ingresado sea invalido.
	 * */
	public Usuario guardar(Usuario usuario) throws IWDaoException;

	/**
	 * Permite actualizar un usuario que ha sido creado previamente.
	 * 
	 * @param usuario
	 *            Contiene los datos para actualizar un usuario existente en la
	 *            tabla de usuarios de la base de datos.
	 * @return el usuario que se ha actualizado.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a los datos
	 *             o se ingrese un usuario invalido.
	 * */
	public Usuario actualizar(Usuario usuario) throws IWDaoException;

	/**
	 * Permite eliminar un usuario de la tabla de usuarios de la base de datos.
	 * 
	 * @param usuario
	 *            el usuario que se desea eliminar de la base de datos.
	 * @throws IWDaoException
	 *             En caso de que se presente un error en el acceso a la base de
	 *             datos o el usuario sea invalido.
	 * */
	public void eliminar(Usuario usuario) throws IWDaoException;

}
