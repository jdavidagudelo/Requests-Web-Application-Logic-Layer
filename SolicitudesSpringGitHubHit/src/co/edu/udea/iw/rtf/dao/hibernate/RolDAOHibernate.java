package co.edu.udea.iw.rtf.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import co.edu.udea.iw.rtf.dao.RolDAO;
import co.edu.udea.iw.rtf.dto.Rol;
import co.edu.udea.iw.rtf.util.exception.IWDaoException;

/**
 * Implementación para acceder a la tabla de roles usando el framework
 * Hibernate.
 * 
 * @author Juan David Agudelo Álvarez jdaaa2009@gmail.com
 * */
public class RolDAOHibernate extends HibernateDaoSupport implements RolDAO {

	/**
	 * @see RolDAO#obtener()
	 */
	public List<Rol> obtener() throws IWDaoException {

		List<Rol> roles = new ArrayList<Rol>();
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();

			Transaction t = sesion.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Rol> list = sesion.createCriteria(Rol.class).list();
			roles = list;
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}

		return roles;
	}

	public Rol obtener(Long codigo) throws IWDaoException {

		Rol rol = null;
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			rol = (Rol) sesion.get(Rol.class, codigo);
			t.commit();
		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return rol;
	}

	public Rol guardar(Rol rol) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.save(rol);
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return rol;
	}

	public Rol actualizar(Rol rol) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.update(rol);
			t.commit();

		} catch (HibernateException e) {
			throw new IWDaoException(e);
		} finally {
			if (sesion != null) {
				sesion.close();
			}
		}
		return rol;
	}

	public void eliminar(Rol rol) throws IWDaoException {
		Session sesion = null;
		try {
			sesion = getSessionFactory().openSession();
			Transaction t = sesion.beginTransaction();
			sesion.delete(rol);
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
			codigo = (Long) sesion.createQuery("SELECT MAX(codigo) FROM Rol")
					.uniqueResult();
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