package co.edu.udea.iw.rtf.service;

import java.util.List;

import co.edu.udea.iw.rtf.dao.PreguntaDAO;
import co.edu.udea.iw.rtf.dto.Pregunta;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;
import co.edu.udea.iw.rtf.util.validations.Validaciones;

/**
 * Esta clase es la que se encarga de manejar la lógica del negocio para las
 * preguntas de las encuestas.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class PreguntaService {

	/**
	 * Este atributo será inyectado en este bean por Spring durante la ejecución
	 * del programa. Permite el acceso a la tabla de preguntas de la base de
	 * datos.
	 * */
	private PreguntaDAO preguntaDAO;

	public PreguntaDAO getPreguntaDAO() {
		return preguntaDAO;
	}

	/**
	 * Permite obtener la lista de todas las preguntas de la base de datos.
	 * 
	 * @return Las preguntas de la base de datos.
	 * */
	public List<Pregunta> obtener() throws IWDaoException {
		return preguntaDAO.obtener();
	}

	/**
	 * Permite obtener la pregunta con el codigo ingresado como argumento.
	 * 
	 * @param codigo
	 *            el codigo de la pregunta de la base de datos.
	 * @return la pregunta que se desea buscar en la base de datos.
	 * @throws IWDaoException
	 *             En caso de presentar error en el acceso a los datos.
	 * */
	public Pregunta obtener(Long codigo) throws IWDaoException {
		if (codigo == null) {
			throw new NullPointerException(
					"El codigo de la pregunta no puede ser nulo");
		}
		return preguntaDAO.obtener(codigo);
	}

	public void setPreguntaDAO(PreguntaDAO preguntaDAO) {
		this.preguntaDAO = preguntaDAO;
	}

	public PreguntaService(PreguntaDAO preguntaDAO) {
		super();
		this.preguntaDAO = preguntaDAO;
	}

	public PreguntaService() {
		super();
	}

	/**
	 * Método utilizado para crear una nueva pregunta en la base de datos de
	 * preguntas. Este método genera automaticamente el código de la nueva
	 * pregunta.
	 * 
	 * @param textoPregunta
	 *            El texto de la nueva pregunta.
	 * @return La pregunta creada.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a la base de
	 *             datos.
	 * */
	public Pregunta guardarPregunta(String textoPregunta) throws IWDaoException {
		if (Validaciones.isTextoVacio(textoPregunta)) {
			throw new NullPointerException(
					"El texto de la pregunta no puede ser nulo o vacio");
		}
		Pregunta pregunta = new Pregunta();
		Long codigo = preguntaDAO.obtenerMaximoCodigo();
		codigo = codigo + 1;
		pregunta.setCodigo(codigo);
		pregunta.setPregunta(textoPregunta);
		preguntaDAO.guardar(pregunta);
		return pregunta;
	}

}
