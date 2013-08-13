package co.edu.udea.iw.rtf.server;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import co.edu.udea.iw.rtf.client.dto.UsuarioSingleton;
import co.edu.udea.iw.rtf.service.UsuarioService;
/**
 * Servlet usado para iniciar un usuario como invitado.
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * */
public class GuestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		procesarLogin(request, response);

		String referer = request.getHeader("referer");

		response.sendRedirect(referer);
	}

	/**
	 * Metodo usado para inicializar los datos
	 * del usuario.
	 * */
	protected void procesarLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		UsuarioSingleton.getInstance().setLogin(null);
		sesion.setAttribute("UsuarioConectado", UsuarioSingleton.getInstance());

	}

	/**
	 * Obtiene el servicio de logica del negocio para
	 * el usuario.
	 * */
	public UsuarioService getUsuarioService() {

		ServletContext sc = getServletContext();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		return (UsuarioService) context.getBean("usuarioService");
	}

}
