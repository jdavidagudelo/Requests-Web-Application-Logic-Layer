package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dto.Pregunta;
import co.edu.udea.iw.rtf.service.PreguntaService;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class PreguntaServiceTest {

	@Autowired
	PreguntaService preguntaService;

	@Test
	public void testObtener() {

		System.out.println("Prueba obtener pregunta service.");
		try {

			List<Pregunta> lista = preguntaService.obtener();
			for (Pregunta pregunta : lista) {
				System.out.println(pregunta.getCodigo() + ": "
						+ pregunta.getPregunta());
			}
		} catch (IWDaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGuardarPregunta() {
		try {
			System.out.println("Prueba guardar pregunta service.");
			preguntaService.guardarPregunta("¿Esta es una pregunta de prueba?");
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
