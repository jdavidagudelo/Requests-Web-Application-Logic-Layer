package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dao.TipoSolicitudDAO;
import co.edu.udea.iw.rtf.dto.TipoSolicitud;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class TipoSolicitudDAOHibernateTest {
	@Autowired
	TipoSolicitudDAO tipoSolicitudDAO;

	@Test
	public void testObtener() {
		System.out.println("Prueba obtener tipos de solicitud.");
		try {
			List<TipoSolicitud> lista = tipoSolicitudDAO.obtener();
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

	@Test
	public void testObtenerLong() {
		System.out.println("Prueba obtener tipo de solicitud.");
		try {
			TipoSolicitud tipoSolicitud = tipoSolicitudDAO.obtener(1l);
			System.out.println(tipoSolicitud.getCodigo() + ": "
					+ tipoSolicitud.getNombre());

		} catch (IWDaoException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGuardar() {
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setCodigo(5l);
		tipoSolicitud.setNombre("Sugerencia");
		tipoSolicitud.setActivo(true);
		try {
			tipoSolicitudDAO.guardar(tipoSolicitud);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testActualizar() {
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setCodigo(5l);
		tipoSolicitud.setNombre("Peticion");
		tipoSolicitud.setActivo(true);
		try {
			tipoSolicitudDAO.actualizar(tipoSolicitud);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testEliminar() {
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setCodigo(5l);
		tipoSolicitud.setNombre("Peticion");
		tipoSolicitud.setActivo(true);
		try {
			tipoSolicitudDAO.eliminar(tipoSolicitud);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
