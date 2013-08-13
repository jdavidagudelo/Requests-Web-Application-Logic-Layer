package test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udea.iw.rtf.dao.ProductoDAO;
import co.edu.udea.iw.rtf.dao.SucursalDAO;
import co.edu.udea.iw.rtf.dao.TipoSolicitudDAO;
import co.edu.udea.iw.rtf.dao.UsuarioDAO;
import co.edu.udea.iw.rtf.dao.hibernate.SolicitudDAOHibernate;
import co.edu.udea.iw.rtf.dto.Producto;
import co.edu.udea.iw.rtf.dto.Solicitud;
import co.edu.udea.iw.rtf.dto.Sucursal;
import co.edu.udea.iw.rtf.dto.TipoSolicitud;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_configuration.xml")
public class SolicitudDAOHibernateTest {

	@Autowired
	SolicitudDAOHibernate solicitudDAO;
	@Autowired
	UsuarioDAO usuarioDAO;

	@Test
	public void testObtenerSolicitudes() {
		System.out.println("Prueba obtener solicitudes");
		try {
			List<Solicitud> lista = solicitudDAO.obtener();
			for (Solicitud solicitud : lista) {
				System.out.println(solicitud.getCodigo() + ": "
						+ solicitud.getApellidosSolicitante() + ": "
						+ solicitud.getNombresSolicitante() + ": "
						+ solicitud.getCorreoElectronico() + ": "
						+ solicitud.getTelefono());
			}
		} catch (IWDaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testObtener() {
		System.out.println("Prueba obtener solicitudes");
		try {
			List<Solicitud> lista = solicitudDAO.obtener();
			for (Solicitud solicitud : lista) {
				System.out.println(solicitud.getCodigo() + ": "
						+ solicitud.getApellidosSolicitante() + ": "
						+ solicitud.getNombresSolicitante() + ": "
						+ solicitud.getCorreoElectronico() + ": "
						+ solicitud.getTelefono());
			}
		} catch (IWDaoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private SucursalDAO sucursalDAO;
	@Autowired
	private ProductoDAO productoDAO;
	@Autowired
	private TipoSolicitudDAO tipoSolicitudDAO;

	@Test
	public void testObtenerLong() {
		try {
			System.out.println("Prueba obtener solicitud.");
			Solicitud solicitud = solicitudDAO.obtener(1l);
			System.out.println(solicitud.getCodigo() + ": "
					+ solicitud.getApellidosSolicitante() + ": "
					+ solicitud.getNombresSolicitante() + ": "
					+ solicitud.getCorreoElectronico() + ": "
					+ solicitud.getTelefono());

		} catch (IWDaoException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGuardar() {
		try {
			System.out.println("Prueba guardar solicitud.");
			Long codigo = solicitudDAO.obtenerMaximoCodigo();
			codigo = codigo + 1;
			Solicitud solicitud = new Solicitud();
			solicitud.setNombresSolicitante("Juan David");
			solicitud.setApellidosSolicitante("Agudelo Álvarez");
			solicitud.setCorreoElectronico("jdaaa2009@gmail.com");
			solicitud.setTelefono("123456789");
			solicitud.setCelular("3217788857");
			Sucursal sucursal = sucursalDAO.obtener(1l);
			solicitud.setSucursal(sucursal);
			Producto producto = productoDAO.obtener(1l);
			solicitud.setProducto(producto);
			solicitud.setTextoSolicitud("Estoy solicitando algo");
			TipoSolicitud tipoSolicitud = tipoSolicitudDAO.obtener(1l);
			solicitud.setTipoSolicitud(tipoSolicitud);
			solicitud.setCodigo(codigo);
			Calendar calendar = Calendar.getInstance();
			// fecha de la solicitud
			solicitud.setFechaSolicitud(new Timestamp(calendar.getTime()
					.getTime()));
			System.out.println(solicitud.getCodigo() + ": "
					+ solicitud.getApellidosSolicitante() + ": "
					+ solicitud.getNombresSolicitante() + ": "
					+ solicitud.getCorreoElectronico() + ": "
					+ solicitud.getTelefono());
			solicitudDAO.guardar(solicitud);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testActualizar() {
		try {
			System.out.println("Prueba actualizar solicitud.");
			Solicitud solicitud = solicitudDAO.obtener(1l);
			solicitud.setNombresSolicitante("Nombre actualizado");
			solicitudDAO.actualizar(solicitud);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testEliminar() {
		try {
			System.out.println("Prueba eliminar solicitud.");
			Solicitud solicitud = solicitudDAO.obtener(1l);
			solicitudDAO.eliminar(solicitud);
		} catch (IWDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
