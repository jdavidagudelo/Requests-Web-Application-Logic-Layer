package co.edu.udea.iw.rtf.service;

import java.util.List;

import co.edu.udea.iw.rtf.dao.ProductoDAO;
import co.edu.udea.iw.rtf.dto.Producto;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;
import co.edu.udea.iw.rtf.util.validations.Validaciones;

/**
 * Esta clase es la que se encarga de manejar la lógica del negocio para los
 * productos especificados en las solicitudes de los clientes.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class ProductoService {

	/**
	 * Este atributo será inyectado en este bean por Spring durante la ejecución
	 * del programa. Permite el acceso a la tabla de productos de la base de
	 * datos.
	 * */
	private ProductoDAO productoDAO;

	public ProductoDAO getProductoDAO() {
		return productoDAO;
	}

	public List<Producto> obtener() throws IWDaoException {
		return productoDAO.obtener();
	}

	/**
	 * Permite obtener el producto con el codigo ingresado como argumento.
	 * 
	 * @param codigo
	 *            el codigo del producto de la base de datos.
	 * @return el producto que se desea buscar en la base de datos.
	 * @throws IWDaoException
	 *             En caso de presentar error en el acceso a los datos.
	 * */
	public Producto obtener(Long codigo) throws IWDaoException {
		if (codigo == null) {
			throw new NullPointerException(
					"El codigo del producto no puede ser nulo");
		}
		return productoDAO.obtener(codigo);
	}

	public void setProductoDAO(ProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
	}

	/**
	 * Método utilizado para crear un nuevo producto en la base de datos. Este
	 * método genera automaticamente el código del nuevo producto.
	 * 
	 * @param nombre
	 *            el nombre del nuevo producto.
	 * @return El nuevo producto creado.
	 * */
	public Producto guardarProducto(String nombre) throws IWDaoException {
		if (Validaciones.isTextoVacio(nombre)) {
			throw new NullPointerException(
					"El nombre del producto no puede ser nulo o vacio");
		}
		Producto producto = new Producto();
		Long codigo = productoDAO.obtenerMaximoCodigo();
		codigo = codigo + 1;
		producto.setCodigo(codigo);
		producto.setNombre(nombre);
		productoDAO.guardar(producto);
		return producto;
	}
}
