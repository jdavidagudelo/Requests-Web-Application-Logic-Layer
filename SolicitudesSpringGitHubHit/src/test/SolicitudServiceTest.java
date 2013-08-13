package test;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dto.Solicitud;
import co.edu.udea.iw.rtf.service.SolicitudService;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class SolicitudServiceTest {
	@Autowired
	private SolicitudService solicitudService;



	@Test
	public void testResponder() {
		try {
			System.out.println("Prueba responder solicitud.");
			solicitudService.responderSolicitud(2l, "jdavid.agudelo",
					"Esto es una respuesta nuevamente");
			Thread.sleep(60000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 @Test
	public void testObtener() {
		try {
			System.out.println("Prueba obtener las solicitudes.");
			List<Solicitud> solicitudes = solicitudService.obtener();
			System.out.println(solicitudes.size());
			for (Solicitud solicitud : solicitudes) {
				System.out.println(solicitud.getCodigo() + ": "
						+ solicitud.getApellidosSolicitante() + ": "
						+ solicitud.getNombresSolicitante() + ": "
						+ solicitud.getCorreoElectronico() + ": "
						+ solicitud.getTelefono());
			}
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 @Test
	public void testObtenerSolicitudesPendientes() {
		try {
			System.out.println("Prueba de obtener las solicitudes pendientes.");
			List<Solicitud> solicitudes = solicitudService
					.obtenerSolicitudesPendientes();
			for (Solicitud solicitud : solicitudes) {
				System.out.println(solicitud.getCodigo() + ": "
						+ solicitud.getApellidosSolicitante() + ": "
						+ solicitud.getNombresSolicitante() + ": "
						+ solicitud.getCorreoElectronico() + ": "
						+ solicitud.getTelefono());
			}
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 @Test
	public void testObtenerSolicitudesAsignadas() {
		try {
			System.out
					.println("Prueba obtener solicitudes asignadas a un usuario.");
			Set<Solicitud> solicitudesAsignadas = solicitudService
					.obtenerSolicitudesAsignadas("jdavid.agudelo");
			for (Solicitud solicitud : solicitudesAsignadas) {
				System.out.println(solicitud.getCodigo() + ": "
						+ solicitud.getApellidosSolicitante() + ": "
						+ solicitud.getNombresSolicitante() + ": "
						+ solicitud.getCorreoElectronico() + ": "
						+ solicitud.getTelefono());
			}
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 @Test
	public void testAsignarSolicitud() {
		try {
			System.out.println("Prueba asignar solicitud.");
			solicitudService.asignarSolicitud(5l, "jdavid.agudelo");
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
