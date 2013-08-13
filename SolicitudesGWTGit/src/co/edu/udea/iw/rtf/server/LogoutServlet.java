package co.edu.udea.iw.rtf.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.edu.udea.iw.rtf.client.dto.UsuarioSingleton;
/**
 * Servlet usado para cerrar la sesion del usuario.
 * @author Juan David Agudelo jdaaa2009@gmail.com
 * */
public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		session.setAttribute("UsuarioConectado", null);
		session.setAttribute("isAdministrador", null);
		UsuarioSingleton.clearInstance();
		String referrer = req.getHeader("referer");
		resp.sendRedirect(referrer);
	}

}
