package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dto.Usuario;
import co.edu.udea.iw.rtf.service.UsuarioService;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class UsuarioServiceTest {
	@Autowired
	UsuarioService usuarioService;

	//@Test
	public void testValidar() {
		try {
			System.out.println("Prueba validar usuario service.");
			System.out.println("Validar: "
					+ usuarioService.validar("jdavid.agudelo1", "1231"));
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//@Test
	public void testObtener() {
		try {
			System.out.println("Prueba obtener usuarios service.");
			List<Usuario> usuarios = usuarioService.obtener();
			System.out.println(usuarios.size());
			for (Usuario usuario : usuarios) {
				System.out.println(usuario.getLogin() + ": "
						+ usuario.getNombreCompleto() + ": "
						+ usuario.getRol().getNombre());
			}
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//@Test
	public void obtenerUsuario() {
		Usuario usuario = null;
		try {
			System.out.println("Prueba obtener usuario service.");
			usuario = usuarioService.obtener("jdavid.agudelo");
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(usuario.getLogin() + ": "
				+ usuario.getNombreCompleto() + ": "
				+ usuario.getRol().getNombre());

	}

	@Test
	public void testCrearUsuario() {
		try {
			System.out.println("Test crear usuario service.");
			usuarioService.crearUsuario("jdavid.agudelo", "Juan David", "123", 1l);
			usuarioService.crearUsuario("jdavid.agudelo2", "Juan David", "123", 1l);
			usuarioService.crearUsuario("jdavid.agudelo3", "Juan David", "123", 1l);
			usuarioService.crearUsuario("jdavid.agudelo4", "Juan David", "123", 2l);
			usuarioService.crearUsuario("jdavid.agudelo5", "Juan David", "123", 2l);
			usuarioService.crearUsuario("jdavid.agudelo6", "Juan David", "123", 2l);
		} catch (IWDaoException e) {
			e.printStackTrace();
		}
	}

}
