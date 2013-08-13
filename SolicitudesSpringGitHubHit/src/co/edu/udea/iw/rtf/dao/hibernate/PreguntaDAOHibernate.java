package co.edu.udea.iw.rtf.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.udea.iw.rtf.dao.PreguntaDAO;
import co.edu.udea.iw.rtf.dto.Pregunta;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Implementación para acceder a la tabla de preguntas usando el framework
 * Hibernate.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class PreguntaDAOHibernate extends HibernateDaoSupport implements
		PreguntaDAO {

	/**
	 * @see PreguntaDAO#obtener() 
	 * */
	public List<Pregunta> obtener() throws IWDaoException {

		List<Pregunta> clientes = new ArrayList<Pregunta>();
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Pregunta> list = sesion.createCriteria(Pregunta.class).list();
			clientes = list;
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return clientes;
	}
	/**
	 * @see PreguntaDAO#obtener(Long)
	 * */
	public Pregunta obtener(Long codigo) throws IWDaoException {
		Session sesion = null;
		Pregunta pregunta = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			pregunta = (Pregunta) sesion.get(Pregunta.class, codigo);
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return pregunta;
	}
	/**
	 * @see PreguntaDAO#guardar(Pregunta)
	 * */
	public Pregunta guardar(Pregunta pregunta) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.save(pregunta);
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return pregunta;
	}
	/**
	 * @see PreguntaDAO#actualizar(Pregunta)
	 * */
	public Pregunta actualizar(Pregunta pregunta) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.update(pregunta);
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return pregunta;
	}
	/**
	 * @see PreguntaDAO#eliminar(Pregunta)
	 * */
	public void eliminar(Pregunta pregunta) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.delete(pregunta);
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}

	}
	/**
	 * @see PreguntaDAO#obtenerMaximoCodigo()
	 * */
	public Long obtenerMaximoCodigo() throws IWDaoException {
		Session sesion = null;
		Long codigo = 0l;
		try {
			sesion = getSessionFactory().openSession();

			Transaction t = sesion.beginTransaction();
			codigo = (Long) sesion.createQuery(
					"SELECT MAX(codigo) FROM Pregunta").uniqueResult();
			t.commit();
			if (codigo == null) {
				codigo = 0l;
			}
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
