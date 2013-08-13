package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dao.SucursalDAO;
import co.edu.udea.iw.rtf.dto.Sucursal;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class SucursalDAOHibernateTest {
	@Autowired
	SucursalDAO sucursalDAO;

	@Test
	public void testObtener() {
		System.out.println("Prueba obtener sucursales.");
		try {
			List<Sucursal> lista = sucursalDAO.obtener();
			for (Sucursal sucursal : lista) {
				System.out.println(sucursal.getCodigo() + ": "
						+ sucursal.getNombre());
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
			System.out.println("Prueba obtener sucursal.");
			Sucursal sucursal = sucursalDAO.obtener(1l);
			System.out.println(sucursal.getCodigo() + ": "
					+ sucursal.getNombre());

		} catch (IWDaoException e) {
			System.out.println("Fail");
			e.printStackTrace();
		}

	}

	@Test
	public void testGuardar() {
		System.out.println("Prueba guardar sucursal.");
		Sucursal sucursal = new Sucursal();
		sucursal.setCodigo(5l);
		sucursal.setNombre("Las americas");
		try {
			sucursalDAO.guardar(sucursal);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testActualizar() {
		System.out.println("Prueba actualizar sucursal.");
		Sucursal sucursal = new Sucursal();
		sucursal.setCodigo(5l);
		sucursal.setNombre("Las americas del exito");
		try {
			sucursalDAO.actualizar(sucursal);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void testEliminar() {

		System.out.println("Prueba eliminar sucursal.");
		Sucursal sucursal = new Sucursal();
		sucursal.setCodigo(5l);
		sucursal.setNombre("Gerente de cuentas");
		try {
			sucursalDAO.eliminar(sucursal);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
