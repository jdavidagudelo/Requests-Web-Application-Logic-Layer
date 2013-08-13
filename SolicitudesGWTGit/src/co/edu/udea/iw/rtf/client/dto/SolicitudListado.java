package co.edu.udea.iw.rtf.client.dto;

/**
 * @author Andr�s Vanegas L 
 * E-mail: anfevaloudea@gmail.com
 * @author Juan David Agudelo
 * E-mail: jdaaa2009@gmail.com
 */

import java.io.Serializable;
import java.util.Date;

/**
 * Información requerida para listar las solicitudes y asignarles un responsable
 * de responderlas.
 * */
public class SolicitudListado implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Concatenacion del nombre y apellidos del solicitante.
	 * */
	private String nombreCompletoSolicitante;
	/**
	 * Correo electronico del solicitante.
	 * */
	private String correoElectronico;
	/**
	 * Fecha de realización de la solicitud.
	 * */
	private Date fechaSolicitud;
	/**
	 * Nombre del tipo de solicitud.
	 * */
	private String nombreTipoSolicitud;
	/**
	 * Texto de la solicitud.
	 * */
	private String textoSolicitud;
	/**
	 * Nombre del producto.
	 * */
	private String nombreProducto;
	private String nombreSucursal;
	private String celular;
	private String telefono;
	private Long codigo;

	public String getNombreCompletoSolicitante() {
		return nombreCompletoSolicitante;
	}

	public void setNombreCompletoSolicitante(String nombreCompletoSolicitante) {
		this.nombreCompletoSolicitante = nombreCompletoSolicitante;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getNombreTipoSolicitud() {
		return nombreTipoSolicitud;
	}

	public void setNombreTipoSolicitud(String nombreTipoSolicitud) {
		this.nombreTipoSolicitud = nombreTipoSolicitud;
	}

	public String getTextoSolicitud() {
		return textoSolicitud;
	}

	public void setTextoSolicitud(String textoSolicitud) {
		this.textoSolicitud = textoSolicitud;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getNombreSucursal() {
		return nombreSucursal;
	}

	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	public SolicitudListado(String nombreCompletoSolicitante,
			String correoElectronico, Date fechaSolicitud,
			String nombreTipoSolicitud, String textoSolicitud,
			String nombreProducto, String nombreSucursal, String celular,
			String telefono, Long codigo) {
		super();
		this.nombreCompletoSolicitante = nombreCompletoSolicitante;
		this.correoElectronico = correoElectronico;
		this.fechaSolicitud = fechaSolicitud;
		this.nombreTipoSolicitud = nombreTipoSolicitud;
		this.textoSolicitud = textoSolicitud;
		this.nombreProducto = nombreProducto;
		this.nombreSucursal = nombreSucursal;
		this.celular = celular;
		this.telefono = telefono;
		this.codigo = codigo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SolicitudListado() {
		super();
	}

}