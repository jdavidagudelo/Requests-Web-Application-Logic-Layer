package co.edu.udea.iw.rtf.service;

import java.util.List;

import co.edu.udea.iw.rtf.dao.RespuestaDAO;
import co.edu.udea.iw.rtf.dto.Pregunta;
import co.edu.udea.iw.rtf.dto.Respuesta;
import co.edu.udea.iw.rtf.dto.RespuestaID;
import co.edu.udea.iw.rtf.dto.Solicitud;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Esta clase es la que se encarga de manejar la lógica del negocio para las
 * respuestas de las encuestas asociadas con las solicitudes de los clientes.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class RespuestaService {

	/**
	 * Este atributo será inyectado en este bean por Spring durante la ejecución
	 * del programa. Permite el acceso a la tabla de respuestas de la base de
	 * datos.
	 * */
	private RespuestaDAO respuestaDAO;

	/**
	 * Este atributo será inyectado en este bean por Spring durante la ejecución
	 * del programa. Permite el acceso a la tabla de solicitudes de la base de
	 * datos.
	 * */
	private SolicitudService solicitudService;

	/**
	 * Este atributo será inyectado en este bean por Spring durante la ejecución
	 * del programa. Permite el acceso a la tabla de preguntas de la base de
	 * datos.
	 * */
	private PreguntaService preguntaService;

	public SolicitudService getSolicitudService() {
		return solicitudService;
	}

	public void setSolicitudService(SolicitudService solicitudService) {
		this.solicitudService = solicitudService;
	}

	public PreguntaService getPreguntaService() {
		return preguntaService;
	}

	public void setPreguntaService(PreguntaService preguntaService) {
		this.preguntaService = preguntaService;
	}

	public RespuestaDAO getRespuestaDAO() {
		return respuestaDAO;
	}

	public List<Respuesta> obtener() throws IWDaoException {
		return respuestaDAO.obtener();
	}

	/**
	 * Permite obtener la respuesta con el codigo ingresado como argumento.
	 * 
	 * @param codigo
	 *            el codigo de la respuesta de la base de datos.
	 * @return la respuesta que se desea buscar en la base de datos.
	 * @throws IWDaoException
	 *             En caso de presentar error en el acceso a los datos.
	 * */
	public Respuesta obtener(RespuestaID codigo) throws IWDaoException {
		if (codigo == null) {
			throw new NullPointerException(
					"El codigo de la respuesta no puede ser nulo");
		}
		return respuestaDAO.obtener(codigo);
	}

	public void setRespuestaDAO(RespuestaDAO respuestaDAO) {
		this.respuestaDAO = respuestaDAO;
	}

	/**
	 * Permite crear una nueva respuesta en la base de datos de respuestas.
	 * Además comprueba si la información de la nueva respuesta es correcta y si
	 * aún no existe en la base de datos.
	 * 
	 * @param respuesta
	 *            La respuesta a la pregunta que puede ser True o false. No
	 *            puede ser Nula.
	 * @param codigoSolicitud
	 *            El codigo de la solicitud que se encuentra en la base de
	 *            datos. No puede ser nulo y la solicitud debe existir en la
	 *            base de datos.
	 * @param codigoPregunta
	 *            El código de la pregunta que se responde. No puede ser nulo y
	 *            debe existir en la base de datos.
	 * @return La nueva pregunta creada.
	 * */
	public Respuesta guardarRespuesta(Boolean respuesta, Long codigoSolicitud,
			Long codigoPregunta) throws IWDaoException {
		if (respuesta == null) {
			throw new NullPointerException("La respuesta no puede ser nula.");
		}
		if (codigoSolicitud == null) {
			throw new NullPointerException("La solicitud no puede ser nula.");
		}
		if (codigoPregunta == null) {
			throw new NullPointerException("La pregunta no puede ser nula.");
		}
		Solicitud solicitud = solicitudService.obtener(codigoSolicitud);
		if (solicitud == null) {
			throw new IWDaoException(
					"La solicitud no existe en la base de datos.");
		}
		Pregunta pregunta = preguntaService.obtener(codigoPregunta);
		if (pregunta == null) {
			throw new IWDaoException(
					"La pregunta no existe en la base de datos.");
		}
		RespuestaID id = new RespuestaID();
		id.setPregunta(pregunta);
		id.setSolicitud(solicitud);
		Respuesta respuestaDatos = respuestaDAO.obtener(id);
		if (respuestaDatos != null) {
			throw new IWDaoException(
					"La respuesta ya existe en la base de datos.");
		}
		Respuesta respuestaNueva = new Respuesta();
		respuestaNueva.setRespuesta(respuesta);
		respuestaNueva.setRespuestaID(id);
		respuestaDAO.guardar(respuestaNueva);
		return respuestaNueva;
	}
}