package co.edu.udea.iw.rtf.dto;

/**
 * Esta clase representa una respuesta a una pregunta de la encuesta realizada a
 * un cliente de la empresa que ha realizado una solicitud.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class Respuesta {
	/**
	 * Respuesta a la pregunta, true significa si, false significa no. Este
	 * atributo es obligatorio.
	 * */
	private Boolean respuesta;
	/**
	 * Identificador compuesto de la respuesta encapsulado en otra clase. Este
	 * atributo es obligatorio.
	 * */
	private RespuestaID respuestaID;

	/**
	 * @param respuesta
	 *            true significa si, false significa no.
	 * @param respuestaID
	 *            identificador compuesto de una respuesta en la base de datos.
	 * */
	public Respuesta(Boolean respuesta, RespuestaID respuestaID) {
		super();
		this.respuesta = respuesta;
		this.respuestaID = respuestaID;
	}

	public RespuestaID getRespuestaID() {
		return respuestaID;
	}

	public void setRespuestaID(RespuestaID respuestaID) {
		this.respuestaID = respuestaID;
	}

	public Boolean getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Boolean respuesta) {
		this.respuesta = respuesta;
	}

	public Respuesta() {
		super();
	}

}
