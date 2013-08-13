package co.edu.udea.iw.rtf.dao;

import java.util.List;

import co.edu.udea.iw.rtf.dto.Respuesta;
import co.edu.udea.iw.rtf.dto.RespuestaID;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Interfaz utilizada para acceder a la tabla de respuestas de la base de datos.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public interface RespuestaDAO {
	/**
	 * Este método permite obtener la lista de todas las respuestas de la base
	 * de datos.
	 * 
	 * @return Lista de respuestas dentro de la base de datos de respuestas.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a la base de
	 *             datos.
	 * */
	public List<Respuesta> obtener() throws IWDaoException;

	/**
	 * Permite obtener la respuesta con el objeto del tipo RespuestaID ingresado
	 * como argumento.
	 * 
	 * @param respuestaID
	 *            el identificador único de la respuesta que se desea obtener de
	 *            la tabla de respuestas.
	 * @return La respuesta con el identificador ingresado como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos.
	 * */
	public Respuesta obtener(RespuestaID respuestaID) throws IWDaoException;

	/**
	 * Permite guardar una respuesta en la base de datos.
	 * 
	 * @param respuesta
	 *            respuesta que contiene los campos requeridos para crear una
	 *            nueva respuesta en la tabla de preguntas de la base de datos.
	 * @return La respuesta ingresada como argumento.
	 * @throws IWDaoException
	 *             En caso de que se presente error en el acceso a los datos o
	 *             la respuesta ingresada sea invalida.
	 * */
	public Respuesta guardar(Respuesta respuesta) throws IWDaoException;

	/**
	 * Permite actualizar una respuesta que ha sido creada previamente.
	 * 
	 * @param respuesta
	 *            Contiene los datos para actualizar una respuesta existente en
	 *            la tabla de respuestas de la base de datos.
	 * @return la respuesta que se ha actualizado.
	 * @throws IWDaoException
	 *             en caso de que se presente un error en el acceso a los datos
	 *             o se ingrese una respuesta invalida.
	 * */
	public Respuesta actualizar(Respuesta respuesta) throws IWDaoException;

	/**
	 * Permite eliminar una respuesta de la tabla de respuestas de la base de
	 * datos.
	 * 
	 * @param respuesta
	 *            la respuesta que se desea eliminar de la base de datos.
	 * @throws IWDaoException
	 *             En caso de que se presente un error en el acceso a la base de
	 *             datos o la respuesta sea invalida.
	 * */
	public void eliminar(Respuesta respuesta) throws IWDaoException;
}