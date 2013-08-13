package co.edu.udea.iw.rtf.client.dto;

/**
 * @author Andr�s Vanegas L 
 * E-mail: anfevaloudea@gmail.com
 * @author Juan David Agudelo
 * E-mail: jdaaa2009@gmail.com
 */

import java.io.Serializable;

/**
 * Clase utilizada para transferencia de la información de una nueva solicitud
 * que el cliente desea ingresar al sistema.
 * */
public class SolicitudCreado implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Nombres de la persona que realiza la solicitud.
	 * */
	private String nombres;
	/**
	 * Apellidos del solicitante.
	 * */
	private String apellidos;
	/**
	 * Correo electronico del solicitante.
	 * */
	private String correoElectronico;
	/**
	 * Confirmación del correo del solicitante.
	 * */
	private String confirmacionCorreo;
	/**
	 * Telefono del solicitante.
	 * */
	private String telefono;
	/**
	 * Celular del solicitante.
	 * */
	private String celular;
	/**
	 * Codigo de la sucursal en la que se compro el producto.
	 * */
	private Long sucursal;
	/**
	 * Codigo del producto por el que se realiza la solicitud.
	 * */
	private Long producto;
	/**
	 * Texto de la solicitud.
	 * */
	private String textoSolicitud;
	/**
	 * Código del tipo de solicitud.
	 * */
	private Long tipoSolicitud;
	/**
	 * Otro producto especificado.
	 * */
	private String otroProducto;

	public String getNombres() {
		return nombres;
	}

	public SolicitudCreado(String nombres, String apellidos,
			String correoElectronico, String confirmacionCorreo,
			String telefono, String celular, Long suscursal, Long producto,
			String textoSolicitud, Long tipoSolicitud, String otroProducto) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.correoElectronico = correoElectronico;
		this.confirmacionCorreo = confirmacionCorreo;
		this.telefono = telefono;
		this.celular = celular;
		this.sucursal = suscursal;
		this.producto = producto;
		this.textoSolicitud = textoSolicitud;
		this.tipoSolicitud = tipoSolicitud;
		this.otroProducto = otroProducto;
	}

	public Long getProducto() {
		return producto;
	}

	public void setProducto(Long producto) {
		this.producto = producto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getConfirmacionCorreo() {
		return confirmacionCorreo;
	}

	public void setConfirmacionCorreo(String confirmacionCorreo) {
		this.confirmacionCorreo = confirmacionCorreo;
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

	public Long getSucursal() {
		return sucursal;
	}

	public void setSucursal(Long suscursal) {
		this.sucursal = suscursal;
	}

	public String getTextoSolicitud() {
		return textoSolicitud;
	}

	public void setTextoSolicitud(String textoSolicitud) {
		this.textoSolicitud = textoSolicitud;
	}

	public Long getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(Long tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public String getOtroProducto() {
		return otroProducto;
	}

	public void setOtroProducto(String otroProducto) {
		this.otroProducto = otroProducto;
	}

	public SolicitudCreado() {
		super();
	}

}
