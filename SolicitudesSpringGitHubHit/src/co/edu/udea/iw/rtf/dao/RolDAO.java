package co.edu.udea.iw.rtf.dao;

import java.util.List;
import co.edu.udea.iw.rtf.dto.Rol;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Interfaz utilizada para acceder a la tabla de roles de la base de datos.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public interface RolDAO {
	/**
	 * Este método permite obtener la lista de todos los roles de la base de
	 * datos.
	 * 
	 * @return Lista de roles dentro de la base de datos de roles.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a la base de
	 *             datos.
	 * */
	public List<Rol> obtener() throws IWDaoException;

	/**
	 * Permite obtener el rol con el código ingresado como argumento.
	 * 
	 * @param codigo
	 *            el codigo del rol que se desea obtener de la tabla de roles.
	 * @return el rol con el código ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos.
	 * */
	public Rol obtener(Long codigo) throws IWDaoException;

	/**
	 * Permite guardar un rol en la base de datos.
	 * 
	 * @param rol
	 *            rol que contiene los campos requeridos para crear un nuevo rol
	 *            en la tabla de rol de la base de datos.
	 * @return el rol ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos o
	 *             el rol ingresado sea invalido.
	 * */
	public Rol guardar(Rol rol) throws IWDaoException;

	/**
	 * Permite actualizar un rol que ha sido creado previamente.
	 * 
	 * @param rol
	 *            Contiene los datos para actualizar un rol existente en la
	 *            tabla de roles de la base de datos.
	 * @return el rol que se ha actualizado.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a los datos
	 *             o se ingrese un rol invalido.
	 * */
	public Rol actualizar(Rol rol) throws IWDaoException;

	/**
	 * Permite eliminar un rol de la tabla de roles de la base de datos.
	 * 
	 * @param rol
	 *            el rol que se desea eliminar de la base de datos.
	 * @throws IWDaoException
	 *             En caso de que se presente un error en el acceso a la base de
	 *             datos o el rol sea invalido.
	 * */
	public void eliminar(Rol rol) throws IWDaoException;

	/**
	 * Permite obtener el siguiente código válido para la creación de un rol en
	 * la base de datos.
	 * 
	 * @return Siguiente código disponible para una solicitud dentro de la base
	 *         de datos.
	 * @throws IWDaoException
	 *             En caso de presentarse error en el acceso a la base de datos.
	 * */
	public Long obtenerMaximoCodigo() throws IWDaoException;
}
