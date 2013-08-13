package co.edu.udea.iw.rtf.client;

import java.util.List;

import co.edu.udea.iw.rtf.client.dto.ProductoListado;
import co.edu.udea.iw.rtf.client.dto.SolicitudCreado;
import co.edu.udea.iw.rtf.client.dto.SolicitudListado;
import co.edu.udea.iw.rtf.client.dto.SucursalListado;
import co.edu.udea.iw.rtf.client.dto.TipoSolicitudListado;
import co.edu.udea.iw.rtf.client.dto.UsuarioListado;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * interfaz asincrona asociada con los servicios de la capa de lógica del
 * negocio asociados con la aplicación web.
 * 
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * @author Andres Felipe Vanegas anfevaloudea@gmail.com
 * */
public interface SolicitudesServiceAsync {

	/**
	 * @see SolicitudesService#getSolicitudes()
	 * */
	void getSolicitudes(AsyncCallback<List<SolicitudListado>> callback);

	/**
	 * @see SolicitudesService#getProductos()
	 * */
	void getProductos(AsyncCallback<List<ProductoListado>> callback);

	/**
	 * @see SolicitudesService#getSucursales()
	 * */
	void getSucursales(AsyncCallback<List<SucursalListado>> callback);

	/**
	 * @see SolicitudesService#getTiposSolicitud()
	 * */
	void getTiposSolicitud(AsyncCallback<List<TipoSolicitudListado>> callback);

	/**
	 * @see SolicitudesService#guardarSolicitud(SolicitudCreado)
	 * */
	void guardarSolicitud(SolicitudCreado solicitudCreado,
			AsyncCallback<String> callback);

	/**
	 * @see SolicitudesService#testUser(String, String)
	 * */
	void testUser(String login, String clave, AsyncCallback<Boolean> callback);

	/**
	 * @see SolicitudesService#getSolicitudesAsignadas(String)
	 * */
	void getSolicitudesAsignadas(String loginUsuario,
			AsyncCallback<List<SolicitudListado>> callback);

	/**
	 * @see SolicitudesService#getUsuarios()
	 * */
	void getUsuarios(AsyncCallback<List<UsuarioListado>> callback);

	/**
	 * @see SolicitudesService#asignarSolicitud(Long, String)
	 * */
	void asignarSolicitud(Long codigoSolicitud, String loginUsuario,
			AsyncCallback<Void> callback);

	/**
	 * @see SolicitudesService#isAdministrador(String)
	 * */
	void isAdministrador(String login, AsyncCallback<Boolean> callback);

}
