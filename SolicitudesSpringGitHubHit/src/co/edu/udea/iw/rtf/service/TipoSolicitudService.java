package co.edu.udea.iw.rtf.service;

import java.util.List;

import co.edu.udea.iw.rtf.dao.TipoSolicitudDAO;
import co.edu.udea.iw.rtf.dto.TipoSolicitud;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;
import co.edu.udea.iw.rtf.util.validations.Validaciones;

/**
 * Esta clase es la que se encarga de manejar la lógica del negocio para los
 * tipos de solicitud especificados en las solicitudes de los clientes.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class TipoSolicitudService {
	private TipoSolicitudDAO tipoSolicitudDAO;

	public TipoSolicitudDAO getTipoSolicitudDAO() {
		return tipoSolicitudDAO;
	}

	public List<TipoSolicitud> obtener() throws IWDaoException {
		return tipoSolicitudDAO.obtener();
	}

	public void setTipoSolicitudDAO(TipoSolicitudDAO tipoSolicitudDAO) {
		this.tipoSolicitudDAO = tipoSolicitudDAO;
	}

	/**
	 * Permite obtener el tipo de solicitud con el codigo ingresado como
	 * argumento.
	 * 
	 * @param codigo
	 *            el codigo del tipo de solicitud de la base de datos.
	 * @return el tipo de solicitud que se desea buscar en la base de datos.
	 * @throws IWDaoException
	 *             En caso de presentar error en el acceso a los datos.
	 * */
	public TipoSolicitud obtener(Long codigo) throws IWDaoException {
		if (codigo == null) {
			throw new NullPointerException(
					"El codigo del tipo de solicitud no puede ser nulo");
		}
		return tipoSolicitudDAO.obtener(codigo);
	}

	/**
	 * Método utilizado para crear un nuevo tipo de solicitud en la base de
	 * datos. Este método genera automaticamente el código del nuevo tipo de
	 * solicitud.
	 * 
	 * @param nombre
	 *            el nombre del nuevo tipo de solicitud.
	 * @return El nuevo tipo de solicitud creado.
	 * */
	public TipoSolicitud guardarTipoSolicitud(String nombre)
			throws IWDaoException {
		if (Validaciones.isTextoVacio(nombre)) {
			throw new NullPointerException(
					"El nombre del tipo de solicitud no puede ser nulo o vacio");
		}
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		Long codigo = tipoSolicitudDAO.obtenerMaximoCodigo();
		codigo = codigo + 1;
		tipoSolicitud.setCodigo(codigo);
		tipoSolicitud.setNombre(nombre);
		tipoSolicitudDAO.guardar(tipoSolicitud);
		return tipoSolicitud;
	}
}