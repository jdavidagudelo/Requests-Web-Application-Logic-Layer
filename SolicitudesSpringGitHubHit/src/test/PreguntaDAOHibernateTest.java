package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dao.PreguntaDAO;
import co.edu.udea.iw.rtf.dto.Pregunta;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class PreguntaDAOHibernateTest {
	@Autowired
	PreguntaDAO preguntaDAO;

	@Test
	public void nothing()
	{
		System.out.println("1. osa".substring(0, "1. osa".indexOf("."))); 
	}
	//@Test
	public void testObtener() {

		List<Pregunta> preguntas = null;
		try {
			preguntas = preguntaDAO.obtener();
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Pregunta pregunta : preguntas) {
			System.out.println(pregunta.getCodigo() + ": "
					+ pregunta.getPregunta());
		}

	}

	//@Test
	public void testGuardar() {
		System.out.println("Hello world");
		try {
			Pregunta pregunta = new Pregunta();

			pregunta.setCodigo(4l);
			pregunta.setPregunta("¿What is your name again...?");

			// pregunta = preguntaDAO.obtener(1l);
			preguntaDAO.actualizar(pregunta);
			System.out.println(pregunta.getPregunta());
			System.out.println("success");
		} catch (IWDaoException e) {
			System.out.println("Fail");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}

}
