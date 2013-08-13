package co.edu.udea.iw.rtf.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.udea.iw.rtf.dao.TipoSolicitudDAO;
import co.edu.udea.iw.rtf.dto.TipoSolicitud;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Implementación para acceder a la tabla de tipos de solicitudes usando el
 * framework Hibernate.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class TipoSolicitudDAOHibernate extends HibernateDaoSupport implements
		TipoSolicitudDAO {

	public List<TipoSolicitud> obtener() throws IWDaoException {

		List<TipoSolicitud> tiposSolicitud = new ArrayList<TipoSolicitud>();
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();

			Transaction t = sesion.beginTransaction();
			@SuppressWarnings("unchecked")
			List<TipoSolicitud> list = sesion.createCriteria(
					TipoSolicitud.class).list();
			tiposSolicitud = list;
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return tiposSolicitud;
	}

	public TipoSolicitud obtener(Long codigo) throws IWDaoException {
		Session sesion = null;
		TipoSolicitud tipoSolicitud = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			tipoSolicitud = (TipoSolicitud) sesion.get(TipoSolicitud.class,
					codigo);
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return tipoSolicitud;
	}

	public TipoSolicitud guardar(TipoSolicitud tipoSolicitud)
			throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.save(tipoSolicitud);
			sesion.flush();
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return tipoSolicitud;
	}

	public TipoSolicitud actualizar(TipoSolicitud tipoSolicitud)
			throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.update(tipoSolicitud);
			sesion.flush();
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return tipoSolicitud;
	}

	public void eliminar(TipoSolicitud tipoSolicitud) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.delete(tipoSolicitud);
			sesion.flush();
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}

	}

	public Long obtenerMaximoCodigo() throws IWDaoException {
		Session sesion = null;
		Long codigo = 0l;
		try {
			sesion = getSessionFactory().openSession();

			Transaction t = sesion.beginTransaction();
			codigo = (Long) sesion.createQuery(
					"SELECT MAX(codigo) FROM TipoSolicitud").uniqueResult();
			t.commit();
			if (codigo == null)
				codigo = 0l;

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return codigo;
	}
}