package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dto.Rol;
import co.edu.udea.iw.rtf.service.RolService;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class RolServiceTest {
	@Autowired
	private RolService rolService;

	@Test
	public void testObtener() {

		System.out.println("Prueba obtener rol desde el service.");
		try {

			List<Rol> lista = rolService.obtener();
			for (Rol rol : lista) {
				System.out.println(rol.getCodigo() + ": " + rol.getNombre());
			}
		} catch (IWDaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGuardarRol() {
		try {

			System.out.println("Prueba guardar rol desde el service.");
			rolService.guardarRol("Gerente");
			rolService.guardarRol("Cliente");
			rolService.guardarRol("Responsable");
			rolService.guardarRol("Invitado");
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
