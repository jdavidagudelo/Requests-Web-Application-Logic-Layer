package co.edu.udea.iw.rtf.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import co.edu.udea.iw.rtf.dao.SolicitudDAO;
import co.edu.udea.iw.rtf.dto.Producto;
import co.edu.udea.iw.rtf.dto.Solicitud;
import co.edu.udea.iw.rtf.dto.Sucursal;
import co.edu.udea.iw.rtf.dto.TipoSolicitud;
import co.edu.udea.iw.rtf.dto.Usuario;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;
import co.edu.udea.iw.rtf.util.mail.Notificador;
import co.edu.udea.iw.rtf.util.validations.Validaciones;

/**
 * En esta clase se maneja la lÃ³gica del negocio para las solicitudes realizadas
 * por un cliente de la empresa.
 * 
 * @author Juan David Agudelo Ã�lvarez jdaaa2009@gmail.com
 * */
public class SolicitudService {
	/**
	 * Este atributo serÃ¡ inyectado en este bean por Spring durante la ejecuciÃ³n
	 * del programa. Permite el acceso a la tabla de solicitudes de la base de
	 * datos.
	 * */
	private SolicitudDAO solicitudDAO;
	/**
	 * Este atributo serÃ¡ inyectado en este bean por Spring durante la ejecuciÃ³n
	 * del programa. Permite el acceso a la tabla de sucursales de la base de
	 * datos.
	 * */
	private SucursalService sucursalService;
	/**
	 * Este atributo serÃ¡ inyectado en este bean por Spring durante la ejecuciÃ³n
	 * del programa. Permite el acceso a la tabla de productos de la base de
	 * datos.
	 * */
	private ProductoService productoService;
	/**
	 * Este atributo serÃ¡ inyectado en este bean por Spring durante la ejecuciÃ³n
	 * del programa. Permite el acceso a la tabla de tipos de solicitudes de la
	 * base de datos.
	 * */
	private TipoSolicitudService tipoSolicitudService;
	/**
	 * Este atributo serÃ¡ inyectado en este bean por Spring durante la ejecuciÃ³n
	 * del programa. Permite el acceso a la tabla de usuarios de la base de
	 * datos.
	 * */
	private UsuarioService usuarioService;

	/**
	 * Permite confirmar que los dos correos electrÃ³nicos ingresados por el
	 * usuario sean el mismo.
	 * 
	 * @param correo1
	 *            un correo electrÃ³nico original.
	 * @param correo2
	 *            El correo electrÃ³nico que se desea comparar.
	 * @return true si el correo electrÃ³nico de confirmaciÃ³n es igual al correo
	 *         electrÃ³nico original, false en caso contrario.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * */
	public Boolean confirmarCorreoElectronico(String correo1, String correo2) {
		return Validaciones.confirmarCorreoElectronico(correo1, correo2);
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	/**
	 * Este mÃ©todo permite responder una solicitud.
	 * 
	 * @param codigoSolicitud
	 *            El codigo de la solicitud que se desea responder. No puede ser
	 *            nulo y debe existir la solicitud en la base de datos.
	 * @param loginUsuarioResponde
	 *            El login del usuario que responde la solicitud. No puede ser
	 *            nulo y el usuario debe existir en la base de datos.
	 * @param respuesta
	 *            La respuesta a la solicitud ingresada por el usuario.
	 * @return La solicitud actualizada.
	 * @throws IWDaoException
	 *             Error en el acceso a la base de datos.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * */
	public Solicitud responderSolicitud(Long codigoSolicitud,
			String loginUsuarioResponde, String respuesta)
			throws IWDaoException {
		if (codigoSolicitud == null) {
			throw new NullPointerException(
					"El codigo de la solicitud no puede ser nulo.");
		}
		if (Validaciones.isTextoVacio(loginUsuarioResponde)) {
			throw new NullPointerException(
					"El login del usuario que responde la solicitud no puede ser nulo o vacio.");
		}
		if (Validaciones.isTextoVacio(respuesta)) {
			throw new NullPointerException(
					"La respuesta de la solicitud no puede ser nula o vacia.");
		}
		Solicitud solicitud = solicitudDAO.obtener(codigoSolicitud);
		if (solicitud == null) {
			throw new IWDaoException(
					"La solicitud no existe en la base de datos.");
		}
		Usuario usuario = usuarioService.obtener(loginUsuarioResponde);
		if (usuario == null) {
			throw new IWDaoException(
					"El usuario no existe en la base de datos.");
		}
		ResourceBundle configuracion = ResourceBundle
				.getBundle("co.edu.udea.iw.rtf.util.properties.configuracion");
		Calendar calendar = Calendar.getInstance();
		solicitud.setFechaRespuesta(calendar.getTime());
		solicitud.setUsuarioResponde(usuario);
		solicitud.setRespondida(Boolean.TRUE);
		solicitud.setTextoRespuesta(respuesta);
		this.enviarCorreo(codigoSolicitud,
				configuracion.getString("default.subject"), respuesta);
		solicitud = solicitudDAO.actualizar(solicitud);
		return solicitud;
	}

	/**
	 * Este mÃ©todo permite enviar un correo electronico al cliente que realizÃ³
	 * una solicitud.
	 * 
	 * @param codigoSolicitud
	 *            El codigo de la solicitud realizada por el cliente. No puede
	 *            ser nulo.
	 * @param asunto
	 *            El asunto del correo. No puede ser nulo o vacio.
	 * @param mensaje
	 *            El mensaje del correo. No puede ser nulo op vacio.
	 * @throws IWDaoException
	 *             En caso de presentar error en el acceso a la base de datos.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * */
	public void enviarCorreo(Long codigoSolicitud, String asunto, String mensaje)
			throws IWDaoException {

		if (codigoSolicitud == null) {
			throw new NullPointerException(
					"El codigo de la solicitud no puede ser nulo.");
		}
		if (Validaciones.isTextoVacio(asunto)) {
			throw new NullPointerException(
					"El asunto del correo no puede ser nulo o una cadena de caracteres vacia");
		}
		if (Validaciones.isTextoVacio(mensaje)) {
			throw new NullPointerException(
					"El mensaje del correio no puede ser nulo o una cadena de caracteres vacia");
		}
		Solicitud solicitud = solicitudDAO.obtener(codigoSolicitud);

		if (solicitud == null) {
			throw new IWDaoException("La solicitud no existe en el sistema.");
		}
		// inicia el hilo que ejecuta el procedimiento para enviar el email.
		Notificador notificador = new Notificador(
				solicitud.getCorreoElectronico(), asunto, mensaje);
		notificador.start();
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public TipoSolicitudService getTipoSolicitudService() {
		return tipoSolicitudService;
	}

	public void setTipoSolicitudService(
			TipoSolicitudService tipoSolicitudService) {
		this.tipoSolicitudService = tipoSolicitudService;
	}

	public ProductoService getProductoService() {
		return productoService;
	}

	public void setProductoService(ProductoService productoService) {
		this.productoService = productoService;
	}

	public SucursalService getSucursalService() {
		return sucursalService;
	}

	public void setSucursalService(SucursalService sucursalService) {
		this.sucursalService = sucursalService;
	}

	public SolicitudDAO getSolicitudDAO() {
		return solicitudDAO;
	}

	public List<Solicitud> getSolicitudes(Long codigoSucursal) throws IWDaoException
	{
		Sucursal sucursal = sucursalService.obtener(codigoSucursal);
		List<Solicitud> solicitudes = new ArrayList<Solicitud>();
		for(Solicitud solicitud : sucursal.getSolicitudes())
		{
			solicitudes.add(solicitud);
		}
		return solicitudes;
	}
	
	/**
	 * Este mÃ©todo permite obtener las solicitudes que le han sido asignadas a
	 * un usuario del sistema.
	 * 
	 * @param loginUsuario
	 *            El identificador Ãºnico del usuario para el que se desean
	 *            conocer las solicitudes de las cuales se debe encargar.
	 * @return Lista de solicitudes que le han sido asignadas a un usuario del
	 *         sistema.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * */
	public Set<Solicitud> obtenerSolicitudesAsignadas(String loginUsuario)
			throws IWDaoException {
		if (Validaciones.isTextoVacio(loginUsuario)) {
			throw new NullPointerException(
					"El login del usuario no puede ser nulo o vacio.");
		}
		Usuario usuario = usuarioService.obtener(loginUsuario);
		if (usuario == null) {
			throw new IWDaoException(
					"El usuario ingresado no existe en la base de datos ");
		}
		return usuario.getSolicitudesUsuarioAsignada();
	}

	/**
	 * Metodo que permite obtener las solicitudes asignadas a un usuario que aun
	 * no han sido respondidas.
	 * 
	 * @param loginUsuario
	 *            El identificador de los usuarios del sistema.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * @throws IWDaoException
	 *             En caso de error en el acceso a los datos de la base de
	 *             datos.
	 * */
	public List<Solicitud> obtenerSolicitudesAsignadasPendientes(
			String loginUsuario) throws IWDaoException {
		if (Validaciones.isTextoVacio(loginUsuario)) {
			throw new NullPointerException(
					"El login del usuario no puede ser nulo o vacio.");
		}
		Usuario usuario = usuarioService.obtener(loginUsuario);
		if (usuario == null) {
			throw new IWDaoException(
					"El usuario ingresado no existe en la base de datos ");
		}
		List<Solicitud> solicitudes = new ArrayList<Solicitud>();
		Set<Solicitud> solicitudesAsignadas = usuario
				.getSolicitudesUsuarioAsignada();
		for (Solicitud solicitud : solicitudesAsignadas) {
			if (solicitud.getRespondida() == null || !solicitud.getRespondida()) {
				solicitudes.add(solicitud);
			}
		}
		return solicitudes;
	}

	/**
	 * Permite obtener la solicitud con el codigo ingresado como argumento.
	 * 
	 * @param codigo
	 *            el codigo de la solicitud de la base de datos.
	 * @return la solicitud que se desea buscar en la base de datos.
	 * @throws IWDaoException
	 *             En caso de presentar error en el acceso a los datos.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * */
	public Solicitud obtener(Long codigo) throws IWDaoException {
		if (codigo == null) {
			throw new NullPointerException(
					"El codigo de la solicitud no puede ser nulo");
		}
		return solicitudDAO.obtener(codigo);
	}

	/**
	 * Este mÃ©todo permite obtener las solicitudes pendientes por asignar.
	 * 
	 * @return Lista de las solicitudes pendientes por asignar.
	 * @throws IWDaoException
	 *             En caso de error en el acceso a los datos.
	 * */
	public List<Solicitud> obtenerSolicitudesPendientes() throws IWDaoException {
		List<Solicitud> solicitudesPendientes = new ArrayList<Solicitud>();
		List<Solicitud> solicitudes = this.obtener();
		for (Solicitud solicitud : solicitudes) {
			if (solicitud.getAsignada() == null || !solicitud.getAsignada()) {
				solicitudesPendientes.add(solicitud);
			}
		}
		return solicitudesPendientes;
	}

	/**
	 * Permite obtener todas las solicitudes de la base de datos.
	 * 
	 * @return La lista de todas las solicitudes de la base de datos.
	 * @throws IWDaoException
	 *             En caso de error en el acceso a la base de datos.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * */
	public List<Solicitud> obtener() throws IWDaoException {
		return solicitudDAO.obtener();
	}

	public void setSolicitudDAO(SolicitudDAO solicitudDAO) {
		this.solicitudDAO = solicitudDAO;
	}

	/**
	 * MÃ©todo que permite asignar una solicitud a un usuario del sistema. AdemÃ¡s
	 * automaticamente establece la solicitud como asignada y establece la fecha
	 * de asignaciÃ³n a la fecha actual.
	 * 
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * @param codigoSolicitud
	 *            El codigo de la solicitud que se desea asignar a un usuario.
	 *            No debe ser nulo y debe existir en la base de datos.
	 * @param loginUsuario
	 *            El login del usuario al cual se le desea asignar la solicitud.
	 *            No debe ser nulo y debe existir en la base de datos.
	 * @return La solicitud actualizada.
	 * @author Juan David Agudelo Ã�lvarez jdaaa2009@gmail.com
	 * @throws IWDaoException
	 *             En caso de que alguno de los parametros no exista en la base
	 *             de datos.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * */
	public Solicitud asignarSolicitud(Long codigoSolicitud, String loginUsuario)
			throws IWDaoException {
		if (codigoSolicitud == null) {
			throw new NullPointerException(
					"El cÃ³digo de la solicitud no puede ser nulo.");
		}
		Solicitud solicitud = solicitudDAO.obtener(codigoSolicitud);
		if (solicitud == null) {
			throw new IWDaoException(
					"La solicitud no se encuentra en la base de datos.");
		}
		if (loginUsuario == null) {
			throw new NullPointerException(
					"El login del usuario asignado no puede ser nulo.");
		}
		Usuario usuarioAsignado = usuarioService.obtener(loginUsuario);
		if (usuarioAsignado == null) {
			throw new IWDaoException(
					"El usuario asignado no se encuentra en la base de datos.");
		}
		solicitud.setAsignada(Boolean.TRUE);
		solicitud.setUsuarioAsignada(usuarioAsignado);
		Calendar calendar = Calendar.getInstance();
		solicitud.setFechaAsignacion(calendar.getTime());
		solicitud = solicitudDAO.actualizar(solicitud);
		return solicitud;
	}

	/**
	 * MÃ©todo utilizado para insertar una solicitud vÃ¡lida en la base de datos.
	 * Recibe como parÃ¡metro la informaciÃ³n ingresada por el cliente para crear
	 * la solicitud. AdemÃ¡s dentro del mÃ©todo se genera automaticamente el
	 * cÃ³digo Ãºnico de la solicitud y se establece la fecha actual como la fecha
	 * de realizaciÃ³n de la solicitud.
	 * 
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * @param nombres
	 *            Los nombres del cliente que realiza la solicitud. No puede ser
	 *            nulo o vacio.
	 * @param apellidos
	 *            Los apellidos del cliente que realiza la solicitud. No puede
	 *            ser nulo o vacio.
	 * @param correoElectronico
	 *            el correo electronico del cliente que realiza la solicitud. No
	 *            puede ser nulo o vacio y debe contener una @ para ser vÃ¡lido.
	 * @param confirmacionCorreo
	 *            Confirmacion del correo electrÃ³nico del cliente, debe ser
	 *            igual al correo electronico del cliente.
	 * @param telefono
	 *            El telefono del cliente que realiza la solicitud. No puede ser
	 *            nulo o vacio.
	 * @param celular
	 *            el celular del cliente que realiza la solicitud. Puede ser
	 *            nulo o vacio.
	 * @param sucursal
	 *            Codigo de la sucursal en la que el cliente comprÃ³ el producto.
	 *            No puede ser nulo y debe existir en la base de datos.
	 * @param producto
	 *            Codigo del producto por el cual se realiza la solicitud. Solo
	 *            puede ser nulo o no existir en la base de datos si el
	 *            parametro otroProducto no es nulo ni vacio.
	 * @param textoSolicitud
	 *            El texto de la solicitud realizada por el cliente. No puede se
	 *            nulo o vacio.
	 * @param tipoSolicitud
	 *            CÃ³digo del tipo de solicitud. No puede ser nulo y debe existir
	 *            en la base de datos.
	 * @param otroProducto
	 *            El otro producto especificado por el cliente. Solo puede ser
	 *            nulo o vacio si el parametro producto no es nulo y existe en
	 *            la base de datos de productos.
	 * @return La nueva solicitud creada en la base de datos.
	 * @throws IWDaoException
	 *             En caso de presentarse error en el acceso a los datos.
	 * @throws NullPointerException
	 *             En caso de que se presente algÃºn error en los datos recibidos
	 *             por el mÃ©todo.
	 * @author Juan David Agudelo Alvarez jdaaa2009@gmail.com
	 * */
	public Solicitud guardarSolicitud(String nombres, String apellidos,
			String correoElectronico, String confirmacionCorreo,
			String telefono, String celular, Long sucursal, Long producto,
			String textoSolicitud, Long tipoSolicitud, String otroProducto)
			throws IWDaoException {

		if (Validaciones.isTextoVacio(nombres)) {
			throw new NullPointerException(
					"Los nombres del cliente no pueden ser nulos o vacios.");
		}
		if (nombres.length() > 45) {
			throw new NullPointerException(
					"El nombre del cliente no puede tener mÃ¡s de 45 caracteres.");
		}
		if (Validaciones.isTextoVacio(apellidos)) {
			throw new NullPointerException(
					"Los apellidos del cliente no pueden ser nulos o vacios.");
		}
		if (apellidos.length() > 45) {
			throw new NullPointerException(
					"El apellido del cliente no puede tener mÃ¡s de 45 caracteres.");
		}
		if (!Validaciones.isEmail(correoElectronico)) {
			throw new NullPointerException(
					"El correo electrÃ³nico del cliente es invalido.");
		}
		if (correoElectronico.length() > 120) {
			throw new NullPointerException(
					"El correo del cliente no puede tener mÃ¡s de 120 caracteres.");
		}
		if (!this.confirmarCorreoElectronico(correoElectronico,
				confirmacionCorreo)) {
			throw new NullPointerException(
					"El correo electronico de confirmaciÃ³n no coincide con el original.");
		}
		if (Validaciones.isTextoVacio(telefono)) {
			throw new NullPointerException(
					"El telefono del cliente no pueden ser nulo o vacio.");
		}

		if (telefono.length() > 15) {
			throw new NullPointerException(
					"El telefono del cliente no puede tener mÃ¡s de 15 caracteres.");
		}
		if (sucursal == null) {
			throw new NullPointerException(
					"La sucursal de la solicitud no pueden ser nula.");
		}
		Sucursal sucursalObject = sucursalService.obtener(sucursal);
		if (sucursalObject == null) {
			throw new NullPointerException(
					"La sucursal especificada no existe en la base de datos.");
		}
		if (producto == null ) {
			throw new NullPointerException(
					"El producto especificado en la solicitud no puede se nulo.");
		}
		Producto productoObject = null;
		if (producto != null) {
			productoObject = productoService.obtener(producto);
			if (productoObject == null) {
				throw new NullPointerException(
						"El producto especificado no existe en la base de datos");
			}
		}
		if (Validaciones.isTextoVacio(textoSolicitud)) {
			throw new NullPointerException(
					"El texto de la solicitud no pueden ser nulo o vacio.");
		}
		if (tipoSolicitud == null) {
			throw new NullPointerException(
					"El tipo de la solicitud no pueden ser nulo.");
		}
		TipoSolicitud tipoSolicitudObject = tipoSolicitudService
				.obtener(tipoSolicitud);
		if (tipoSolicitudObject == null) {
			throw new NullPointerException(
					"El tipo de solicitud especificado no existe en la base de datos.");
		}
		if (celular != null & celular.length() > 15) {
			throw new NullPointerException(
					"El celular del cliente no puede tener mÃ¡s de 15 caracteres.");
		}
		Long codigo = solicitudDAO.obtenerMaximoCodigo();
		codigo = codigo + 1;
		Solicitud solicitud = new Solicitud();
		solicitud.setAsignada(Boolean.FALSE);
		solicitud.setRespondida(Boolean.FALSE);
		solicitud.setNombresSolicitante(nombres);
		solicitud.setApellidosSolicitante(apellidos);
		solicitud.setCorreoElectronico(correoElectronico);
		solicitud.setTelefono(telefono);
		solicitud.setCelular(celular);
		solicitud.setSucursal(sucursalObject);
		solicitud.setProducto(productoObject);
		solicitud.setTextoSolicitud(textoSolicitud);
		solicitud.setTipoSolicitud(tipoSolicitudObject);
		solicitud.setCodigo(codigo);
		Calendar calendar = Calendar.getInstance();
		// fecha de la solicitud
		solicitud.setFechaSolicitud(calendar.getTime());
		solicitudDAO.guardar(solicitud);
		return solicitud;
	}


}