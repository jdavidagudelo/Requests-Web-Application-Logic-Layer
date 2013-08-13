package co.edu.udea.iw.rtf.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.TransactionSystemException;

import co.edu.udea.iw.rtf.dao.SolicitudDAO;
import co.edu.udea.iw.rtf.dto.Solicitud;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Implementación para acceder a la tabla de solicitudes usando el framework
 * Hibernate.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class SolicitudDAOHibernate extends HibernateDaoSupport implements
		SolicitudDAO {

	public List<Solicitud> obtener() throws IWDaoException {

		List<Solicitud> solicitudes = new ArrayList<Solicitud>();
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Solicitud> list = sesion.createCriteria(Solicitud.class)
					.list();
			solicitudes = list;
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}

		return solicitudes;
	}

	public Solicitud obtener(Long codigo) throws IWDaoException {
		Session sesion = null;
		Solicitud solicitud = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			solicitud = (Solicitud) sesion.get(Solicitud.class, codigo);
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return solicitud;
	}

	public Solicitud guardar(Solicitud solicitud) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.save(solicitud);
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return solicitud;
	}

	public Solicitud actualizar(Solicitud solicitud) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.update(solicitud);
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} catch (TransactionSystemException ex) {
			throw new IWDaoException(ex);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return solicitud;
	}

	public void eliminar(Solicitud solicitud) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.delete(solicitud);
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
					"SELECT MAX(codigo) FROM Solicitud").uniqueResult();
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