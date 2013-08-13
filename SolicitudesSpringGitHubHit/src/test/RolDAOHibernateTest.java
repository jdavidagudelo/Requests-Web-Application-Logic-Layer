package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dao.RolDAO;
import co.edu.udea.iw.rtf.dto.Rol;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class RolDAOHibernateTest {

	@Autowired
	RolDAO rolDAO;

	@Test
	public void testObtener() {
		System.out.println("Prueba obtener roles.");
		try {
			List<Rol> lista = rolDAO.obtener();
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
	public void testObtenerLong() {
		try {
			System.out.println("Prueba obtener rol.");
			Rol rol = rolDAO.obtener(1l);
			System.out.println(rol.getCodigo() + ": " + rol.getNombre());

		} catch (IWDaoException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGuardar() {

		System.out.println("Prueba guardar rol.");
		Rol rol = new Rol();
		rol.setCodigo(1l);
		rol.setNombre("Gerente");
		try {
			rolDAO.guardar(rol);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testActualizar() {
		System.out.println("Prueba guardar rol.");
		Rol rol = new Rol();
		rol.setCodigo(5l);
		rol.setNombre("Gerente de cuentas");
		try {
			rolDAO.actualizar(rol);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testEliminar() {
		System.out.println("Prueba eliminar rol.");
		Rol rol = new Rol();
		rol.setCodigo(5l);
		rol.setNombre("Gerente de cuentas");
		try {
			rolDAO.eliminar(rol);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
