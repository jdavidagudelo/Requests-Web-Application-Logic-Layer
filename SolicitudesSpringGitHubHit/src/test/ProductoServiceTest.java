package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dto.Producto;
import co.edu.udea.iw.rtf.service.ProductoService;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class ProductoServiceTest {
	@Autowired
	private ProductoService productoService;

	@Test
	public void testObtener() {
		System.out.println("Prueba obtener productos en el service.");
		try {

			List<Producto> lista = productoService.obtener();
			for (Producto producto : lista) {
				System.out.println(producto.getCodigo() + ": "
						+ producto.getNombre());
			}
		} catch (IWDaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGuardarProducto() {
		try {
			System.out.println("Prueba guardar producto en el service.");
			productoService.guardarProducto("Lavadora");
			productoService.guardarProducto("Nevera");
			productoService.guardarProducto("Carro");
			productoService.guardarProducto("Mueble");
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
