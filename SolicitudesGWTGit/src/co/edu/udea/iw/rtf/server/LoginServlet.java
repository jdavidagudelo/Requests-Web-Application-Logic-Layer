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
import co.edu.udea.iw.rtf.dto.Usuario;
import co.edu.udea.iw.rtf.service.UsuarioService;
/**
 * Servlet usado para loguear al usuario.
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * */
public class LoginServlet extends HttpServlet {

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
	 * Metodo llamado desde la pagina JSP.
	 * */
	protected void procesarLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userName = request.getParameter("usuario");

		String password = request.getParameter("pws");

		HttpSession sesion = request.getSession();

		try {
			if (getUsuarioService().validar(userName, password)) {

				Usuario usuario = getUsuarioService().obtener(userName);
				UsuarioSingleton.getInstance()
						.setIsAdministrador(
								getUsuarioService().isAdministrador(
										usuario.getLogin()));
				UsuarioSingleton.getInstance().setLogin(usuario.getLogin());
				sesion.setAttribute("IsAdministrador", UsuarioSingleton
						.getInstance().getIsAdministrador());
				sesion.setAttribute("UsuarioConectado",
						UsuarioSingleton.getInstance());
			} else {
				sesion.setAttribute("UsuarioConectado", null);
				sesion.setAttribute("DatosInvalidos",
						"Usuario o contrasenia no valido");
			}
		} catch (Exception e) {
			sesion.setAttribute("UsuarioConectado", null);
			sesion.setAttribute("DatosInvalidos",
					"Ocurrio un error validando los datos del usuario");
		}

	}

	/**
	 * Permite obtener el servicio del usuario.
	 * */
	public UsuarioService getUsuarioService() {

		ServletContext sc = getServletContext();
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(sc);
		return (UsuarioService) context.getBean("usuarioService");
	}

}
