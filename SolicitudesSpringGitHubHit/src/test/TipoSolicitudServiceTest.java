package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dto.TipoSolicitud;
import co.edu.udea.iw.rtf.service.TipoSolicitudService;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class TipoSolicitudServiceTest {
	@Autowired
	private TipoSolicitudService tipoSolicitudService;

	@Test
	public void testObtener() {

		System.out.println("Prueba obtener tipos de solicitud service.");
		try {
			List<TipoSolicitud> lista = tipoSolicitudService.obtener();
			for (TipoSolicitud tipoSolicitud : lista) {
				System.out.println(tipoSolicitud.getCodigo() + ": "
						+ tipoSolicitud.getNombre());
			}
		} catch (IWDaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// @Test
	public void testGuardarTipoSolicitud() {
		try {
			System.out.println("Prueba guardar tipo de solicitud service.");
			tipoSolicitudService.guardarTipoSolicitud("Peticion");
			tipoSolicitudService.guardarTipoSolicitud("Queja");
			tipoSolicitudService.guardarTipoSolicitud("Reclamo");
			tipoSolicitudService.guardarTipoSolicitud("Sugerencia");
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
