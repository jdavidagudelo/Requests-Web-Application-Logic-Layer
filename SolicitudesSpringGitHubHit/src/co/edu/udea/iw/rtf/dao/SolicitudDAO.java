package co.edu.udea.iw.rtf.dao;

import java.util.List;

import co.edu.udea.iw.rtf.dto.Solicitud;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Interfaz utilizada para acceder a la tabla de solicitudes de la base de
 * datos.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public interface SolicitudDAO {
	/**
	 * Este método permite obtener la lista de todas las solicitudes de la base
	 * de datos.
	 * 
	 * @return Lista de solicitudes dentro de la base de datos de solicitudes.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a la base de
	 *             datos.
	 * */
	public List<Solicitud> obtener() throws IWDaoException;

	/**
	 * Permite obtener la solicitud con el codigo ingresado como argumento.
	 * 
	 * @param codigo
	 *            el identificador único de la solicitud que se desea obtener de
	 *            la tabla de solicitudes.
	 * @return La solicitud con el identificador ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos.
	 * */
	public Solicitud obtener(Long codigo) throws IWDaoException;

	/**
	 * Permite guardar una solicitud en la base de datos.
	 * 
	 * @param solicitud
	 *            solicitud que contiene los campos requeridos para crear una
	 *            nueva solicitud en la tabla de solicitudes de la base de
	 *            datos.
	 * @return La solicitud ingresada como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos o
	 *             la solicitud ingresada sea invalida.
	 * */
	public Solicitud guardar(Solicitud solicitud) throws IWDaoException;

	/**
	 * Permite actualizar una solicitud que ha sido creada previamente.
	 * 
	 * @param solicitud
	 *            Contiene los datos para actualizar una solicitud existente en
	 *            la tabla de solicitudes de la base de datos.
	 * @return la solicitud que se ha actualizado.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a los datos
	 *             o se ingrese una solicitud invalida.
	 * */
	public Solicitud actualizar(Solicitud solicitud) throws IWDaoException;

	/**
	 * Permite eliminar una solicitud de la tabla de solicitudes de la base de
	 * datos.
	 * 
	 * @param solicitud
	 *            la solicitud que se desea eliminar de la base de datos.
	 * @throws IWDaoException
	 *             En caso de que se presente un error en el acceso a la base de
	 *             datos o la solicitud sea invalida.
	 * */
	public void eliminar(Solicitud solicitud) throws IWDaoException;

	/**
	 * Permite obtener el siguiente código válido para la creación de una
	 * solicitud en la base de datos.
	 * 
	 * @return Siguiente código disponible para una solicitud dentro de la base
	 *         de datos.
	 * @throws IWDaoException
	 *             En caso de presentarse error en el acceso a la base de datos.
	 * */
	public Long obtenerMaximoCodigo() throws IWDaoException;

}