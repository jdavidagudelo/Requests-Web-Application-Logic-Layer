package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dao.RespuestaDAO;
import co.edu.udea.iw.rtf.dto.Respuesta;
import co.edu.udea.iw.rtf.dto.RespuestaID;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class RespuestaDAOHibernateTest {
	@Autowired
	private RespuestaDAO respuestaDAO;

	@Test
	public void testObtener() {
		System.out.println("Prueba obtener respuestas.");
		try {
			List<Respuesta> lista = respuestaDAO.obtener();
			for (Respuesta respuesta : lista) {
				System.out.println(respuesta.getRespuestaID().getPregunta()
						.getPregunta()
						+ ": " + respuesta.getRespuesta());
			}
		} catch (IWDaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void testObtenerLong() {
		try {
			RespuestaID id = new RespuestaID();
			Respuesta respuesta = respuestaDAO.obtener(id);
			System.out.println(respuesta.getRespuestaID().getPregunta()
					.getPregunta()
					+ ": " + respuesta.getRespuesta());

		} catch (IWDaoException e) {
			System.out.println("Fail");
			e.printStackTrace();
		}

	}

	// @Test
	public void testGuardar() {
		Respuesta respuesta = new Respuesta();

		try {
			respuestaDAO.guardar(respuesta);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void testActualizar() {
		Respuesta respuesta = new Respuesta();

		try {
			respuestaDAO.actualizar(respuesta);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void testEliminar() {
		Respuesta respuesta = new Respuesta();
		try {
			respuestaDAO.eliminar(respuesta);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
