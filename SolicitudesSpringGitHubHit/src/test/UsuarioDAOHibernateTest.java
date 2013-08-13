package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dao.RolDAO;
import co.edu.udea.iw.rtf.dao.UsuarioDAO;
import co.edu.udea.iw.rtf.dto.Rol;
import co.edu.udea.iw.rtf.dto.Usuario;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class UsuarioDAOHibernateTest {

	@Autowired
	UsuarioDAO usuarioDAO;

	@Test
	public void testObtener() {
		System.out.println("Prueba obtener usuarios.");
		try {
			List<Usuario> lista = usuarioDAO.obtener();
			for (Usuario usuario : lista) {
				System.out.println(usuario.getLogin() + ": "
						+ usuario.getNombreCompleto() + ": "
						+ usuario.getRol().getNombre());
			}
		} catch (IWDaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testObtenerLong() {
		try {
			System.out.println("Prueba obtener usuario.");
			Usuario usuario = usuarioDAO.obtener("juan");
			System.out.println(usuario.getLogin() + ": "
					+ usuario.getNombreCompleto() + ": "
					+ usuario.getRol().getNombre());

		} catch (IWDaoException e) {
			e.printStackTrace();
		}

	}

	@Autowired
	private RolDAO rolDAO;

	@Test
	public void testGuardar() {
		System.out.println("Prueba guardar usuario.");
		Usuario usuario = new Usuario();
		usuario.setLogin("juan");
		usuario.setNombreCompleto("Juan David Agudelo");
		usuario.setClave("123");
		try {
			Rol rol = rolDAO.obtener(1l);
			usuario.setRol(rol);
			usuarioDAO.guardar(usuario);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testActualizar() {
		System.out.println("Prueba actualizar usuario.");
		Usuario usuario = new Usuario();
		usuario.setLogin("juan");
		usuario.setNombreCompleto("Juan David Agudelo Alvarez 1");
		usuario.setClave("123");
		try {
			Rol rol = rolDAO.obtener(1l);
			usuario.setRol(rol);
			usuarioDAO.actualizar(usuario);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testEliminar() {
		System.out.println("Prueba eliminar usuario.");
		Usuario usuario = new Usuario();
		usuario.setLogin("juan");
		usuario.setNombreCompleto("Juan David Agudelo Alvarez 1");
		usuario.setClave("123");
		try {
			Rol rol = rolDAO.obtener(1l);
			usuario.setRol(rol);
			usuarioDAO.eliminar(usuario);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
