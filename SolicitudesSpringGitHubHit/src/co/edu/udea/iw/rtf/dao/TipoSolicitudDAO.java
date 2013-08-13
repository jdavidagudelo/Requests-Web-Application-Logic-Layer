package co.edu.udea.iw.rtf.dao;

import java.util.List;

import co.edu.udea.iw.rtf.dto.TipoSolicitud;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Interfaz utilizada para acceder a la tabla de tipos de solicitudes de la base
 * de datos.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public interface TipoSolicitudDAO {
	/**
	 * Este método permite obtener la lista de todos los tipos de solicitud de
	 * la base de datos.
	 * 
	 * @return Lista de tipos de solicitud dentro de la base de datos de tipos
	 *         de solicitudes.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a la base de
	 *             datos.
	 * */
	public List<TipoSolicitud> obtener() throws IWDaoException;

	/**
	 * Permite obtener el tipo de solicitud con el código ingresado como
	 * argumento.
	 * 
	 * @param codigo
	 *            el codigo del tipo de solicitud que se desea obtener de la
	 *            tabla de tipos de solicitudes.
	 * @return el tipo de solicitud con el código ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos.
	 * */
	public TipoSolicitud obtener(Long codigo) throws IWDaoException;

	/**
	 * Permite guardar un tipo de solicitud en la base de datos.
	 * 
	 * @param tipoSolicitud
	 *            tipo de solicitud que contiene los campos requeridos para
	 *            crear un nuevo tipo de solicitud en la tabla de tipos de
	 *            solicitudes de la base de datos.
	 * @return el tipo de solicitud ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos o
	 *             el tipo de solicitud ingresado sea invalido.
	 * */
	public TipoSolicitud guardar(TipoSolicitud tipoSolicitud)
			throws IWDaoException;

	/**
	 * Permite actualizar un tipo de solicitud que ha sido creado previamente.
	 * 
	 * @param tipoSolicitud
	 *            Contiene los datos para actualizar un tipo de solicitud
	 *            existente en la tabla de tipos de solicitud de la base de
	 *            datos.
	 * @return el tipo de solicitud que se ha actualizado.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a los datos
	 *             o se ingrese un tipo de solicitud invalido.
	 * */
	public TipoSolicitud actualizar(TipoSolicitud tipoSolicitud)
			throws IWDaoException;

	/**
	 * Permite eliminar un tipo de solicitud de la tabla de tipos de solicitudes
	 * de la base de datos.
	 * 
	 * @param tipoSolicitud
	 *            el tipo de solicitud que se desea eliminar de la base de
	 *            datos.
	 * @throws IWDaoException
	 *             En caso de que se presente un error en el acceso a la base de
	 *             datos o el tipo de solicitud sea invalido.
	 * */
	public void eliminar(TipoSolicitud tipoSolicitud) throws IWDaoException;

	/**
	 * Permite obtener el siguiente código válido para la creación de un tipo de
	 * solicitud en la base de datos.
	 * 
	 * @return Siguiente código disponible para una solicitud dentro de la base
	 *         de datos.
	 * @throws IWDaoException
	 *             En caso de presentarse error en el acceso a la base de datos.
	 * */
	public Long obtenerMaximoCodigo() throws IWDaoException;
}