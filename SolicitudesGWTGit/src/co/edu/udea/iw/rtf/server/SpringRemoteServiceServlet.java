package co.edu.udea.iw.rtf.server;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import co.edu.udea.iw.rtf.service.ProductoService;
import co.edu.udea.iw.rtf.service.RolService;
import co.edu.udea.iw.rtf.service.SolicitudService;
import co.edu.udea.iw.rtf.service.SucursalService;
import co.edu.udea.iw.rtf.service.TipoSolicitudService;
import co.edu.udea.iw.rtf.service.UsuarioService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Clase que permite obtener los servicios de la capa de lógica del negocio
 * desde los servicios remotos utilizados por la capa de presentación.
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * */
public class SpringRemoteServiceServlet extends RemoteServiceServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo usado para obtener el contexto de la aplicación web.
	 * 
	 * @return El contexto de la aplicacion web usado para acceder a los
	 *         servicios de la capa de lógica del negocio.
	 * */
	public ApplicationContext getContext() {
		ServletContext sc = getServletContext();
		ApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		return applicationContext;
	}

	/**
	 * Permite obtener el servico de logica del negocio asociado a una
	 * solicitud.
	 * 
	 * @return Objeto que permite acceder a los servicios de la capa de la
	 *         lógica del negocio para una solicitud.
	 * */
	public SolicitudService getSolicitudService() {
		return (SolicitudService) this.getContext().getBean("solicitudService");
	}

	/**
	 * Permite obtener el servico de logica del negocio asociado a una sucursal.
	 * 
	 * @return Objeto que permite acceder a los servicios de la capa de la
	 *         lógica del negocio para una sucursal.
	 * */
	public SucursalService getSucursalService() {
		return (SucursalService) this.getContext().getBean("sucursalService");
	}

	/**
	 * Permite obtener el servico de logica del negocio asociado a un producto.
	 * 
	 * @return Objeto que permite acceder a los servicios de la capa de la
	 *         lógica del negocio para un producto.
	 * */
	public ProductoService getProductoService() {
		return (ProductoService) this.getContext().getBean("productoService");
	}

	/**
	 * Permite obtener el servico de logica del negocio asociado a un usuario.
	 * 
	 * @return Objeto que permite acceder a los servicios de la capa de la
	 *         lógica del negocio para un usuario.
	 * */
	public UsuarioService getUsuarioService() {
		return (UsuarioService) this.getContext().getBean("usuarioService");
	}

	/**
	 * Permite obtener el servico de logica del negocio asociado a un rol.
	 * 
	 * @return Objeto que permite acceder a los servicios de la capa de la
	 *         lógica del negocio para un rol.
	 * */
	public RolService getRolService() {
		return (RolService) this.getContext().getBean("rolService");
	}

	/**
	 * Permite obtener el servico de logica del negocio asociado a un tipo de
	 * solicitud.
	 * 
	 * @return Objeto que permite acceder a los servicios de la capa de la
	 *         lógica del negocio para un tipo de solicitud.
	 * */
	public TipoSolicitudService getTipoSolicitudService() {
		return (TipoSolicitudService) this.getContext().getBean(
				"tipoSolicitudService");
	}

}
