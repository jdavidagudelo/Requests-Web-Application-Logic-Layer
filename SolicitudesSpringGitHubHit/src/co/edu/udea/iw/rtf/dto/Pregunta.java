package co.edu.udea.iw.rtf.dto;

import java.util.Set;

/**
 * Esta clase representa una pregunta incluida en una encuesta realizada a un
 * cliente de la empresa para verificar su satisfacción con los servicios de la
 * empresa en cuanto a las respuestas a las solicitudes.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public class Pregunta {
	/**
	 * Identificador único de cada pregunta dentro de la tabla de preguntas.
	 * Este atributo es obligatorio.
	 * */
	private Long codigo;
	/**
	 * Texto de cada una de las preguntas de la encuesta realizada al cliente.
	 * Este atributo es obligatorio.
	 * */
	private String pregunta;
	/**
	 * Este atributo representa una lista de las respuestas asociadas con esta
	 * pregunta. Esta lista es llenada automaticamente por hibernate.
	 * */
	private Set<Respuesta> respuestas;

	/**
	 * @param codigo
	 *            El codigo utilizado como identificador único de la pregunta en
	 *            la base de datos.
	 * @param pregunta
	 *            el texto de la pregunta.
	 * */
	public Pregunta(Long codigo, String pregunta) {
		super();

		this.codigo = codigo;
		this.pregunta = pregunta;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public Set<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(Set<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	public Pregunta() {
		super();
	}

}
