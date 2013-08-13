package co.edu.udea.iw.rtf.dao;

import java.util.List;

import co.edu.udea.iw.rtf.dto.Producto;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Interfaz utilizada para acceder a la tabla de productos de la base de datos.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public interface ProductoDAO {
	/**
	 * Este método permite obtener la lista de todos los productos de la base de
	 * datos.
	 * 
	 * @return Lista de productos dentro de la base de datos de productos.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a la base de
	 *             datos.
	 * */
	public List<Producto> obtener() throws IWDaoException;

	/**
	 * Permite obtener el producto con el código ingresado como argumento.
	 * 
	 * @param codigo
	 *            el codigo del producto que se desea obtener de la tabla de
	 *            productos.
	 * @return el producto con el código ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos.
	 * */
	public Producto obtener(Long codigo) throws IWDaoException;

	/**
	 * Permite guardar un producto en la base de datos.
	 * 
	 * @param producto
	 *            producto que contiene los campos requeridos para crear un
	 *            nuevo producto en la tabla de productos de la base de datos.
	 * @return el producto ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos o
	 *             el producto ingresado sea invalido.
	 * */
	public Producto guardar(Producto producto) throws IWDaoException;

	/**
	 * Permite actualizar un producto que ha sido creado previamente.
	 * 
	 * @param producto
	 *            Contiene los datos para actualizar un producto existente en la
	 *            tabla de productos de la base de datos.
	 * @return el producto que se ha actualizado.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a los datos
	 *             o se ingrese un producto invalido.
	 * */
	public Producto actualizar(Producto producto) throws IWDaoException;

	/**
	 * Permite eliminar un producto de la tabla de productos de la base de
	 * datos.
	 * 
	 * @param producto
	 *            el producto que se desea eliminar de la base de datos.
	 * @throws IWDaoException
	 *             En caso de que se presente un error en el acceso a la base de
	 *             datos o el producto sea invalido.
	 * */
	public void eliminar(Producto producto) throws IWDaoException;

	/**
	 * Permite obtener el siguiente código válido para la creación de un
	 * producto en la base de datos.
	 * 
	 * @return Siguiente código disponible para una solicitud dentro de la base
	 *         de datos.
	 * @throws IWDaoException
	 *             En caso de presentarse error en el acceso a la base de datos.
	 * */
	public Long obtenerMaximoCodigo() throws IWDaoException;
}
