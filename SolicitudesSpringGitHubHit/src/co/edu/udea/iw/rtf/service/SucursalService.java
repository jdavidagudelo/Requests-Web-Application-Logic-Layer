package co.edu.udea.iw.rtf.service;

import java.util.List;

import co.edu.udea.iw.rtf.dao.SucursalDAO;
import co.edu.udea.iw.rtf.dto.Sucursal;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;
import co.edu.udea.iw.rtf.util.validations.Validaciones;

/**
 * Esta clase es la que se encarga de manejar la lógica del negocio para las
 * sucursales especificadas en las solicitudes de los clientes.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class SucursalService {
	private SucursalDAO sucursalDAO;

	public SucursalDAO getSucursalDAO() {
		return sucursalDAO;
	}

	public List<Sucursal> obtener() throws IWDaoException {
		return sucursalDAO.obtener();
	}

	/**
	 * Permite obtener la sucursal con el codigo ingresado como argumento.
	 * 
	 * @param codigo
	 *            el codigo de la sucursal de la base de datos.
	 * @return la sucursal que se desea buscar en la base de datos.
	 * @throws IWDaoException
	 *             En caso de presentar error en el acceso a los datos.
	 * */
	public Sucursal obtener(Long codigo) throws IWDaoException {
		if (codigo == null) {
			throw new NullPointerException(
					"El codigo de la sucursal no puede ser nulo");
		}
		return sucursalDAO.obtener(codigo);
	}

	public void setSucursalDAO(SucursalDAO sucursalDAO) {
		this.sucursalDAO = sucursalDAO;
	}

	/**
	 * Método utilizado para crear una nueva sucursal en la base de datos. Este
	 * método genera automaticamente el código la nueva sucursal.
	 * 
	 * @param nombre
	 *            el nombre dela nueva sucursal.
	 * @param direccion
	 *            La direccion de la nueva sucursal.
	 * @param telefono
	 *            El telefono de la nueva sucursal.
	 * @return La nueva sucursal creada.
	 * */
	public Sucursal guardarSucursal(String nombre, String direccion,
			String telefono) throws IWDaoException {
		if (Validaciones.isTextoVacio(nombre)) {
			throw new NullPointerException(
					"El nombre de la sucursal no puede ser nulo o vacio");
		}
		Sucursal sucursal = new Sucursal();
		Long codigo = sucursalDAO.obtenerMaximoCodigo();
		codigo = codigo + 1;
		sucursal.setCodigo(codigo);
		sucursal.setNombre(nombre);
		sucursal.setDireccion(direccion);
		sucursal.setTelefono(telefono);
		sucursalDAO.guardar(sucursal);
		return sucursal;
	}
}