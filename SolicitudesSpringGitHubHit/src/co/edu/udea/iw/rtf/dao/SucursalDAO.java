package co.edu.udea.iw.rtf.dao;

import java.util.List;

import co.edu.udea.iw.rtf.dto.Sucursal;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Interfaz utilizada para acceder a la tabla de sucursales de la base de datos.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public interface SucursalDAO {
	/**
	 * Este método permite obtener la lista de todas las sucursales de la base
	 * de datos.
	 * 
	 * @return Lista de sucursales dentro de la base de datos de sucursales.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a la base de
	 *             datos.
	 * */
	public List<Sucursal> obtener() throws IWDaoException;

	/**
	 * Permite obtener la sucursal con el codigo ingresado como argumento.
	 * 
	 * @param codigo
	 *            el identificador único de la sucursal que se desea obtener de
	 *            la tabla de sucursales.
	 * @return La sucursal con el identificador ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos.
	 * */
	public Sucursal obtener(Long codigo) throws IWDaoException;

	/**
	 * Permite guardar una sucursal en la base de datos.
	 * 
	 * @param sucursal
	 *            sucursal que contiene los campos requeridos para crear una
	 *            nueva sucursal en la tabla de sucursales de la base de datos.
	 * @return La sucursal ingresada como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos o
	 *             la sucursal ingresada sea invalida.
	 * */
	public Sucursal guardar(Sucursal sucursal) throws IWDaoException;

	/**
	 * Permite actualizar una sucursal que ha sido creada previamente.
	 * 
	 * @param sucursal
	 *            Contiene los datos para actualizar una sucursal existente en
	 *            la tabla de sucursal de la base de datos.
	 * @return la sucursal que se ha actualizado.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a los datos
	 *             o se ingrese una sucursal invalida.
	 * */
	public Sucursal actualizar(Sucursal sucursal) throws IWDaoException;

	/**
	 * Permite eliminar una sucursal de la tabla de sucursales de la base de
	 * datos.
	 * 
	 * @param sucursal
	 *            la sucursal que se desea eliminar de la base de datos.
	 * @throws IWDaoException
	 *             En caso de que se presente un error en el acceso a la base de
	 *             datos o la sucursal sea invalida.
	 * */
	public void eliminar(Sucursal sucursal) throws IWDaoException;

	/**
	 * Permite obtener el siguiente código válido para la creación de una
	 * sucursal en la base de datos.
	 * 
	 * @return Siguiente código disponible para una solicitud dentro de la base
	 *         de datos.
	 * @throws IWDaoException
	 *             En caso de presentarse error en el acceso a la base de datos.
	 * */
	public Long obtenerMaximoCodigo() throws IWDaoException;
}