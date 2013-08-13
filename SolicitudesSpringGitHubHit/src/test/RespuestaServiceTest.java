package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dto.Respuesta;
import co.edu.udea.iw.rtf.service.RespuestaService;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class RespuestaServiceTest {
	@Autowired
	private RespuestaService respuestaService;

	@Test
	public void testObtener() {
		List<Respuesta> respuestas = null;
		try {
			System.out.println("Prueba obtener respuestas desde el service.");
			respuestas = respuestaService.obtener();
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Respuesta respuesta : respuestas) {
			System.out.println(respuesta.getRespuestaID().getPregunta()
					.getPregunta()
					+ ": "
					+ respuesta.getRespuestaID().getSolicitud().getCodigo()
					+ ": " + respuesta.getRespuesta());
		}
	}

	@Test
	public void testGuardar() {
		try {
			System.out.println("Prueba guardar respuesta desde el service.");
			respuestaService.guardarRespuesta(true, 1l, 1l);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
