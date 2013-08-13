package co.edu.udea.iw.rtf.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.udea.iw.rtf.dao.RespuestaDAO;
import co.edu.udea.iw.rtf.dto.Respuesta;
import co.edu.udea.iw.rtf.dto.RespuestaID;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Implementación para acceder a la tabla de respuestas usando el framework
 * Hibernate.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class RespuestaDAOHibernate extends HibernateDaoSupport implements
		RespuestaDAO {

	public List<Respuesta> obtener() throws IWDaoException {

		List<Respuesta> respuestas = new ArrayList<Respuesta>();
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Respuesta> list = sesion.createCriteria(Respuesta.class)
					.list();
			respuestas = list;
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}

		return respuestas;
	}

	public Respuesta obtener(RespuestaID respuestaID) throws IWDaoException {
		Session sesion = null;
		Respuesta respuesta = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			respuesta = (Respuesta) sesion.get(Respuesta.class, respuestaID);
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return respuesta;
	}

	public Respuesta guardar(Respuesta respuesta) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.save(respuesta);
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return respuesta;
	}

	public Respuesta actualizar(Respuesta respuesta) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.update(respuesta);
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return respuesta;
	}

	public void eliminar(Respuesta respuesta) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.delete(respuesta);
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}

	}
}