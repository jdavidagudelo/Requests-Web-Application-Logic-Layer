package co.edu.udea.iw.rtf.dto;

import java.io.Serializable;

/**
 * Esta clase representa la clave compuesta utilizada para identificar una
 * respuesta a una encuesta dentro de la base de datos.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public class RespuestaID implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * La solicitud con la que se encuentra asociada está respuesta. Este
	 * atributo es obligatorio.
	 * */
	private Solicitud solicitud;
	/**
	 * La pregunta a la cual esta respuesta responde. Este atributo es
	 * obligatorio.
	 * */
	private Pregunta pregunta;

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	/**
	 * @param solicitud
	 *            La solicitud con la que se encuentra asociada está respuesta.
	 * @param pregunta
	 *            La pregunta a la cual esta respuesta responde
	 * */
	public RespuestaID(Solicitud solicitud, Pregunta pregunta) {
		super();
		this.solicitud = solicitud;
		this.pregunta = pregunta;
	}

	public RespuestaID() {
		super();
	}

}
