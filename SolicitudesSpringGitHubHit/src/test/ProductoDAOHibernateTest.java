package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dao.ProductoDAO;
import co.edu.udea.iw.rtf.dto.Producto;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class ProductoDAOHibernateTest {
	@Autowired
	ProductoDAO productoDAO;

	@Test
	public void testObtener() {
		System.out.println("Prueba obtener productos");
		try {
			List<Producto> lista = productoDAO.obtener();
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
	public void testObtenerLong() {
		try {
			System.out
					.println("Prueba obtener producto con un codigo determinado");
			Producto producto = productoDAO.obtener(1l);
			System.out.println(producto.getCodigo() + ": "
					+ producto.getNombre());

		} catch (IWDaoException e) {
			System.out.println("Fail");
			e.printStackTrace();
		}

	}

	@Test
	public void testGuardar() {
		System.out.println("Prueba guardar un producto.");
		Producto producto = new Producto();
		producto.setCodigo(5l);
		producto.setNombre("Producto");
		try {
			productoDAO.guardar(producto);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testActualizar() {
		System.out.println("Prueba actualizar producto.");
		Producto producto = new Producto();
		producto.setCodigo(5l);
		producto.setNombre("Producto actualizado1");
		try {
			productoDAO.actualizar(producto);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void testEliminar() {
		System.out.println("Prueba eliminar producto.");
		Producto producto = new Producto();
		producto.setCodigo(5l);
		producto.setNombre("Producto actualizado");
		try {
			productoDAO.eliminar(producto);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
