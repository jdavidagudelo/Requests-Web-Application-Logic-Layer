package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dto.Sucursal;
import co.edu.udea.iw.rtf.service.SucursalService;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class SucursalServiceTest {

	@Autowired
	private SucursalService sucursalService;

	@Test
	public void testObtener() {

		System.out.println("Prueba obtener sucursales desde el servicio.");
		try {
			List<Sucursal> lista = sucursalService.obtener();
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
	public void testGuardarSucursal() {
		try {
			System.out.println("Prueba guardar sucursal desde el service.");
			sucursalService.guardarSucursal("Sucursal 1", null, null);
			sucursalService.guardarSucursal("Sucursal 2", null, null);
			sucursalService.guardarSucursal("Sucursal 3", null, null);
			sucursalService.guardarSucursal("Sucursal 4", null, null);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
