package co.edu.udea.iw.rtf.dao;

import java.util.List;
import co.edu.udea.iw.rtf.dto.Pregunta;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Interfaz utilizada para acceder a la tabla de preguntas de la base de datos.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public interface PreguntaDAO {
	/**
	 * Este método permite obtener la lista de todas las preguntas de la base de
	 * datos.
	 * 
	 * @return Lista de preguntas dentro de la base de datos de preguntas.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a la base de
	 *             datos.
	 * */
	public List<Pregunta> obtener() throws IWDaoException;

	/**
	 * Permite obtener la pregunta con el código ingresado como argumento.
	 * 
	 * @param codigo
	 *            el codigo de la pregunta que se desea obtener de la tabla de
	 *            Preguntas.
	 * @return La pregunta con el código ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos.
	 * */
	public Pregunta obtener(Long codigo) throws IWDaoException;

	/**
	 * Permite guardar una pregunta en la base de datos.
	 * 
	 * @param pregunta
	 *            Pregunta que contiene los campos requeridos para crear una
	 *            nueva pregunta en la tabla de preguntas de la base de datos.
	 * @return La Pregunta ingresada como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos o
	 *             la pregunta ingresada sea invalida.
	 * */
	public Pregunta guardar(Pregunta pregunta) throws IWDaoException;

	/**
	 * Permite actualizar una pregunta que ha sido creada previamente.
	 * 
	 * @param pregunta
	 *            Contiene los datos para actualizar una pregunta existente en
	 *            la tabla de preguntas de la base de datos.
	 * @return la pregunta que se ha actualizado.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a los datos
	 *             o se ingrese una pregunta invalida.
	 * */
	public Pregunta actualizar(Pregunta pregunta) throws IWDaoException;

	/**
	 * Permite eliminar una pregunta de la tabla de preguntas de la base de
	 * datos.
	 * 
	 * @param pregunta
	 *            la pregunta que se desea eliminar de la base de datos.
	 * @throws IWDaoException
	 *             En caso de que se presente un error en el acceso a la base de
	 *             datos o la pregunta sea invalida.
	 * */
	public void eliminar(Pregunta pregunta) throws IWDaoException;

	/**
	 * Permite obtener el siguiente código válido para la creación de una
	 * pregunta en la base de datos.
	 * 
	 * @return Siguiente código disponible para una solicitud dentro de la base
	 *         de datos.
	 * @throws IWDaoException
	 *             En caso de presentarse error en el acceso a la base de datos.
	 * */
	public Long obtenerMaximoCodigo() throws IWDaoException;
}
