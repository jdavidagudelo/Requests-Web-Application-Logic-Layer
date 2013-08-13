package co.edu.udea.iw.rtf.service;

import java.util.List;

import co.edu.udea.iw.rtf.dao.RolDAO;
import co.edu.udea.iw.rtf.dto.Rol;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;
import co.edu.udea.iw.rtf.util.validations.Validaciones;

/**
 * Esta clase es la que se encarga de manejar la lógica del negocio para los
 * roles de los usuarios del sistema.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class RolService {
	/**
	 * Este atributo será inyectado en este bean por Spring durante la ejecución
	 * del programa. Permite el acceso a la tabla de roles de la base de datos.
	 * */
	private RolDAO rolDAO;

	public RolDAO getRolDAO() {
		return rolDAO;
	}

	public List<Rol> obtener() throws IWDaoException {
		return rolDAO.obtener();
	}

	/**
	 * Permite obtener el rol con el codigo ingresado como argumento.
	 * 
	 * @param codigo
	 *            el codigo del rol de la base de datos.
	 * @return el rol que se desea buscar en la base de datos.
	 * @throws IWDaoException
	 *             En caso de presentar error en el acceso a los datos.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * */
	public Rol obtener(Long codigo) throws IWDaoException {
		if (codigo == null) {
			throw new NullPointerException(
					"El codigo del rol no puede ser nulo");
		}
		return rolDAO.obtener(codigo);
	}

	public void setRolDAO(RolDAO rolDAO) {
		this.rolDAO = rolDAO;
	}

	/**
	 * Método utilizado para crear un nuevo rol en la base de datos de roles.
	 * Este método genera automaticamente el código del nuevo rol.
	 * 
	 * @param nombre
	 *            el nombre del nuevo rol.
	 * @return El nuevo rol creado.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * */
	public Rol guardarRol(String nombre) throws IWDaoException {
		if (Validaciones.isTextoVacio(nombre)) {
			throw new NullPointerException(
					"El nombre del rol no puede ser nulo o vacio");
		}
		Rol rol = new Rol();
		Long codigo = rolDAO.obtenerMaximoCodigo();
		codigo = codigo + 1;
		rol.setCodigo(codigo);
		rol.setNombre(nombre);
		rolDAO.guardar(rol);
		return rol;
	}
}
