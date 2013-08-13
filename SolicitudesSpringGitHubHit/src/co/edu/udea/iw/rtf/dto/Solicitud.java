package co.edu.udea.iw.rtf.dto;

import java.util.Date;
import java.util.Set;

/**
 * Esta clase representa una solicitud realizada por un cliente a la empresa
 * para una Petición, queja reclamo o sugerencia.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * @version 1
 * */
public class Solicitud {
	/**
	 * Identificador único de la solicitud dentro de la tabla de solicitudes.
	 * Este atributo es obligatorio.
	 * */
	private Long codigo;
	/**
	 * Nombres del cliente que realiza la solicitud. Este atributo es
	 * obligatorio.
	 * */
	private String nombresSolicitante;
	/**
	 * Apellidos del cliente que realiza la solicitud. Este atributo es
	 * obligatorio.
	 * */
	private String apellidosSolicitante;
	/**
	 * Correo electronico del cliente que realiza la solicitud. Este atributo es
	 * obligatorio.
	 * */
	private String correoElectronico;
	/**
	 * Telefono del cliente que realiza la solicitud. Este atributo es
	 * obligatorio.
	 * */
	private String telefono;
	/**
	 * Otro producto introducido por el usuario en caso de tener otro. Este
	 * atributo es opcional.
	 * */
	private String cualOtroProducto;
	/**
	 * La fecha en la cual el cliente ha realizado una solicitud. Este atributo
	 * es obligatorio.
	 * */
	private Date fechaSolicitud;
	/**
	 * El texto que el cliente ha ingresado para la solicitud. Este atributo es
	 * obligatorio.
	 * */
	private String textoSolicitud;
	/**
	 * Indica si la solicitud ya fue asignada a un responsable de responderla.
	 * Este atributo es opcional.
	 * */
	private Boolean asignada;
	/**
	 * La fecha en la que el gerente de cuentas asigna la solicitud a un
	 * responsable de responderla. Este atributo es opcional.
	 * */
	private Date fechaAsignacion;
	/**
	 * Indica si la solicitud ya ha sido respondida por el responsable de
	 * responderla. Este atributo es opcional.
	 * */
	private Boolean respondida;
	/**
	 * La fecha en la que el responsable de responder la solicitud la ha
	 * respondido. Este atributo es opcional.
	 * */
	private Date fechaRespuesta;
	/**
	 * El texto de la respuesta a la solicitud introducido por el responsable de
	 * responder la solicitud. Este atributo es opcional.
	 * */
	private String textoRespuesta;
	/**
	 * La sucursal en la cual se realizó la solicitud. Es una clave foranea a la
	 * tabla de sucursales. Es un atributo obligatorio.
	 * */
	private Sucursal sucursal;
	/**
	 * El producto por el cual el cliente ha realizado la solicitud. Representa
	 * la clave foranea a la tabla de productos. Este atributo es opcional dado
	 * que es posible que el usuario ingrese otro producto que no se encuentre
	 * en la base de datos de productos.
	 * */
	private Producto producto;
	/**
	 * El tipo de solicitud. Puede ser una petición, una queja, un reclamo o una
	 * sugerencia. Este atributo representa la clave foranea a la tabla de tipos
	 * de solicitudes. Este atributo es opcional.
	 * */
	private TipoSolicitud tipoSolicitud;
	/**
	 * El usuario que tiene asignada la solicitud para encargarse de
	 * responderla. Representa una clave foranea a la tabla de usuarios. Este
	 * atributo es opcional.
	 * */
	private Usuario usuarioAsignada;
	/**
	 * El usuario que se encarga de responder una solicitud del usuario.
	 * Representa una clave foranea a la tabla de usuarios. Este atributo es
	 * opcional.
	 * */
	private Usuario usuarioResponde;
	/**
	 * Representa el número de celular del cliente que realiza la solicitud.
	 * Este atributo es opcional.
	 * */
	private String celular;
	/**
	 * Lista de las respuestas a las preguntas realizadas en la encuesta
	 * asociada con esta solicitud. Es llenado automaticamente por hibernate.
	 * */
	private Set<Respuesta> respuestas;

	public Set<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(Set<Respuesta> respuestas) {
		this.respuestas = respuestas;
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

	public String getNombresSolicitante() {
		return nombresSolicitante;
	}

	public void setNombresSolicitante(String nombresSolicitante) {
		this.nombresSolicitante = nombresSolicitante;
	}

	public String getApellidosSolicitante() {
		return apellidosSolicitante;
	}

	public void setApellidosSolicitante(String apellidosSolicitante) {
		this.apellidosSolicitante = apellidosSolicitante;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCualOtroProducto() {
		return cualOtroProducto;
	}

	public void setCualOtroProducto(String cualOtroProducto) {
		this.cualOtroProducto = cualOtroProducto;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getTextoSolicitud() {
		return textoSolicitud;
	}

	public void setTextoSolicitud(String textoSolicitud) {
		this.textoSolicitud = textoSolicitud;
	}

	public Boolean getAsignada() {
		return asignada;
	}

	public void setAsignada(Boolean asignada) {
		this.asignada = asignada;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Boolean getRespondida() {
		return respondida;
	}

	public void setRespondida(Boolean respondida) {
		this.respondida = respondida;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public String getTextoRespuesta() {
		return textoRespuesta;
	}

	public void setTextoRespuesta(String textoRespuesta) {
		this.textoRespuesta = textoRespuesta;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public TipoSolicitud getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public Usuario getUsuarioAsignada() {
		return usuarioAsignada;
	}

	public void setUsuarioAsignada(Usuario usuarioAsignada) {
		this.usuarioAsignada = usuarioAsignada;
	}

	public Usuario getUsuarioResponde() {
		return usuarioResponde;
	}

	public void setUsuarioResponde(Usuario usuarioResponde) {
		this.usuarioResponde = usuarioResponde;
	}

	public Solicitud(Long codigo, String nombresSolicitante,
			String apellidosSolicitante, String correoElectronico,
			String telefono, String cualOtroProducto, Date fechaSolicitud,
			String textoSolicitud, Boolean asignada, Date fechaAsignacion,
			Boolean respondida, Date fechaRespuesta, String textoRespuesta,
			Sucursal sucursal, Producto producto, TipoSolicitud tipoSolicitud,
			Usuario usuarioAsignada, Usuario usuarioResponde, String celular) {
		super();
		this.codigo = codigo;
		this.nombresSolicitante = nombresSolicitante;
		this.apellidosSolicitante = apellidosSolicitante;
		this.correoElectronico = correoElectronico;
		this.telefono = telefono;
		this.cualOtroProducto = cualOtroProducto;
		this.fechaSolicitud = fechaSolicitud;
		this.textoSolicitud = textoSolicitud;
		this.asignada = asignada;
		this.fechaAsignacion = fechaAsignacion;
		this.respondida = respondida;
		this.fechaRespuesta = fechaRespuesta;
		this.textoRespuesta = textoRespuesta;
		this.sucursal = sucursal;
		this.producto = producto;
		this.tipoSolicitud = tipoSolicitud;
		this.usuarioAsignada = usuarioAsignada;
		this.usuarioResponde = usuarioResponde;
		this.celular = celular;
	}

	public Solicitud(Long codigo, String nombresSolicitante,
			String apellidosSolicitante, String correoElectronico,
			String telefono, Date fechaSolicitud, String textoSolicitud,
			Sucursal sucursal, TipoSolicitud tipoSolicitud) {
		super();
		this.codigo = codigo;
		this.nombresSolicitante = nombresSolicitante;
		this.apellidosSolicitante = apellidosSolicitante;
		this.correoElectronico = correoElectronico;
		this.telefono = telefono;
		this.fechaSolicitud = fechaSolicitud;
		this.textoSolicitud = textoSolicitud;
		this.sucursal = sucursal;
		this.tipoSolicitud = tipoSolicitud;
	}

	public Solicitud() {
		super();
	}

}
